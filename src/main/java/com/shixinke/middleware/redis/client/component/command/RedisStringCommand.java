package com.shixinke.middleware.redis.client.component.command;

import java.util.List;
import java.util.Map;

/**
 * redis中String类型的操作命令
 * @author shixinke
 */
public interface RedisStringCommand {
    /**
     * 给键追加值
     * @param key
     * @param value
     * @return
     */
    boolean append(String key, String value);

    /**
     * 统计字符串被设置为1的bit数
     * @param key
     * @param start
     * @param end
     * @return
     */
    Long bitCount(String key, Long start, Long end);

    /**
     * 设置值
     * @param key
     * @param value
     * @param expireSeconds
     * @return
     */
    boolean set(String key, Object value, Long expireSeconds);

    /**
     * 设置值并设置过期时间(秒为单位)
     * @param key
     * @param value
     * @param expireSeconds
     * @return
     */
    boolean setEx(String key, Object value, Long expireSeconds);

    /**
     * 设置值并设置过期时间(毫秒为单位)
     * @param key
     * @param value
     * @param expireMills
     * @return
     */
    boolean pSetEx(String key, Object value, Long expireMills);

    /**
     * 当不存在时设置键的值
     * @param key
     * @param value
     * @return
     */
    boolean setNx(String key, Object value);

    /**
     * 获取缓存值
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    <V> V get(String key, Class<V> clazz);

    /**
     * 设置值并返回其原来的值
     * @param key
     * @param value
     * @param clazz
     * @param <V>
     * @return
     */
    <V> V getSet(String key, Object value, Class<V> clazz);

    /**
     * 自增操作
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 按固定步长增长
     * @param key
     * @param step
     * @return
     */
    Long incrBy(String key, int step);

    /**
     * 按指定步长增长(步长为浮点数)
     * @param key
     * @param step
     * @return
     */
    double incrByFloat(String key, float step);

    /**
     * 自减
     * @param key
     * @return
     */
    Long decr(String key);

    /**
     * 按固定步长自减
     * @param key
     * @param step
     * @return
     */
    Long decrBy(String key, int step);

    /**
     * 批量获取值
     * @param keys
     * @param clazz
     * @param <V>
     * @return
     */
    <V> Map<String, V> mGet(List<String> keys, Class<V> clazz);

    /**
     * 批量设置值
     * @param valueMap
     * @return
     */
    boolean mSet(Map<String, Object> valueMap);

    /**
     * 批量设置值(当不存在时才设置成功)
     * @param valueMap
     * @return
     */
    boolean mSetNx(Map<String, Object> valueMap);

    /**
     * 设置指定位置的值
     * @param key
     * @param offset
     * @param value
     * @return
     */
    boolean setRange(String key, int offset, String value);

    /**
     * 获取值的长度
     * @param key
     * @return
     */
    Long strLen(String key);
}
