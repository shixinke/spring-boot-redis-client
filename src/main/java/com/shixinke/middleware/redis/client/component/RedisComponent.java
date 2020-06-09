package com.shixinke.middleware.redis.client.component;

import com.shixinke.middleware.redis.client.component.enums.RedisScriptDebugLevelEnum;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.Codec;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * redis组件
 *
 * @author shixinke
 */
@Component
public class RedisComponent implements RedisExecutor, RedisLock {

    @Resource
    private RedisCommandClient redisCommandClient;

    @Resource
    private RedisLockClient redisLockClient;


    @Override
    public void setCodec(Codec codec) {

    }

    @Override
    public Codec getCodec() {
        return null;
    }

    @Override
    public RLock lock(String key, int timeout, TimeUnit timeUnit) {
        return redisLockClient.lock(key, timeout, timeUnit);
    }

    @Override
    public RLock getLock(String key) {
        return redisLockClient.getLock(key);
    }

    @Override
    public RLock getLock(String key, boolean fair) {
        return redisLockClient.getLock(key, fair);
    }

    @Override
    public ReadWriteLock getReadWriteLock(String key) {
        return redisLockClient.getReadWriteLock(key);
    }

    @Override
    public Lock readLock(String key) {
        return redisLockClient.readLock(key);
    }

    @Override
    public Lock writeLock(String key) {
        return redisLockClient.writeLock(key);
    }

    @Override
    public boolean lock(String key, int timeoutMs) {
        return redisLockClient.lock(key, timeoutMs);
    }

    @Override
    public RFuture<Void> lockAsync(String key, int timeoutMs) {
        return redisLockClient.lockAsync(key, timeoutMs);
    }

    @Override
    public RFuture<Void> lockAsync(String key, int timeout, TimeUnit timeUnit) {
        return redisLockClient.lockAsync(key, timeout, timeUnit);
    }

    @Override
    public boolean unlock(String key) {
        return redisLockClient.unlock(key);
    }

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return redisLockClient.tryLock(key, waitTime, leaseTime, unit);
    }

    @Override
    public boolean forceUnlock(String key) {
        return redisLockClient.forceUnlock(key);
    }

    @Override
    public boolean hDel(String key, List<String> fields) {
        return redisCommandClient.hDel(key, fields);
    }

    @Override
    public boolean hDel(String key, String field) {
        return redisCommandClient.hDel(key, field);
    }

    @Override
    public boolean hExists(String key, String filed) {
        return redisCommandClient.hExists(key, filed);
    }

    @Override
    public <V> V hGet(String key, String field, Class<V> clazz) {
        return redisCommandClient.hGet(key, field, clazz);
    }

    @Override
    public <V> Map<String, V> hGetAll(String key) {
        return redisCommandClient.hGetAll(key);
    }

    @Override
    public long hIncr(String key, String field, int step) {
        return redisCommandClient.hIncr(key, field, step);
    }

    @Override
    public double hIncrByFloat(String key, String field, double step) {
        return redisCommandClient.hIncrByFloat(key, field, step);
    }

    @Override
    public List<String> hKeys(String key) {
        return redisCommandClient.hKeys(key);
    }

    @Override
    public int hLen(String key) {
        return redisCommandClient.hLen(key);
    }

    @Override
    public <V> Map<String, V> hMGet(String key, List<String> fields, Class<V> clazz) {
        return redisCommandClient.hMGet(key, fields, clazz);
    }

    @Override
    public boolean hMSet(String key, Map<String, Object> valuesMap) {
        return redisCommandClient.hMSet(key, valuesMap);
    }

    @Override
    public boolean hSet(String key, String field, Object value) {
        return redisCommandClient.hSet(key, field, value);
    }

    @Override
    public boolean hSetNx(String key, String field, Object value) {
        return redisCommandClient.hSetNx(key, field, value);
    }

    @Override
    public int hStrLen(String key, String field) {
        return redisCommandClient.hStrLen(key, field);
    }

    @Override
    public <V> List<V> hVals(String key, Class<V> clazz) {
        return redisCommandClient.hVals(key, clazz);
    }

    @Override
    public boolean del(String key) {
        return redisCommandClient.del(key);
    }

    @Override
    public boolean exists(String key) {
        return redisCommandClient.exists(key);
    }

    @Override
    public boolean expire(String key, Long seconds) {
        return redisCommandClient.expire(key, seconds);
    }

    @Override
    public boolean expireAt(String key, Integer timestamp) {
        return redisCommandClient.expireAt(key, timestamp);
    }

    @Override
    public boolean pExpire(String key, Long mills) {
        return redisCommandClient.pExpire(key, mills);
    }

    @Override
    public boolean pExpireAt(String key, Long timestampMills) {
        return redisCommandClient.pExpireAt(key, timestampMills);
    }

    @Override
    public Long ttl(String key) {
        return redisCommandClient.ttl(key);
    }

    @Override
    public Long pTtl(String key) {
        return redisCommandClient.pTtl(key);
    }

    @Override
    public boolean persist(String key) {
        return redisCommandClient.persist(key);
    }

    @Override
    public boolean rename(String originalKey, String newKey) {
        return redisCommandClient.rename(originalKey, newKey);
    }

    @Override
    public boolean renameNx(String originalKey, String newKey) {
        return redisCommandClient.renameNx(originalKey, newKey);
    }

    @Override
    public String randomKey() {
        return redisCommandClient.randomKey();
    }

    @Override
    public String type(String key) {
        return redisCommandClient.type(key);
    }

    @Override
    public Iterable<String> keys(String pattern) {
        return redisCommandClient.keys(pattern);
    }

    @Override
    public <V> V blPop(String key, Long timeout) {
        return redisCommandClient.blPop(key, timeout);
    }

    @Override
    public <V> List<V> blPop(List<String> keys, Long timeoutMills) {
        return redisCommandClient.blPop(keys, timeoutMills);
    }

    @Override
    public <V> V brPop(String key, Long timeout) {
        return redisCommandClient.brPop(key, timeout);
    }

    @Override
    public <V> List<V> brPop(List<String> keys, Long timeout) {
        return redisCommandClient.brPop(keys, timeout);
    }

    @Override
    public <V> V brPopLPush(String sourceKey, String destKey) {
        return brPopLPush(sourceKey, destKey);
    }

    @Override
    public <V> V lIndex(String key, int index) {
        return redisCommandClient.lIndex(key, index);
    }

    @Override
    public boolean lInsert(String key, Integer direction, Object pivot, List<Object> values) {
        return redisCommandClient.lInsert(key, direction, pivot, values);
    }

    @Override
    public boolean lInsertBefore(String key, Object pivot, List<Object> values) {
        return redisCommandClient.lInsertBefore(key, pivot, values);
    }

    @Override
    public boolean lInsertAfter(String key, Object pivot, List<Object> values) {
        return redisCommandClient.lInsertAfter(key, pivot, values);
    }

    @Override
    public int lLen(String key) {
        return redisCommandClient.lLen(key);
    }

    @Override
    public <V> V lPop(String key) {
        return redisCommandClient.lPop(key);
    }

    @Override
    public boolean lPush(String key, Object value) {
        return redisCommandClient.lPush(key, value);
    }

    @Override
    public boolean lPush(String key, List<Object> values) {
        return redisCommandClient.lPush(key, values);
    }

    @Override
    public boolean lPushX(String key, Object value) {
        return redisCommandClient.lPushX(key, value);
    }

    @Override
    public <V> List<V> lRange(String key, int start, int end) {
        return redisCommandClient.lRange(key, start, end);
    }

    @Override
    public boolean lRem(String key, Object value, Integer count) {
        return redisCommandClient.lRem(key, value, count);
    }

    @Override
    public boolean lSet(String key, int index, Object value) {
        return redisCommandClient.lSet(key, index, value);
    }

    @Override
    public boolean lTrim(String key, int start, int end) {
        return redisCommandClient.lTrim(key, start, end);
    }

    @Override
    public boolean rPop(String key) {
        return redisCommandClient.rPop(key);
    }

    @Override
    public boolean rPopLPush(String sourceKey, String destKey) {
        return redisCommandClient.rPopLPush(sourceKey, destKey);
    }

    @Override
    public boolean rPush(String key, Object value) {
        return redisCommandClient.rPush(key, value);
    }

    @Override
    public boolean rPushX(String key, Object value) {
        return redisCommandClient.rPushX(key, value);
    }

    @Override
    public <T> void pSubscribe(String pattern, Class<T> type, PatternMessageListener<T> listener) {
        redisCommandClient.pSubscribe(pattern, type, listener);
    }

    @Override
    public <T> void pSubscribe(List<String> patterns, List<Class<T>> types, List<PatternMessageListener<T>> patternMessageListeners) {
        redisCommandClient.pSubscribe(patterns, types, patternMessageListeners);
    }

    @Override
    public void pUnSubscribe(String pattern) {
        redisCommandClient.pUnSubscribe(pattern);
    }

    @Override
    public void pUnSubscribe(List<String> patterns) {
        redisCommandClient.pUnSubscribe(patterns);
    }

    @Override
    public Long publish(String channel, Object message) {
        return redisCommandClient.publish(channel, message);
    }

    @Override
    public <M> void subscribe(String topic, Class<M> type, MessageListener<? extends M> listener) {
        redisCommandClient.subscribe(topic, type, listener);
    }

    @Override
    public <M> void subscribe(List<String> topics, List<Class<M>> clazz, List<MessageListener<? extends M>> messageListeners) {
        redisCommandClient.subscribe(topics, clazz, messageListeners);
    }

    @Override
    public void unsubscribe(String topic) {
        redisCommandClient.unsubscribe(topic);
    }

    @Override
    public void unsubscribe(List<String> topics) {
        redisCommandClient.unsubscribe(topics);
    }

    @Override
    public <V> V eval(String script, int keysNum, List<String> keys, List<Object> args) {
        return redisCommandClient.eval(script, keysNum, keys, args);
    }

    @Override
    public <V> V evalSha(String sha1, int keysNum, List<String> keys, List<Object> args) {
        return redisCommandClient.evalSha(sha1, keysNum, keys, args);
    }

    @Override
    public void debugScript(RedisScriptDebugLevelEnum level) {
        redisCommandClient.debugScript(level);
    }

    @Override
    public boolean scriptExists(String script) {
        return redisCommandClient.scriptExists(script);
    }

    @Override
    public List<Boolean> scriptExists(List<String> scripts) {
        return redisCommandClient.scriptExists(scripts);
    }

    @Override
    public boolean flushScript() {
        return redisCommandClient.flushScript();
    }

    @Override
    public boolean killScript() {
        return redisCommandClient.killScript();
    }

    @Override
    public <V> V loadScript(String script) {
        return redisCommandClient.loadScript(script);
    }

    @Override
    public boolean sAdd(String key, Object value) {
        return redisCommandClient.sAdd(key, value);
    }

    @Override
    public boolean sAdd(String key, List<Object> values) {
        return redisCommandClient.sAdd(key, values);
    }

    @Override
    public int sCard(String key) {
        return redisCommandClient.sCard(key);
    }

    @Override
    public <V> Set<V> sDiff(String sourceKey, String destKey) {
        return redisCommandClient.sDiff(sourceKey, destKey);
    }

    @Override
    public <V> Set<V> sDiff(String sourceKey, List<String> destKeys) {
        return redisCommandClient.sDiff(sourceKey, destKeys);
    }

    @Override
    public boolean sDiffStore(String storeKey, String sourceKey, List<String> destKeys) {
        return redisCommandClient.sDiffStore(storeKey, sourceKey, destKeys);
    }

    @Override
    public <V> Set<V> sInter(String sourceKey, String destKey) {
        return redisCommandClient.sInter(sourceKey, destKey);
    }

    @Override
    public <V> Set<V> sInter(String sourceKey, List<String> destKeys) {
        return redisCommandClient.sInter(sourceKey, destKeys);
    }

    @Override
    public boolean sInterStore(String storeKey, String sourceKey, List<String> destKeys) {
        return redisCommandClient.sInterStore(storeKey, sourceKey, destKeys);
    }

    @Override
    public <V> Set<V> sUnion(String sourceKey, String destKey) {
        return redisCommandClient.sUnion(sourceKey, destKey);
    }

    @Override
    public <V> Set<V> sUnion(String sourceKey, List<String> destKeys) {
        return redisCommandClient.sUnion(sourceKey, destKeys);
    }

    @Override
    public boolean sUnionStore(String storeKey, String sourceKey, List<String> destKeys) {
        return redisCommandClient.sUnionStore(storeKey, sourceKey, destKeys);
    }

    @Override
    public boolean sIsMember(String key, Object member) {
        return redisCommandClient.sIsMember(key, member);
    }

    @Override
    public <V> Set<V> sMembers(String key) {
        return redisCommandClient.sMembers(key);
    }

    @Override
    public boolean sMove(String sourceKey, String destKey, Object value) {
        return redisCommandClient.sMove(sourceKey, destKey, value);
    }

    @Override
    public <V> Set<V> sPop(String key, Integer count) {
        return redisCommandClient.sPop(key, count);
    }

    @Override
    public boolean sRem(String key, Object value) {
        return redisCommandClient.sRem(key, value);
    }

    @Override
    public boolean sRem(String key, List<Object> values) {
        return redisCommandClient.sRem(key, values);
    }

    @Override
    public <V> Set<V> sRandomMember(String key, Integer count) {
        return redisCommandClient.sRandomMember(key, count);
    }

    @Override
    public boolean zAdd(String key, String member, Double score) {
        return redisCommandClient.zAdd(key, member, score);
    }

    @Override
    public boolean zAdd(String key, Map<Object, Double> members) {
        return redisCommandClient.zAdd(key, members);
    }

    @Override
    public int zCard(String key) {
        return redisCommandClient.zCard(key);
    }

    @Override
    public int zCount(String key, double min, double max) {
        return redisCommandClient.zCount(key, min, max);
    }

    @Override
    public double zIncrBy(String key, String member, double step) {
        return redisCommandClient.zIncrBy(key, member, step);
    }

    @Override
    public boolean zInterStore(String storeKey, String sourceKey, List<String> destKeys) {
        return redisCommandClient.zInterStore(storeKey, sourceKey, destKeys);
    }

    @Override
    public long zLexCount(String key, String minMember, String maxMember) {
        return redisCommandClient.zLexCount(key, minMember, maxMember);
    }

    @Override
    public List<Map<String, Double>> zRange(String key, int start, int end) {
        return redisCommandClient.zRange(key, start, end);
    }

    @Override
    public Map<String, Double> zRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count) {
        return redisCommandClient.zRangeByScore(key, start, end, withScores, offset, count);
    }

    @Override
    public List<Map<String, Double>> zRevRange(String key, int start, int end) {
        return redisCommandClient.zRevRange(key, start, end);
    }

    @Override
    public Map<String, Double> zRevRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count) {
        return redisCommandClient.zRevRangeByScore(key, start, end, withScores, offset, count);
    }

    @Override
    public Integer zRank(String key, String member) {
        return redisCommandClient.zRank(key, member);
    }

    @Override
    public Integer zRevRank(String key, String member) {
        return redisCommandClient.zRevRank(key, member);
    }

    @Override
    public boolean zRem(String key, String member) {
        return redisCommandClient.zRem(key, member);
    }

    @Override
    public boolean zRem(String key, List<String> members) {
        return redisCommandClient.zRem(key, members);
    }

    @Override
    public boolean zRemRangeByRank(String key, int start, int end) {
        return redisCommandClient.zRemRangeByRank(key, start, end);
    }

    @Override
    public boolean zRemRangeByScope(String key, double min, double max) {
        return redisCommandClient.zRemRangeByScope(key, min, max);
    }

    @Override
    public Double zScore(String key, String member) {
        return redisCommandClient.zScore(key, member);
    }

    @Override
    public boolean zUnionStore(String storeKey, String sourceKey, List<String> destKeys) {
        return redisCommandClient.zUnionStore(storeKey, sourceKey, destKeys);
    }

    @Override
    public boolean append(String key, String value) {
        return redisCommandClient.append(key, value);
    }

    @Override
    @Deprecated
    public Long bitCount(String key, Long start, Long end) {
        return redisCommandClient.bitCount(key, start, end);
    }

    @Override
    public boolean set(String key, Object value, Long expireSeconds) {
        return redisCommandClient.set(key, value, expireSeconds);
    }

    @Override
    public boolean setEx(String key, Object value, Long expireSeconds) {
        return redisCommandClient.setEx(key, value, expireSeconds);
    }

    @Override
    public boolean pSetEx(String key, Object value, Long expireMills) {
        return redisCommandClient.pSetEx(key, value, expireMills);
    }

    @Override
    public boolean setNx(String key, Object value) {
        return redisCommandClient.setNx(key, value);
    }

    @Override
    public <V> V get(String key, Class<V> clazz) {
        return redisCommandClient.get(key, clazz);
    }

    @Override
    public <V> V getSet(String key, Object value, Class<V> clazz) {
        return redisCommandClient.getSet(key, value, clazz);
    }

    @Override
    public Long incr(String key) {
        return redisCommandClient.incr(key);
    }

    @Override
    public Long incrBy(String key, int step) {
        return redisCommandClient.incrBy(key, step);
    }

    @Override
    public double incrByFloat(String key, float step) {
        return redisCommandClient.incrByFloat(key, step);
    }

    @Override
    public Long decr(String key) {
        return redisCommandClient.decr(key);
    }

    @Override
    public Long decrBy(String key, int step) {
        return redisCommandClient.decrBy(key, step);
    }

    @Override
    public <V> Map<String, V> mGet(List<String> keys, Class<V> clazz) {
        return redisCommandClient.mGet(keys, clazz);
    }

    @Override
    public boolean mSet(Map<String, Object> valueMap) {
        return redisCommandClient.mSet(valueMap);
    }

    @Override
    public boolean mSetNx(Map<String, Object> valueMap) {
        return redisCommandClient.mSetNx(valueMap);
    }

    @Override
    public boolean setRange(String key, int offset, String value) {
        return redisCommandClient.setRange(key, offset, value);
    }

    @Override
    public Long strLen(String key) {
        return redisCommandClient.strLen(key);
    }

    @Override
    public boolean discard(RTransaction transaction) {
        return redisCommandClient.discard(transaction);
    }

    @Override
    public boolean watch(String key, ObjectListener listener) {
        return redisCommandClient.watch(key, listener);
    }

    @Override
    public boolean watch(List<String> keys) {
        return redisCommandClient.watch(keys);
    }

    @Override
    public RTransaction multi() {
        return redisCommandClient.multi();
    }

    @Override
    public boolean unwatch() {
        return redisCommandClient.unwatch();
    }

    @Override
    public boolean exec(RTransaction transaction) {
        return redisCommandClient.exec(transaction);
    }
}
