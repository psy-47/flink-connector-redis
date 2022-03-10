package org.apache.flink.streaming.connectors.redis.common.mapper.row.sink;

import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.connectors.redis.common.config.RedisOptions;
import org.apache.flink.streaming.connectors.redis.common.hanlder.RedisMapperHandler;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisSinkMapper;
import org.apache.flink.table.data.GenericRowData;

import java.util.HashMap;
import java.util.Map;

import static org.apache.flink.streaming.connectors.redis.descriptor.RedisValidator.REDIS_COMMAND;

/**
 * base row redis mapper implement.
 */
public abstract class RowRedisSinkMapper implements RedisSinkMapper<GenericRowData>, RedisMapperHandler {

    private Integer ttl;

    private RedisCommand redisCommand;

    private String fieldColumn;

    private String keyColumn;

    private String valueColumn;

    private boolean putIfAbsent;

    public RowRedisSinkMapper(int ttl, RedisCommand redisCommand, String keyColumn, String valueColumn, boolean putIfAbsent) {
        this.ttl = ttl;
        this.redisCommand = redisCommand;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.putIfAbsent = putIfAbsent;
    }

    public RowRedisSinkMapper(RedisCommand redisCommand, String keyColumn, String fieldColumn, String valueColumn, boolean putIfAbsent, int ttl){
        this.ttl = ttl;
        this.redisCommand = redisCommand;
        this.keyColumn = keyColumn;
        this.fieldColumn = fieldColumn;
        this.valueColumn = valueColumn;
        this.putIfAbsent = putIfAbsent;
    }

    public RowRedisSinkMapper(RedisCommand redisCommand){
        this.redisCommand = redisCommand;
    }

    public RowRedisSinkMapper(RedisCommand redisCommand, Map<String, String> config){
        this.redisCommand = redisCommand;
    }

    public RowRedisSinkMapper(RedisCommand redisCommand, ReadableConfig config){
        this.redisCommand = redisCommand;
        this.ttl = config.get(RedisOptions.TTL);
        this.putIfAbsent = config.get(RedisOptions.PUT_IF_ABSENT);
    }

    @Override
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(redisCommand, ttl, putIfAbsent);
    }

    @Override
    public String getKeyFromData(GenericRowData row, Integer keyIndex) {
        return String.valueOf(row.getField(keyIndex));
    }

    @Override
    public String getValueFromData(GenericRowData row,  Integer valueIndex) {
        return String.valueOf(row.getField(valueIndex));
    }

    @Override
    public String getFieldFromData(GenericRowData row, Integer fieldIndex) {
        return String.valueOf(row.getField(fieldIndex));
    }

    public RedisCommand getRedisCommand() {
        return redisCommand;
    }

    @Override
    public Map<String, String> requiredContext() {
        Map<String, String> require = new HashMap<>();
        require.put(REDIS_COMMAND, getRedisCommand().name());
        return require;
    }

    @Override
    public boolean equals(Object obj) {
        RedisCommand redisCommand = ((RowRedisSinkMapper) obj).redisCommand;
        return this.redisCommand == redisCommand;
    }

}