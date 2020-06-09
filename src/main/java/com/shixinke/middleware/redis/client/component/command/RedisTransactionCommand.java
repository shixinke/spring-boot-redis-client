package com.shixinke.middleware.redis.client.component.command;

import org.redisson.api.ObjectListener;
import org.redisson.api.RTransaction;

import java.util.List;

/**
 * redis事务相关的命令
 * @author shixinke
 */
public interface RedisTransactionCommand {

    /**
     * 丢弃multi之后发的命令
     * @param transaction
     * @return
     */
    boolean discard(RTransaction transaction);

    /**
     * 标记所有指定的key 被监视起
     * @param key
     * @return
     */
    boolean watch(String key, ObjectListener listener);

    /**
     * 监视多个key
     * @param keys
     * @return
     */
    boolean watch(List<String> keys);

    /**
     * 开启一个事务
     * @return
     */
    RTransaction multi();

    /**
     * 取消监视
     * @return
     */
    boolean unwatch();

    /**
     * 执行事务
     * @param transaction
     * @return
     */
    boolean exec(RTransaction transaction);
}
