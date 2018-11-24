package xyz.thishome.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.mapper.TbItemCatMapper;
import xyz.thishome.pojo.TbItemCat;
import xyz.thishome.pojo.TbItemCatExample;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.pojo.CatNode;
import xyz.thishome.rest.pojo.CatResult;
import xyz.thishome.rest.service.ItemCatService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Value("${REDSI_KEY_STRING_ITEM_CAT_LIST}")
    private String REDSI_KEY_STRING_ITEM_CAT_LIST;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 获取商品分类列表，以CatResult方式返回
     *
     * @return
     */
    @Override
    public CatResult getItemCatList() {

        //查缓存
        try {
            String redisString = jedisClient.hget(REDSI_KEY_STRING_ITEM_CAT_LIST,"getItemCatList");
            if (!StringUtils.isBlank(redisString)) {
                CatResult catResult = JsonUtils.jsonToPojo(redisString, CatResult.class);
                return catResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0l));

        //添加到缓存中
        try {
            jedisClient.hset(REDSI_KEY_STRING_ITEM_CAT_LIST,
                    JsonUtils.objectToJson(catResult),"getItemCatList");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return catResult;
    }

    /**
     * 通过id获取分类的集合，包含三个属性，name，url，
     * list：子分类列表，子分类如果死父节点，一样包含子子分类，如果为叶子节点，则只需一个字符串
     *
     * @param parentId
     * @return
     */
    private List<?> getCatList(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);

        List<Object> catNodes = new ArrayList<>();
        int num = 0;
        for (TbItemCat itemCat : itemCats) {
            //如果是父节点，需要填充name,ur,list属性（所有子节点）
            if (itemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                //如果是第一级节点
                if (parentId == 0) {
                    //第一级节点Name格式
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                } else {
                    //第二级节点name格式
                    catNode.setName(itemCat.getName());
                }
                //填充url
                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                //填充该父节点的子节点list，使用递归方式，调用本方法
                catNode.setItems(getCatList(itemCat.getId()));
                //添加到集合中
                catNodes.add(catNode);
                num++;
                if (parentId == 0 && num >= 14) {
                    break;
                }

                //如果是叶子节点
            } else {
                //叶子节点直接添加一个字符串即可，无需name,ur,list属性
                catNodes.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
            }

        }

        return catNodes;
    }
}
