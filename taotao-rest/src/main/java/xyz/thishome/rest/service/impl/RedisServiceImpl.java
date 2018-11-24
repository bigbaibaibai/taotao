package xyz.thishome.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisClient jedisClient;

    @Value("${REDIS_KEY_MAP_INDEX_CONTENT}")
    private String REDIS_KEY_MAP_INDEX_CONTENT;

    @Override
    public TaotaoResult syncContent(Long catId) {
        try {
            jedisClient.hdel(REDIS_KEY_MAP_INDEX_CONTENT, "getContentByCatId" + catId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

}
