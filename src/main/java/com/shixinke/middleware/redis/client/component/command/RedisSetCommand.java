package com.shixinke.middleware.redis.client.component.command;

import java.util.List;
import java.util.Set;

/**
 * 集合类型操作命令
 * @author shixinke
 */
public interface RedisSetCommand {

    /**
     * 向集合添加值
     * @param key
     * @param value
     * @return
     */
    boolean sAdd(String key, Object value);

    /**
     * 向集合中添加值
     * @param key
     * @param values
     * @return
     */
    boolean sAdd(String key, List<Object> values);

    /**
     * 获取集合的数量
     * @param key
     * @return
     */
    int sCard(String key);

    /**
     * 获取差集
     * @param sourceKey
     * @param destKey
     * @param <V>
     * @return
     */
    <V> Set<V> sDiff(String sourceKey, String destKey);

    /**
     * 获取差集
     * @param sourceKey
     * @param destKeys
     * @param <V>
     * @return
     */
    <V> Set<V> sDiff(String sourceKey, List<String> destKeys);

    /**
     * 获取差集并存储到另外一个集合中
     * @param storeKey
     * @param sourceKey
     * @param destKeys
     * @return
     */
    boolean sDiffStore(String storeKey, String sourceKey, List<String> destKeys);

    /**
     * 获取交集
     * @param sourceKey
     * @param destKey
     * @param <V>
     * @return
     */
    <V> Set<V> sInter(String sourceKey, String destKey);

    /**
     * 获取交集
     * @param sourceKey
     * @param destKeys
     * @param <V>
     * @return
     */
    <V> Set<V> sInter(String sourceKey, List<String> destKeys);

    /**
     * 获取交集并存储到另外一个集合中
     * @param storeKey
     * @param sourceKey
     * @param destKeys
     * @return
     */
    boolean sInterStore(String storeKey, String sourceKey, List<String> destKeys);

    /**
     * 获取并集
     * @param sourceKey
     * @param destKey
     * @param <V>
     * @return
     */
    <V> Set<V> sUnion(String sourceKey, String destKey);

    /**
     * 获取并集
     * @param sourceKey
     * @param destKeys
     * @param <V>
     * @return
     */
    <V> Set<V> sUnion(String sourceKey, List<String> destKeys);

    /**
     * 获取并集并存储到另外一个集合中
     * @param storeKey
     * @param sourceKey
     * @param destKeys
     * @return
     */
    boolean sUnionStore(String storeKey, String sourceKey, List<String> destKeys);

    /**
     * 判断某个值是否为成员
     * @param key
     * @param member
     * @return
     */
    boolean sIsMember(String key, Object member);

    /**
     * 获取所有成员的值
     * @param key
     * @param <V>
     * @return
     */
    <V> Set<V> sMembers(String key);

    /**
     * 将一个成员转移到另一个集合中
     * @param sourceKey
     * @param destKey
     * @param value
     * @return
     */
    boolean sMove(String sourceKey, String destKey, Object value);

    /**
     * 随机返回指定个数的成员，并将它从集合中移除
     * @param key
     * @param count
     * @return
     */
    <V> Set<V> sPop(String key, Integer count);

    /**
     * 删除成员
     * @param key
     * @param value
     * @return
     */
    boolean sRem(String key, Object value);

    /**
     * 删除成员
     * @param key
     * @param values
     * @return
     */
    boolean sRem(String key, List<Object> values);

    /**
     * 随机返回一个成员
     * @param key
     * @param count
     * @param <V>
     * @return
     */
    <V> Set<V> sRandomMember(String key, Integer count);
}
