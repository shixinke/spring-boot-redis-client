package com.shixinke.middleware.redis.client.component.command;

/**
 * redis键操作命令
 * @author shixinke
 */
public interface RedisKeyCommand {
    /**
     * 删除键
     * @param key
     * @return
     */
    boolean del(String key);

    /**
     * 判断键是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 设置过期时间
     * @param key
     * @param seconds
     * @return
     */
    boolean expire(String key, Long seconds);

    /**
     * 设置过期时间
     * @param key
     * @param timestamp
     * @return
     */
    boolean expireAt(String key, Integer timestamp);

    /**
     * 设置过期时间(毫秒为单位)
     * @param key
     * @param mills
     * @return
     */
    boolean pExpire(String key, Long mills);

    /**
     * 设置过期时间(毫秒为单位)
     * @param key
     * @param timestampMills
     * @return
     */
    boolean pExpireAt(String key, Long timestampMills);

    /**
     * 获取一个键的过期时间(秒为单位)
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 获取一个键的过期时间(毫秒为单位)
     * @param key
     * @return
     */
    Long pTtl(String key);

    /**
     * 将一个键设置为永久不过期
     * @param key
     * @return
     */
    boolean persist(String key);

    /**
     * 给指定键重命名
     * @param originalKey
     * @param newKey
     * @return
     */
    boolean rename(String originalKey, String newKey);

    /**
     * 给指一个不存在的键重命名
     * @param originalKey
     * @param newKey
     * @return
     */
    boolean renameNx(String originalKey, String newKey);

    /**
     * 返回一个随机键
     * @return
     */
    String randomKey();

    /**
     * 获取一个键的数据类型
     * @param key
     * @return
     */
    String type(String key);

    /**
     * 根据模式返回键名
     * @param pattern
     * @return
     */
    Iterable<String> keys(String pattern);


}
