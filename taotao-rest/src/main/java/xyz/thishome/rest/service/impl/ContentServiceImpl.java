package xyz.thishome.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.mapper.TbContentMapper;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.pojo.TbContentExample;
import xyz.thishome.rest.service.ContentService;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 通过商品分类id获取商品列表
     *
     * @param CatId
     * @return
     */
    @Override
    public List<TbContent> getContentByCatId(Long CatId) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(CatId);
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
        return contents;
    }
}
