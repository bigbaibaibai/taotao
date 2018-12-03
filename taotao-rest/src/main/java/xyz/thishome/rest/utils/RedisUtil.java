package xyz.thishome.rest.utils;

import org.apache.commons.lang3.StringUtils;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.rest.dao.JedisClient;

import java.util.List;

/**
 * 对redis缓存库操作的工具类
 */
public class RedisUtil {

    /**
     * 添加数据到redis中
     *
     * @param
     * @return
     */
    public static void set(JedisClient client, String key, Object obj) {
        set(client, key, obj, null);
    }

    /**
     * 添加数据到redis中，并设置过期时间
     *
     * @param
     * @return
     */
    public static void set(JedisClient client, String key, Object obj, Integer expire) {
        try {
            String redisString = JsonUtils.objectToJson(obj);
            client.set(key, redisString);
            if (expire != null) {
                client.expire(key, expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询信息并设置过期时间
     *
     * @param client
     * @param key
     */
    public static <T> T get(JedisClient client, String key, Class<T> clazz, Integer expire) {
        try {
            String result = client.get(key);
            if (!StringUtils.isBlank(result)) {
                if (expire != null) {
                    client.expire(key, expire);
                }
                T t = JsonUtils.jsonToPojo(result, clazz);
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询信息
     *
     * @param client
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(JedisClient client, String key, Class<T> clazz) {
        return get(client, key, clazz, null);
    }


    /**
     * 查询信息，返回list集合，并设置过期时间
     *
     * @param client
     * @param key
     */
    public static <T> List<T> getToList(JedisClient client, String key, Class<T> clazz, Integer expire) {
        try {
            String redisString = client.get(key);
            if (!StringUtils.isBlank(redisString)) {
                if (expire != null) {
                    client.expire(key, expire);
                }
                List<T> list = JsonUtils.jsonToList(redisString, clazz);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询信息，返回list集合
     *
     * @param client
     * @param key
     */
    public static <T> List<T> getToList(JedisClient client, String key, Class<T> clazz) {
        return getToList(client, key, clazz, null);
    }

    /**
     * 添加到redis中map类型的数据
     *
     * @param client
     * @param obj
     * @param hkey
     * @param key
     */
    public static void hset(JedisClient client, Object obj, String hkey, String key) {
        try {
            String redisString = JsonUtils.objectToJson(obj);
            client.hset(hkey, key, redisString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询map类型，返回封装对象
     *
     * @param client
     * @param hkey
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T hget(JedisClient client, String hkey, String key, Class<T> clazz) {
        try {
            String result = client.hget(hkey, key);
            if (!StringUtils.isBlank(result)) {
                T t = JsonUtils.jsonToPojo(result, clazz);
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询map类型，返回一个list集合
     *
     * @param client
     * @param hkey
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> hgetToList(JedisClient client, String hkey, String key, Class<T> clazz) {
        try {
            String redisString = client.hget(hkey, key);
            if (!StringUtils.isBlank(redisString)) {
                List<T> list = JsonUtils.jsonToList(redisString, clazz);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
