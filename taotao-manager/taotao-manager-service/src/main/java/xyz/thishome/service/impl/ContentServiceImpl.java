package xyz.thishome.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.mapper.TbContentMapper;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.pojo.TbContentExample;
import xyz.thishome.service.ContentService;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 获取内容列表，分页
     *
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIResult getContentList(Integer page, Integer rows, Long categoryId) {
        PageHelper.startPage(page, rows);
        TbContentExample example = null;
        //如果包含查询条件
        if (categoryId != null && categoryId != 0) {
            example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo pageInfo = new PageInfo(contents);

        EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());

        return easyUIResult;
    }

    /**
     * 插入商品内容信息
     *
     * @param content
     * @return
     */
    @Override
    public TaotaoResult insertContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insertSelective(content);
        return TaotaoResult.ok();
    }
}
