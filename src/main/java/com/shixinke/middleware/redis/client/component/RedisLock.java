package com.shixinke.middleware.redis.client.component;

import org.redisson.api.RFuture;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * redis分布式锁
 *
 * @author shixinke
 */
public interface RedisLock {
    /**
     * 获取锁
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    RLock lock(String key, int timeout, TimeUnit timeUnit);

    /**
     * 获取锁
     * @param key
     * @return
     */
    RLock getLock(String key);

    /**
     * 获取锁
     * @param key
     * @param fair
     * @return
     */
    RLock getLock(String key, boolean fair);



    /**
     * 获取读写锁
     * @param key
     * @return
     */
    ReadWriteLock getReadWriteLock(String key);

    /**
     * 获取读锁
     * @param key
     * @return
     */
    Lock readLock(String key);

    /**
     * 获取写锁
     * @param key
     * @return
     */
    Lock writeLock(String key);



    /**
     * 加锁
     * @param key
     * @param timeoutMs
     * @return
     */
    boolean lock(String key, int timeoutMs);

    /**
     * 加锁
     * @param key
     * @param timeoutMs
     * @return
     */
    RFuture<Void> lockAsync(String key, int timeoutMs);

    /**
     * 获取锁
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    RFuture<Void> lockAsync(String key, int timeout, TimeUnit timeUnit);



    /**
     * 解锁
     * @param key
     * @return
     */
    boolean unlock(String key);

    /**
     * 获取锁
     * @param key
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    /**
     * 强制解锁
     * @param key
     * @return
     */
    boolean forceUnlock(String key);
}
