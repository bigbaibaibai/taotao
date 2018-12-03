package xyz.thishome.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.mapper.TbContentMapper;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.pojo.TbContentExample;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.service.ContentService;
import xyz.thishome.rest.utils.RedisUtil;

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

        //从缓存中查
        List<TbContent> contents1 = RedisUtil.hgetToList(jedisClient, REDIS_KEY_MAP_INDEX_CONTENT,
                "" + CatId, TbContent.class);
        if (contents1 != null) {
            return contents1;
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(CatId);
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);

        //存到缓存中
        RedisUtil.hset(jedisClient, contents, REDIS_KEY_MAP_INDEX_CONTENT, "" + CatId);

        return contents;
    }
}
