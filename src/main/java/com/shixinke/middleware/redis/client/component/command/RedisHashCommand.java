package com.shixinke.middleware.redis.client.component.command;

import java.util.List;
import java.util.Map;

/**
 * hash类型操作命令
 * @author shixinke
 */
public interface RedisHashCommand {

    /**
     * 删除成员
     * @param key
     * @param fields
     * @return
     */
    boolean hDel(String key, List<String> fields);

    /**
     * 删除成员
     * @param key
     * @param field
     * @return
     */
    boolean hDel(String key, String field);

    /**
     * 判断某个成员是否存在
     * @param key
     * @param filed
     * @return
     */
    boolean hExists(String key, String filed);

    /**
     * 获取成员值
     * @param key
     * @param field
     * @param clazz
     * @param <V>
     * @return
     */
    <V> V hGet(String key, String field, Class<V> clazz);

    /**
     * 获取所有成员的值
     * @param key
     * @param <V>
     * @return
     */
    <V> Map<String, V> hGetAll(String key);

    /**
     * 成员自增
     * @param key
     * @param field
     * @param step
     * @return
     */
    long hIncr(String key, String field, int step);

    /**
     * 成员自减
     * @param key
     * @param field
     * @param step
     * @return
     */
    double hIncrByFloat(String key, String field, double step);

    /**
     * 获取所有成员的名称
     * @param key
     * @return
     */
    List<String> hKeys(String key);

    /**
     * 获取成员个数
     * @param key
     * @return
     */
    int hLen(String key);

    /**
     * 批量获取成员
     * @param key
     * @param fields
     * @param <V>
     * @return
     */
    <V> Map<String, V> hMGet(String key, List<String> fields, Class<V> clazz);

    /**
     * 批量设置值
     * @param key
     * @param valuesMap
     * @return
     */
    boolean hMSet(String key, Map<String, Object> valuesMap);

    /**
     * 设置成员的值
     * @param key
     * @param field
     * @param value
     * @return
     */
    boolean hSet(String key, String field, Object value);

    /**
     * 设置不存在的成员的值
     * @param key
     * @param field
     * @param value
     * @return
     */
    boolean hSetNx(String key, String field, Object value);

    /**
     * 获取指定成员的长度
     * @param key
     * @param field
     * @return
     */
    int hStrLen(String key, String field);

    /**
     * 获取所有的成员值
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    <V> List<V> hVals(String key, Class<V> clazz);
}
