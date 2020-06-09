package com.shixinke.middleware.redis.client.component.enums;

/**
 * redis脚本Debug级别
 * @author shixinke
 */
public enum  RedisScriptDebugLevelEnum {
    /**
     * 打开非阻塞异步调试模式
     */
    YES("YES"),
    /**
     * 打开阻塞同步调试模式
     */
    SYNC("SYNC"),
    /**
     * 关闭调试
     */
    NO("NO");
    private String value;
    RedisScriptDebugLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
