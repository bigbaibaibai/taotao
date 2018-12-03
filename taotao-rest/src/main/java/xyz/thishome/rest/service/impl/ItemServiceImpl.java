package xyz.thishome.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.mapper.TbItemDescMapper;
import xyz.thishome.mapper.TbItemMapper;
import xyz.thishome.mapper.TbItemParamItemMapper;
import xyz.thishome.pojo.*;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.service.ItemService;
import xyz.thishome.rest.utils.RedisUtil;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_KEY_ITEM}")
    private String REDIS_KEY_ITEM;

    @Value("${REDIS_EXPIRE_ITEM}")
    private Integer REDIS_EXPIRE_ITEM;

    @Override
    public TaotaoResult getItemBaseInfo(Long itemId) {
        String RedisKey = REDIS_KEY_ITEM + ":" + itemId + ":base";

        //从redis中查询
        TbItem item = RedisUtil.get(jedisClient, RedisKey, TbItem.class, REDIS_EXPIRE_ITEM);
        if (item != null) {
            return TaotaoResult.ok(item);
        }

        //数据库中查询
        try {
            TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
            if (tbItem != null) {
                //添加到redis中
                RedisUtil.set(jedisClient, RedisKey, tbItem, REDIS_EXPIRE_ITEM);
                return TaotaoResult.ok(tbItem);
            }
            return TaotaoResult.build(400, "无此商品");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public TaotaoResult getItemDesc(Long itemId) {
        //组建redis中的键
        String RedisKey = REDIS_KEY_ITEM + ":" + itemId + ":desc";
        //从redis中查询
        TbItemDesc desc = RedisUtil.get(jedisClient, RedisKey, TbItemDesc.class, REDIS_EXPIRE_ITEM);
        if (desc != null) {
            return TaotaoResult.ok(desc);
        }
        //数据库中查询
        try {
            TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
            if (tbItemDesc != null) {
                //添加到redis中
                RedisUtil.set(jedisClient, RedisKey, tbItemDesc, REDIS_EXPIRE_ITEM);
                return TaotaoResult.ok(tbItemDesc);
            }
            return TaotaoResult.build(400, "无此商品描述");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public TaotaoResult getItemParam(Long itemId) {
        //组件redis中的键
        String RedisKey = REDIS_KEY_ITEM + ":" + itemId + ":param";
        //从redis中查询
        TbItemParam param = RedisUtil.get(jedisClient, RedisKey, TbItemParam.class, REDIS_EXPIRE_ITEM);
        if (param != null) {
            return TaotaoResult.ok(param);
        }
        //数据库中查询
        try {
            TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
            criteria.andItemIdEqualTo(itemId);
            List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
            if (itemParamItems != null && itemParamItems.size() > 0) {
                //添加到redis中
                RedisUtil.set(jedisClient, RedisKey, itemParamItems.get(0), REDIS_EXPIRE_ITEM);
                return TaotaoResult.ok(itemParamItems.get(0));
            }
            return TaotaoResult.build(400, "无此商品规格信息");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}
