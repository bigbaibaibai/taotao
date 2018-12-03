package xyz.thishome.sso.dao.impl;

import redis.clients.jedis.JedisCluster;
import xyz.thishome.sso.dao.JedisClient;

/**
 * 集群版，用户部署
 */
public class JedisClientCluster implements JedisClient {

    private JedisCluster redisClient;

    public JedisClientCluster(JedisCluster redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public String get(String key) {
        return redisClient.get(key);
    }

    @Override
    public String set(String key, String val) {
        return redisClient.set(key, val);
    }

    @Override
    public String hget(String hkey, String key) {
        return redisClient.hget(hkey, key);
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        return redisClient.hset(hkey, key, value);
    }

    @Override
    public Long incr(String key) {
        return redisClient.incr(key);
    }

    @Override
    public Long expire(String key, Integer second) {
        return redisClient.expire(key, second);
    }

    @Override
    public Long ttl(String key) {
        return redisClient.ttl(key);
    }

    @Override
    public Long del(String key) {
        return redisClient.del(key);
    }

    @Override
    public Long hdel(String hkey, String key) {
        return redisClient.hdel(hkey, key);
    }
}
