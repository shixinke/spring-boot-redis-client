package com.shixinke.middleware.redis.client.component;

import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 分布式锁组件
 *
 * @author shixinke
 */
@Component
public class RedisLockClient implements RedisLock {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String key, int timeout, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, timeUnit);
        return lock;
    }

    @Override
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    @Override
    public RLock getLock(String key, boolean fair) {
        if (fair) {
            return redissonClient.getFairLock(key);
        }
        return redissonClient.getLock(key);
    }


    @Override
    public ReadWriteLock getReadWriteLock(String key) {
        return redissonClient.getReadWriteLock(key);
    }

    @Override
    public Lock readLock(String key) {
        ReadWriteLock readWriteLock = getReadWriteLock(key);
        return readWriteLock.readLock();
    }

    @Override
    public Lock writeLock(String key) {
        ReadWriteLock readWriteLock = getReadWriteLock(key);
        return readWriteLock.writeLock();
    }


    @Override
    public boolean lock(String key, int timeoutMs) {
        RLock lock = lock(key, timeoutMs, TimeUnit.MILLISECONDS);
        return lock.isLocked();
    }

    @Override
    public RFuture<Void> lockAsync(String key, int timeoutMs) {
        RLock lock = redissonClient.getLock(key);
        return lock.lockAsync(timeoutMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public RFuture<Void> lockAsync(String key, int timeout, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        return lock.lockAsync(timeout, timeUnit);
    }


    @Override
    public boolean unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
        return !lock.isLocked();
    }

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(waitTime, leaseTime, unit);
    }

    @Override
    public boolean forceUnlock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.forceUnlock();
    }


}
