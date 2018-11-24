package xyz.thishome.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.mapper.TbContentMapper;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.pojo.TbContentExample;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.service.ContentService;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_KEY_MAP_INDEX_CONTENT}")
    private String REDIS_KEY_MAP_INDEX_CONTENT;

    /**
     * 通过商品分类id获取商品列表
     *
     * @param CatId
     * @return
     */
    @Override
    public List<TbContent> getContentByCatId(Long CatId) {

        //从缓存中查，如果抛异常，不能影响执行，Map的key为方法名+参数
        try {
            String redisString = jedisClient.hget(REDIS_KEY_MAP_INDEX_CONTENT, "getContentByCatId" + CatId);
            if (!StringUtils.isBlank(redisString)) {
                List<TbContent> contents = JsonUtils.jsonToList(redisString, TbContent.class);
                return contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(CatId);
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);

        //存到缓存中，如果抛异常，不能影响执行，Map的key为方法名+参数
        try {
            String redisString = JsonUtils.objectToJson(contents);
            jedisClient.hset(REDIS_KEY_MAP_INDEX_CONTENT, "getContentByCatId" + CatId, redisString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }
}
