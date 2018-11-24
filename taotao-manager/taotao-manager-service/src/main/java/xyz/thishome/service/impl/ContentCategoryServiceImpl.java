package xyz.thishome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.EasyUITreeNode;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.mapper.TbContentCategoryMapper;
import xyz.thishome.pojo.TbContentCategory;
import xyz.thishome.pojo.TbContentCategoryExample;
import xyz.thishome.service.ContentCategoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 获取id下的所有子节点
     *
     * @param parent
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCategory(Long parent) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parent);
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);

        List<EasyUITreeNode> nodes = new ArrayList<>();
        for (TbContentCategory category : contentCategories) {
            //封装EasyUITreeNode对象放到list中
            EasyUITreeNode node = new EasyUITreeNode();
            node.setText(category.getName());
            node.setId(category.getId());
            node.setState(category.getIsParent() ? "closed" : "open");
            nodes.add(node);
        }

        return nodes;
    }

    /**
     * 添加一个新的分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult createContentCat(Long parentId, String name) {
        //创建对象，填充属性
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);

        //更新该节点的父节点，isParent为true
        TbContentCategory tbContentCategory2 = new TbContentCategory();
        tbContentCategory2.setIsParent(true);
        tbContentCategory2.setId(parentId);
        contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory2);

        contentCategoryMapper.insertSelective(tbContentCategory);
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public void deleteContentCat(Long parentId, Long id) {
        //把节点的状态设置为2，删除状态，但并未将其真正删除
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setStatus(2);
        contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);

        //判断该节点的父节点是否还有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
        //如果该父节点没有子节点
        if (contentCategories == null && contentCategories.size() == 0) {
            //把父节点的idParent设置为false
            TbContentCategory tbContentCategory2 = new TbContentCategory();
            tbContentCategory.setId(parentId);
            tbContentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory2);
        }
        //如果该节点有子节点
        TbContentCategoryExample example2 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria2 = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> contentCategories2 = contentCategoryMapper.selectByExample(example2);
        //如果该节点有子节点，将所有子节点全部置为删除状态
        if (contentCategories2 != null && contentCategories2.size() != 0) {
            for (TbContentCategory category : contentCategories2) {
                category.setStatus(2);
                contentCategoryMapper.updateByPrimaryKeySelective(category);
            }
        }

    }

    /**
     * 更新指定id分类节点的name
     *
     * @param id
     * @param name
     */
    @Override
    public void updateContentCat(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
    }


}
