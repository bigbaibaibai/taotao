package xyz.thishome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.EasyUITreeNode;
import xyz.thishome.mapper.TbItemCatMapper;
import xyz.thishome.pojo.TbItemCat;
import xyz.thishome.pojo.TbItemCatExample;
import xyz.thishome.service.ItemCatService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 根据parentId查询子节点
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);

        List<EasyUITreeNode> nodes = new ArrayList<>();
        for (TbItemCat itemCat : itemCats) {
            //根据itemCat对象属性，填充EasyUITreeNode对象对应属性
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            //id对应id
            easyUITreeNode.setId(itemCat.getId());
            //text对应name
            easyUITreeNode.setText(itemCat.getName());
            //如果是父节点，则是closed，如果是叶子节点则是open
            easyUITreeNode.setState(itemCat.getIsParent() ? "closed" : "open");
            nodes.add(easyUITreeNode);
        }
        //把所有节点返回到页面
        return nodes;
    }
}
