package com.shixinke.middleware.redis.client.component;

import com.shixinke.middleware.redis.client.component.command.*;
import org.redisson.client.codec.Codec;

/**
 * redis客户端操作类
 * @author shixinke
 */
public interface RedisExecutor extends RedisKeyCommand,
        RedisStringCommand,
        RedisHashCommand,
        RedisListCommand,
        RedisSetCommand,
        RedisSortedSetCommand,
        RedisTransactionCommand,
        RedisScriptCommand,
        RedisQueueCommand {

    /**
     * 设置编码
     * @param codec
     */
    void setCodec(Codec codec);

    /**
     * 获取编码
     * @return
     */
    Codec getCodec();

}
