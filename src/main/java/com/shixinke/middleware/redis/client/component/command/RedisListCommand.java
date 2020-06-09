package com.shixinke.middleware.redis.client.component.command;

import java.util.List;

/**
 * List类型的命令
 * @author shixinke
 */
public interface RedisListCommand {
    /**
     * 阻塞式弹出元素
     * @param key
     * @param timeout
     * @return
     */
    <V> V blPop(String key, Long timeout);

    /**
     * 批量阻塞式弹出元素
     * @param keys
     * @param timeoutMills
     * @return
     */
    <V> List<V> blPop(List<String> keys, Long timeoutMills);

    /**
     * 阻塞式弹出元素(从右边弹出)
     * @param key
     * @param timeout
     * @return
     */
    <V> V brPop(String key, Long timeout);

    /**
     * 批量阻塞式弹出元素(从右边弹出)
     * @param keys
     * @param timeout
     * @return
     */
    <V> List<V> brPop(List<String> keys, Long timeout);

    /**
     * 从一个队列中弹出再加入到另外一个队列中
     * @param sourceKey
     * @param destKey
     * @return
     */
    <V> V brPopLPush(String sourceKey, String destKey);

    /**
     * 获取指定索引的值
     * @param key
     * @param index
     * @param <V>
     * @return
     */
    <V> V lIndex(String key, int index);

    /**
     * 插入
     * @param key
     * @param direction
     * @param pivot
     * @param values
     * @return
     */
    boolean lInsert(String key, Integer direction, Object pivot, List<Object> values);

    /**
     * 在指定值的前面插入
     * @param key
     * @param pivot
     * @param values
     * @return
     */
    boolean lInsertBefore(String key, Object pivot, List<Object> values);

    /**
     * 在指定值的后面插入
     * @param key
     * @param pivot
     * @param values
     * @return
     */
    boolean lInsertAfter(String key, Object pivot, List<Object> values);

    /**
     * 获取队列长度
     * @param key
     * @return
     */
    int lLen(String key);

    /**
     * 从左边弹出一个值
     * @param key
     * @return
     */
    <V> V lPop(String key);

    /**
     * 从右边插入队列
     * @param key
     * @param value
     * @return
     */
    boolean lPush(String key, Object value);

    /**
     * 批量插入
     * @param key
     * @param values
     * @return
     */
    boolean lPush(String key, List<Object> values);

    /**
     * 从队列存在时右边插入队列
     * @param key
     * @param value
     * @return
     */
    boolean lPushX(String key, Object value);

    /**
     * 获取批定范围的队列的值
     * @param key
     * @param start
     * @param end
     * @param <V>
     * @return
     */
    <V> List<V> lRange(String key, int start, int end);

    /**
     * 删除值
     * @param key
     * @param value
     * @param count
     * @return
     */
    boolean lRem(String key, Object value, Integer count);

    /**
     * 设置某个位置的值
     * @param key
     * @param index
     * @param value
     * @return
     */
    boolean lSet(String key, int index, Object value);

    /**
     * 截取队列
     * @param key
     * @param start
     * @param end
     * @return
     */
    boolean lTrim(String key, int start, int end);

    /**
     * 右边弹出
     * @param key
     * @return
     */
    boolean rPop(String key);

    /**
     * 从一个队列中弹出再加入到另外一个队列中
     * @param sourceKey
     * @param destKey
     * @return
     */
    boolean rPopLPush(String sourceKey, String destKey);

    /**
     * 从队尾加上值
     * @param key
     * @param value
     * @return
     */
    boolean rPush(String key, Object value);

    /**
     * 从队列存在时右边插入队列
     * @param key
     * @param value
     * @return
     */
    boolean rPushX(String key, Object value);
}
