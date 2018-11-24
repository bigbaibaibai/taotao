package xyz.thishome.rest.dao.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.thishome.rest.dao.JedisClient;

/**
 * 单机版，用户测试
 */
public class JedisClientSingle implements JedisClient {

    private JedisPool redisClient;

    public JedisClientSingle(JedisPool redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public String get(String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String set(String key, String val) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.set(key, val);
        } finally {
            jedis.close();
        }
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.hget(hkey, key);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.hset(hkey, key, value);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.incr(key);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long expire(String key, Integer second) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.expire(key, second);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.ttl(key);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long del(String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long hdel(String hkey, String key) {
        Jedis jedis = redisClient.getResource();
        try {
            return jedis.hdel(hkey, key);
        } finally {
            jedis.close();
        }
    }
}
