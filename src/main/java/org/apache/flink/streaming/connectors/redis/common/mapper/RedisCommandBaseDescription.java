package org.apache.flink.streaming.connectors.redis.common.mapper;

import org.apache.flink.calcite.shaded.com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * @author jeff.zou
 * @date 2022/3/9.14:55
 */
public class RedisCommandBaseDescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private RedisCommand redisCommand;

    public RedisCommandBaseDescription(RedisCommand redisCommand) {
        Preconditions.checkNotNull(redisCommand, "redis command type cant be null!!!");
        this.redisCommand = redisCommand;
    }

    public RedisCommand getRedisCommand() {
        return redisCommand;
    }
}