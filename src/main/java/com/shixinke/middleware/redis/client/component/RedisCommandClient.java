package com.shixinke.middleware.redis.client.component;

import com.shixinke.middleware.redis.client.component.enums.RedisScriptDebugLevelEnum;
import com.shixinke.middleware.redis.client.component.serializer.ObjectMapperCodec;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis命令客户端
 * @author shixinke
 */
@Component
public class RedisCommandClient implements RedisExecutor {


    @Resource
    private RedissonClient redissonClient;

    private Codec codec;

    private static final Codec DEFAULT_CODEC = new ObjectMapperCodec();


    @Override
    public boolean del(String key) {
        return redissonClient.getBucket(key).delete();
    }

    @Override
    public boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    @Override
    public boolean expire(String key, Long seconds) {
        return redissonClient.getBucket(key).expire(seconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean expireAt(String key, Integer timestamp) {
        return redissonClient.getBucket(key).expireAt(timestamp);
    }

    @Override
    public boolean pExpire(String key, Long mills) {
        return redissonClient.getBucket(key).expire(mills, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean pExpireAt(String key, Long timestampMills) {
        Date date = new Date();
        date.setTime(timestampMills);
        return redissonClient.getBucket(key).expireAt(date);
    }

    @Override
    public Long ttl(String key) {
        long mills = redissonClient.getBucket(key).remainTimeToLive();
        return mills / 1000;
    }

    @Override
    public Long pTtl(String key) {
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    @Override
    public boolean persist(String key) {
        return redissonClient.getBucket(key).clearExpire();
    }

    @Override
    public boolean rename(String originalKey, String newKey) {
        redissonClient.getBucket(originalKey).rename(newKey);
        return exists(newKey);
    }

    @Override
    public boolean renameNx(String originalKey, String newKey) {
        return redissonClient.getBucket(originalKey).renamenx(newKey);
    }

    @Override
    public String randomKey() {
        return redissonClient.getKeys().randomKey();
    }

    @Override
    public String type(String key) {
        RType type = redissonClient.getKeys().getType(key);
        if (type != null) {
            return type.toString();
        }
        return null;
    }

    @Override
    public Iterable<String> keys(String pattern) {
        return redissonClient.getKeys().getKeysByPattern(pattern);
    }

    @Override
    public boolean append(String key, String value) {
        String originalValue = get(key, String.class);
        if (originalValue == null || originalValue.length() == 0) {
            return set(key, value, null);
        }
        String newValue = originalValue + value;
        return redissonClient.getBucket(key).compareAndSet(originalValue, newValue);
    }

    @Override
    @Deprecated
    public Long bitCount(String key, Long start, Long end) {
        return redissonClient.getBitSet(key).cardinality();
    }

    @Override
    public boolean set(String key, Object value, Long expireSeconds) {
        if (expireSeconds == null) {
            redissonClient.getBucket(key, getCodec()).set(value);
        } else {
            redissonClient.getBucket(key, getCodec()).set(value, expireSeconds, TimeUnit.SECONDS);
        }
        return true;
    }

    @Override
    public boolean setEx(String key, Object value, Long expireSeconds) {
        redissonClient.getBucket(key, getCodec()).set(value, expireSeconds, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public boolean pSetEx(String key, Object value, Long expireMills) {
        redissonClient.getBucket(key).set(value, expireMills, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public boolean setNx(String key, Object value) {
        return redissonClient.getBucket(key).trySet(value);
    }

    @Override
    public <V> V get(String key, Class<V> clazz) {
        return (V) redissonClient.getBucket(key, getCodec()).get();
    }

    @Override
    public <V> V getSet(String key, Object value, Class<V> clazz) {
        return (V) redissonClient.getBucket(key, getCodec()).getAndSet(value);
    }

    @Override
    public Long incr(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public Long incrBy(String key, int step) {
        return redissonClient.getAtomicLong(key).addAndGet(step);
    }

    @Override
    public double incrByFloat(String key, float step) {
        return redissonClient.getAtomicDouble(key).addAndGet(step);
    }

    @Override
    public Long decr(String key) {
        return redissonClient.getAtomicLong(key).decrementAndGet();
    }

    @Override
    public Long decrBy(String key, int step) {
        if (step == 1) {
            return decr(key);
        }
        long originalValue = redissonClient.getAtomicLong(key).get();
        long updateValue = originalValue - step;
        redissonClient.getAtomicLong(key).compareAndSet(originalValue, updateValue);
        return updateValue;
    }

    @Override
    public <V> Map<String, V> mGet(List<String> keys, Class<V> clazz) {
        String[] keysArr = new String[keys.size()];
        keys.toArray(keysArr);
        return redissonClient.getBuckets().get(keysArr);
    }

    @Override
    public boolean mSet(Map<String, Object> valueMap) {
        redissonClient.getBuckets().set(valueMap);
        return true;
    }

    @Override
    public boolean mSetNx(Map<String, Object> valueMap) {
        redissonClient.getBuckets().trySet(valueMap);
        return true;
    }

    @Override
    public boolean setRange(String key, int offset, String value) {
        String originalValue = get(key, String.class);
        if (originalValue == null || originalValue.length() < offset) {
            return false;
        }
        String newValue = originalValue.substring(0, offset) + value;
        return redissonClient.getBucket(key).compareAndSet(originalValue, newValue);
    }

    @Override
    public Long strLen(String key) {
        return redissonClient.getBucket(key).size();
    }

    @Override
    public boolean hDel(String key, List<String> fields) {
        String[] members = new String[fields.size()];
        fields.toArray(members);
        long deleted = redissonClient.getMap(key).fastRemove(members);
        return deleted > 0;
    }

    @Override
    public boolean hDel(String key, String field) {
        long deleted = redissonClient.getMap(key).fastRemove(field);
        return deleted > 0;
    }

    @Override
    public boolean hExists(String key, String filed) {
        return redissonClient.getMap(key).containsKey(filed);
    }

    @Override
    public <V> V hGet(String key, String field, Class<V> clazz) {
        return (V) redissonClient.getMap(key).get(field);
    }

    @Override
    public <V> Map<String, V> hGetAll(String key) {
        Map<Object, Object> valueMap =  redissonClient.getMap(key).readAllMap();
        Map<String, V> resultMap = new HashMap<>(valueMap.size());
        for (Map.Entry<Object, Object> entry : valueMap.entrySet()) {
            resultMap.put(entry.getKey().toString(), (V) entry.getValue());
        }
        return resultMap;
    }

    @Override
    public long hIncr(String key, String field, int step) {
        return (long) redissonClient.getMap(key).addAndGet(field, step);
    }

    @Override
    public double hIncrByFloat(String key, String field, double step) {
        return (double) redissonClient.getMap(key).addAndGet(field, step);
    }

    @Override
    public List<String> hKeys(String key) {
        Set<Object> keys = redissonClient.getMap(key).keySet();
        List<String> fields = new ArrayList<>(keys.size());
        for (Object k : keys) {
            fields.add(k.toString());
        }
        return fields;
    }

    @Override
    public int hLen(String key) {
        return redissonClient.getMap(key).size();
    }

    @Override
    public <V> Map<String, V> hMGet(String key, List<String> fields, Class<V> clazz) {
        Set<Object> keys = new HashSet<>(fields);
        Map<Object, Object> valueMap =  redissonClient.getMap(key).getAll(keys);
        Map<String, V> resultMap = new HashMap<>(valueMap.size());
        for (Map.Entry<Object, Object> entry : valueMap.entrySet()) {
            resultMap.put(entry.getKey().toString(), (V) entry.getValue());
        }
        return resultMap;
    }

    @Override
    public boolean hMSet(String key, Map<String, Object> valuesMap) {
        redissonClient.getMap(key).putAll(valuesMap);
        return true;
    }

    @Override
    public boolean hSet(String key, String field, Object value) {
        Object result = redissonClient.getMap(key).put(field, value);
        return result != null;
    }

    @Override
    public boolean hSetNx(String key, String field, Object value) {
        Object result = redissonClient.getMap(key).fastPutIfAbsent(field, value);
        return result != null;
    }

    @Override
    public int hStrLen(String key, String field) {
        return redissonClient.getMap(key).valueSize(field);
    }

    @Override
    public <V> List<V> hVals(String key, Class<V> clazz) {
        Collection<Object> values = redissonClient.getMap(key).values();
        List<V> valueList = new ArrayList<>(values.size());
        for (Object v : values) {
            valueList.add((V) v);
        }
        return valueList;
    }

    @Override
    public <V> V blPop(String key, Long timeout) {
        try {
            return (V) redissonClient.getBlockingQueue(key).poll(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <V> List<V> blPop(List<String> keys, Long timeout) {
        RBatch batch = redissonClient.createBatch();
        for (String key : keys) {
            batch.getBlockingDeque(key).pollFirstAsync(timeout, TimeUnit.MILLISECONDS);
        }
        BatchResult result = batch.execute();
        return result.getResponses();
    }

    @Override
    public <V> V brPop(String key, Long timeout) {
        return (V) redissonClient.getBlockingQueue(key).pollLastAndOfferFirstToAsync(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public <V> List<V> brPop(List<String> keys, Long timeout) {
        RBatch batch = redissonClient.createBatch();
        for (String key : keys) {
            batch.getBlockingQueue(key).pollAsync(timeout, TimeUnit.MILLISECONDS);
        }
        BatchResult result = batch.execute();
        return result.getResponses();
    }

    @Override
    @Deprecated
    public <V> V brPopLPush(String sourceKey, String destKey) {
        RFuture<Object> future = redissonClient.getBlockingQueue(sourceKey).pollLastAndOfferFirstToAsync(destKey);
        if (future != null ) {
            return (V) future.getNow();
        }
        return null;
    }

    @Override
    public <V> V lIndex(String key, int index) {
        return (V) redissonClient.getList(key).get(index);
    }

    @Override
    @Deprecated
    public boolean lInsert(String key, Integer direction, Object pivot, List<Object> values) {
        return false;
    }

    @Override
    @Deprecated
    public boolean lInsertBefore(String key, Object pivot, List<Object> values) {
        return false;
    }

    @Override
    @Deprecated
    public boolean lInsertAfter(String key, Object pivot, List<Object> values) {
        return false;
    }

    @Override
    public int lLen(String key) {
        return redissonClient.getList(key).size();
    }

    @Override
    public <V> V lPop(String key) {
        return (V) redissonClient.getQueue(key).poll();
    }

    @Override
    public boolean lPush(String key, Object value) {
       redissonClient.getDeque(key).addFirst(value);
       return true;
    }

    @Override
    @Deprecated
    public boolean lPush(String key, List<Object> values) {
        return false;
    }

    @Override
    @Deprecated
    public boolean lPushX(String key, Object value) {
        //redissonClient.getDeque(key).addFirst(value);
        return true;
    }

    @Override
    public <V> List<V> lRange(String key, int start, int end) {
        return (List<V>) redissonClient.getList(key).subList(start, end);
    }

    @Override
    public boolean lRem(String key, Object value, Integer count) {
        return redissonClient.getList(key).remove(value, count);
    }

    @Override
    public boolean lSet(String key, int index, Object value) {
        Object result = redissonClient.getList(key).set(index, value);
        return result != null;
    }

    @Override
    public boolean lTrim(String key, int start, int end) {
        redissonClient.getList(key).trim(start, end);
        return false;
    }

    @Override
    public boolean rPop(String key) {
        Object value = redissonClient.getDeque(key).pollLast();
        return value != null;
    }

    @Override
    public boolean rPopLPush(String sourceKey, String destKey) {
        Object value = redissonClient.getDeque(sourceKey).pollLastAndOfferFirstTo(destKey);
        return value != null;
    }

    @Override
    public boolean rPush(String key, Object value) {
        return redissonClient.getList(key).add(value);
    }

    @Override
    @Deprecated
    public boolean rPushX(String key, Object value) {
        return false;
    }

    @Override
    public boolean sAdd(String key, Object value) {
        return redissonClient.getSet(key).add(value);
    }

    @Override
    public boolean sAdd(String key, List<Object> values) {
        return redissonClient.getSet(key).addAll(values);
    }

    @Override
    public int sCard(String key) {
        return redissonClient.getSet(key).size();
    }

    @Override
    public <V> Set<V> sDiff(String sourceKey, String destKey) {
        return (Set<V>) redissonClient.getSet(sourceKey).readDiff(destKey);
    }

    @Override
    public <V> Set<V> sDiff(String sourceKey, List<String> destKeys) {
        String[] keys = new String[destKeys.size()];
        destKeys.toArray(keys);
        return (Set<V>) redissonClient.getSet(sourceKey).readDiff(keys);
    }

    @Override
    public boolean sDiffStore(String storeKey, String sourceKey, List<String> destKeys) {
        Set<Object> values = sDiff(sourceKey, destKeys);
        return redissonClient.getSet(sourceKey).addAll(values);
    }

    @Override
    public <V> Set<V> sInter(String sourceKey, String destKey) {
        return (Set<V>) redissonClient.getSet(sourceKey).readIntersection(destKey);
    }

    @Override
    public <V> Set<V> sInter(String sourceKey, List<String> destKeys) {
        String[] keys = new String[destKeys.size()];
        destKeys.toArray(keys);
        return (Set<V>) redissonClient.getSet(sourceKey).readIntersection(keys);
    }

    @Override
    public boolean sInterStore(String storeKey, String sourceKey, List<String> destKeys) {
        Set<Object> values = sInter(sourceKey, destKeys);
        return redissonClient.getSet(sourceKey).addAll(values);
    }

    @Override
    public <V> Set<V> sUnion(String sourceKey, String destKey) {
        return (Set<V>) redissonClient.getSet(sourceKey).readUnion(destKey);
    }

    @Override
    public <V> Set<V> sUnion(String sourceKey, List<String> destKeys) {
        String[] keys = new String[destKeys.size()];
        destKeys.toArray(keys);
        return (Set<V>) redissonClient.getSet(sourceKey).readUnion(keys);
    }

    @Override
    public boolean sUnionStore(String storeKey, String sourceKey, List<String> destKeys) {
        Set<Object> values = sUnion(sourceKey, destKeys);
        return redissonClient.getSet(sourceKey).addAll(values);
    }

    @Override
    public boolean sIsMember(String key, Object member) {
        return redissonClient.getSet(key).contains(member);
    }

    @Override
    public <V> Set<V> sMembers(String key) {
        return (Set<V>) redissonClient.getSet(key).readAll();
    }

    @Override
    public boolean sMove(String sourceKey, String destKey, Object value) {
        return redissonClient.getSet(sourceKey).move(destKey, value);
    }

    @Override
    public <V> Set<V> sPop(String key, Integer count) {
        return (Set<V>) redissonClient.getSet(key).removeRandom(count);
    }

    @Override
    public boolean sRem(String key, Object value) {
        return redissonClient.getSet(key).remove(value);
    }

    @Override
    public boolean sRem(String key, List<Object> values) {
        return redissonClient.getSet(key).removeAll(values);
    }

    @Override
    public <V> Set<V> sRandomMember(String key, Integer count) {
        return (Set<V>) redissonClient.getSet(key).random(count);
    }

    @Override
    public boolean zAdd(String key, String member, Double score) {
        return redissonClient.getScoredSortedSet(key).add(score, member);
    }

    @Override
    public boolean zAdd(String key, Map<Object, Double> members) {
        int affected = redissonClient.getScoredSortedSet(key).addAll(members);
        return affected > 0;
    }

    @Override
    public int zCard(String key) {
        return redissonClient.getSortedSet(key).size();
    }

    @Override
    public int zCount(String key, double min, double max) {
        return redissonClient.getScoredSortedSet(key).count(min, true, max, true);
    }

    @Override
    public double zIncrBy(String key, String member, double step) {
        return redissonClient.getScoredSortedSet(key).addScore(member, step);
    }

    @Override
    @Deprecated
    public boolean zInterStore(String storeKey, String sourceKey, List<String> destKeys) {
        String[] keys = new String[destKeys.size()];
        destKeys.toArray(keys);
        redissonClient.getScoredSortedSet(sourceKey).intersection(keys);
        //redissonClient.getScoredSortedSet(sourceKey).addAll(redissonClient.getScoredSortedSet(sourceKey).re);
        return false;
    }

    @Override
    @Deprecated
    public long zLexCount(String key, String minMember, String maxMember) {
        return 0;
    }

    @Override
    public List<Map<String, Double>> zRange(String key, int start, int end) {
        Collection<ScoredEntry<Object>> values = redissonClient.getScoredSortedSet(key).entryRange(start, end);
        return parseScoredEntryList(values, start, end);
    }

    @Override
    @Deprecated
    public Map<String, Double> zRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count) {
        //redissonClient.getScoredSortedSet(key).entryRange()
        return null;
    }

    @Override
    public List<Map<String, Double>> zRevRange(String key, int start, int end) {
        Collection<ScoredEntry<Object>> values = redissonClient.getScoredSortedSet(key).entryRangeReversed(start, end);
        return parseScoredEntryList(values, start, end);
    }

    @Override
    @Deprecated
    public Map<String, Double> zRevRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count) {
        return null;
    }

    @Override
    public Integer zRank(String key, String member) {
        return redissonClient.getScoredSortedSet(key).rank(member);
    }

    @Override
    public Integer zRevRank(String key, String member) {
        return redissonClient.getScoredSortedSet(key).revRank(member);
    }

    @Override
    public boolean zRem(String key, String member) {
        return redissonClient.getScoredSortedSet(key).remove(member);
    }

    @Override
    public boolean zRem(String key, List<String> members) {
        return redissonClient.getSortedSet(key).removeAll(members);
    }

    @Override
    public boolean zRemRangeByRank(String key, int start, int end) {
        int affected = redissonClient.getScoredSortedSet(key).removeRangeByRank(start, end);
        return affected > 0;
    }

    @Override
    public boolean zRemRangeByScope(String key, double min, double max) {
        int affected = redissonClient.getScoredSortedSet(key).removeRangeByScore(min, true, max, true);
        return affected > 0;
    }

    @Override
    public Double zScore(String key, String member) {
        return redissonClient.getScoredSortedSet(key).getScore(member);
    }

    @Override
    @Deprecated
    public boolean zUnionStore(String storeKey, String sourceKey, List<String> destKeys) {
        return false;
    }

    private List<Map<String, Double>> parseScoredEntryList(Collection<ScoredEntry<Object>> entryList, int start, int end) {
        int size = 10;
        if (end > 0) {
            size = end - start;
        }
        List<Map<String, Double>> valueList = new ArrayList<>(size);
        for (ScoredEntry<Object> entry : entryList) {
            Map<String, Double> valueMap = new HashMap<>(1);
            valueMap.put(entry.getValue().toString(), entry.getScore());
            valueList.add(valueMap);
        }
        return valueList;
    }

    @Override
    public boolean discard(RTransaction transaction) {
        if (transaction == null) {
            transaction = redissonClient.createTransaction(TransactionOptions.defaults());
        }
        transaction.rollback();
        return true;
    }

    @Override
    public boolean watch(String key, ObjectListener listener) {
        int affected = redissonClient.getBucket(key).addListener(listener);
        return affected > 0;
    }

    @Override
    @Deprecated
    public boolean watch(List<String> keys) {
        return false;
    }

    @Override
    public RTransaction multi() {
        return redissonClient.createTransaction(TransactionOptions.defaults());
    }

    @Override
    @Deprecated
    public boolean unwatch() {
        return false;
    }

    @Override
    public boolean exec(RTransaction transaction) {
        if (transaction == null) {
            transaction = redissonClient.createTransaction(TransactionOptions.defaults());
        }
        transaction.commit();
        return true;
    }


    @Override
    public <T> void pSubscribe(String pattern, Class<T> type, PatternMessageListener<T> listener) {
        redissonClient.getPatternTopic(pattern).addListener(type, listener);
    }

    @Override
    public <T> void pSubscribe(List<String> patterns, List<Class<T>> types, List<PatternMessageListener<T>> listeners) {
        for (int i = 0; i < patterns.size(); i++) {
            redissonClient.getPatternTopic(patterns.get(i)).addListener(types.get(i), listeners.get(i));
        }
    }

    @Override
    public void pUnSubscribe(String pattern) {
        redissonClient.getPatternTopic(pattern).removeAllListeners();
    }

    @Override
    public void pUnSubscribe(List<String> patterns) {
        for (String pattern : patterns) {
            redissonClient.getPatternTopic(pattern).removeAllListeners();
        }
    }

    @Override
    public Long publish(String channel, Object message) {
        return redissonClient.getTopic(channel).publish(message);
    }

    @Override
    public <M> void subscribe(String topic, Class<M> clazz, MessageListener<? extends M> listener) {
        redissonClient.getTopic(topic).addListener(clazz, listener);
    }

    @Override
    public <M> void subscribe(List<String> topics, List<Class<M>> clazz, List<MessageListener<? extends M>> listeners) {
        for (int i = 0; i < topics.size(); i ++) {
            redissonClient.getTopic(topics.get(i)).addListener(clazz.get(i), listeners.get(i));
        }
    }

    @Override
    public void unsubscribe(String topic) {
        redissonClient.getTopic(topic).removeAllListeners();
    }

    @Override
    public void unsubscribe(List<String> topics) {
        for (String topic : topics) {
            redissonClient.getTopic(topic).removeAllListeners();
        }
    }

    @Override
    @Deprecated
    public <V> V eval(String script, int keysNum, List<String> keys, List<Object> args) {
        return null;
    }

    @Override
    @Deprecated
    public <V> V evalSha(String sha1, int keysNum, List<String> keys, List<Object> args) {
        //redissonClient.getScript().e
        return null;
    }

    @Override
    @Deprecated
    public void debugScript(RedisScriptDebugLevelEnum level) {

    }

    @Override
    public boolean scriptExists(String script) {
        List<Boolean> resultList = redissonClient.getScript().scriptExists(script);
        if (resultList == null || resultList.isEmpty()) {
            return false;
        }
        return resultList.get(0);
    }

    @Override
    public List<Boolean> scriptExists(List<String> scripts) {
        String[] scriptArr = new String[scripts.size()];
        scripts.toArray(scriptArr);
        return redissonClient.getScript().scriptExists(scriptArr);
    }

    @Override
    public boolean flushScript() {
        redissonClient.getScript().scriptFlush();
        return true;
    }

    @Override
    public boolean killScript() {
        redissonClient.getScript().scriptKill();
        return true;
    }

    @Override
    public <V> V loadScript(String script) {
        redissonClient.getScript().scriptLoad(script);
        return null;
    }


    @Override
    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public Codec getCodec() {
        if (this.codec == null) {
            return DEFAULT_CODEC;
        }
        return codec;
    }
}
