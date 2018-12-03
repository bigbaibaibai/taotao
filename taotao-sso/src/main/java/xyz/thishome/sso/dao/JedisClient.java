package xyz.thishome.sso.dao;

/**
 * 操作redis缓存的客户端
 */
public interface JedisClient {
    /**
     * 通过key获取值
     *
     * @param key
     * @return 成功返回字符串。没有返回null
     */
    String get(String key);

    /**
     * 添加普通类型到redis中
     *
     * @param key
     * @param val
     * @return 成功ok
     */
    String set(String key, String val);

    /**
     * 通过key和map的key获取map的值
     *
     * @param hkey
     * @param key
     * @return 成功返回字符串，失败返回null
     */
    String hget(String hkey, String key);

    /**
     * 添加map类型数据到redis中
     *
     * @param hkey
     * @param key
     * @param value
     * @return 成功返回ok
     */
    Long hset(String hkey, String key, String value);

    /**
     * 把一个key的值自增1
     *
     * @param key
     * @return 成功返回自增后的值，失败返回ERR value is not an integer or out of range
     */
    Long incr(String key);

    /**
     * 设置一个key的过期时间
     *
     * @param key
     * @param second 单位秒
     * @return 成功返回1，失败返回0
     */
    Long expire(String key, Integer second);

    /**
     * 查询一个key的过期时间
     *
     * @param key
     * @return -1永久，-2已失效，否则返回还有多少秒过期
     */
    Long ttl(String key);

    /**
     * 删除一个值类型的数据
     *
     * @param key
     * @return 返回1，表示删除成功。返回0，表示无此对象
     */
    Long del(String key);

    /**
     * 删除一个hash类型数据
     *
     * @param hkey
     * @return
     */
    Long hdel(String hkey, String key);
}
