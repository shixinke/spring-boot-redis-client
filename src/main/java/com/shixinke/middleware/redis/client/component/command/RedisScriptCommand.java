package com.shixinke.middleware.redis.client.component.command;

import com.shixinke.middleware.redis.client.component.enums.RedisScriptDebugLevelEnum;

import java.util.List;

/**
 * Redis脚本命令
 * @author shixinke
 */
public interface RedisScriptCommand {

    /**
     * 执行脚本　
     * @param script
     * @param keysNum
     * @param keys
     * @param args
     * @param <V>
     * @return
     */
    <V> V eval(String script, int keysNum, List<String> keys, List<Object> args);

    /**
     * 根据给定的 SHA1 校验码，对缓存在服务器中的脚本进行求值
     * @param sha1
     * @param keysNum
     * @param keys
     * @param args
     * @param <V>
     * @return
     */
    <V> V evalSha(String sha1, int keysNum, List<String> keys, List<Object> args);

    /**
     * 脚本调试
     * @param level
     */
    void debugScript(RedisScriptDebugLevelEnum level);

    /**
     * 脚本是否存在
     * @param script
     * @return
     */
    boolean scriptExists(String script);

    /**
     * 脚本是否存在
     * @param scripts
     * @return
     */
    List<Boolean> scriptExists(List<String> scripts);

    /**
     * 清除脚本
     * @return
     */
    boolean flushScript();

    /**
     * 杀死当前运行的脚本
     * @return
     */
    boolean killScript();

    /**
     * 加载脚本
     * @param script
     * @param <V>
     * @return
     */
    <V> V loadScript(String script);
}
