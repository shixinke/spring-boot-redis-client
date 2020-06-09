package com.shixinke.middleware.redis.client.component.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis有序集合操作命令
 * @author shixinke
 */
public interface RedisSortedSetCommand {

    /**
     * 向集合添加值
     * @param key
     * @param member
     * @param score
     * @return
     */
    boolean zAdd(String key, String member, Double score);

    /**
     * 向集合中添加值
     * @param key
     * @param members
     * @return
     */
    boolean zAdd(String key, Map<Object, Double> members);

    /**
     * 获取集合的数量
     * @param key
     * @return
     */
    int zCard(String key);

    /**
     * 返回指定得分范围的成员个数
     * @param key
     * @param min
     * @param max
     * @return
     */
    int zCount(String key, double min, double max);

    /**
     * 自增
     * @param key
     * @param member
     * @param step
     * @return
     */
    double zIncrBy(String key, String member, double step);

    /**
     * 获取交集并存储到另外一个集合中
     * @param storeKey
     * @param sourceKey
     * @param destKeys
     * @return
     */
    boolean zInterStore(String storeKey, String sourceKey, List<String> destKeys);

    /**
     * 返回在某个范围之间的成员数量
     * @param key
     * @param minMember
     * @param maxMember
     * @return
     */
    long zLexCount(String key, String minMember, String maxMember);

    /**
     * 获取指定范围的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Double>> zRange(String key, int start, int end);


    /**
     * 获取指定范围的成员
     * @param key
     * @param start
     * @param end
     * @param withScores
     * @param offset
     * @param count
     * @return
     */
    Map<String, Double> zRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count);

    /**
     * 获取指定范围的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Double>> zRevRange(String key, int start, int end);

    /**
     * 获取指定范围的成员
     * @param key
     * @param start
     * @param end
     * @param withScores
     * @param offset
     * @param count
     * @return
     */
    Map<String, Double> zRevRangeByScore(String key, int start, int end, boolean withScores, Long offset, Long count);
    /**
     * 获取成员排名　
     * @param key
     * @param member
     * @return
     */
    Integer zRank(String key, String member);

    /**
     * 获取成员排名　
     * @param key
     * @param member
     * @return
     */
    Integer zRevRank(String key, String member);

    /**
     * 删除成员
     * @param key
     * @param member
     * @return
     */
    boolean zRem(String key, String member);

    /**
     * 批量删除成员
     * @param key
     * @param members
     * @return
     */
    boolean zRem(String key, List<String> members);

    /**
     * 按排名删除
     * @param key
     * @param start
     * @param end
     * @return
     */
    boolean zRemRangeByRank(String key, int start, int end);

    /**
     * 按得分删除　
     * @param key
     * @param min
     * @param max
     * @return
     */
    boolean zRemRangeByScope(String key, double min, double max);
    /**
     * 返回成员的得分
     * @param key
     * @param member
     * @return
     */
    Double zScore(String key, String member);
    /**
     * 获取并集并存储到另外一个集合中
     * @param storeKey
     * @param sourceKey
     * @param destKeys
     * @return
     */
    boolean zUnionStore(String storeKey, String sourceKey, List<String> destKeys);
}
