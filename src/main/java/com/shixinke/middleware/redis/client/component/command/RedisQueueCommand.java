package com.shixinke.middleware.redis.client.component.command;

import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.PatternMessageListener;

import java.util.List;

/**
 * redis订阅相关的指令
 * @author shixinke
 */
public interface RedisQueueCommand {

    /**
     * 发起订阅
     * @param pattern
     * @param type
     * @param listener
     */
    <T> void pSubscribe(String pattern, Class<T> type, PatternMessageListener<T> listener);

    /**
     * 发起多个订阅
     * @param patterns
     * @param types
     * @param listeners
     */
    <T> void pSubscribe(List<String> patterns, List<Class<T>> types, List<PatternMessageListener<T>> listeners);

    /**
     * 取消订阅
     * @param pattern
     */
    void pUnSubscribe(String pattern);

    /**
     * 取消多个订阅
     * @param patterns
     */
    void pUnSubscribe(List<String> patterns);

    /**
     * 发布一条消息
     * @param channel
     * @param message
     * @return
     */
    Long publish(String channel, Object message);

    /**
     * 订阅
     * @param topic
     * @param type
     * @param listener
     */
    <M> void subscribe(String topic, Class<M> type, MessageListener<? extends M> listener);

    /**
     * 批量订阅
     * @param topics
     * @param clazz
     * @param listeners
     */
    <M> void subscribe(List<String> topics, List<Class<M>> clazz, List<MessageListener<? extends M>> listeners);

    /**
     * 取消订阅
     * @param topic
     */
    void unsubscribe(String topic);

    /**
     * 批量取消订阅
     * @param topics
     */
    void unsubscribe(List<String> topics);
}
