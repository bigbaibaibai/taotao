package xyz.thishome.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.mapper.TbItemMapper;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.rest.dao.JedisClient;
import xyz.thishome.rest.pojo.CartItem;
import xyz.thishome.rest.service.CartService;
import xyz.thishome.rest.utils.RedisUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Value("${REDIS_KEY_CART}")
    private String REDIS_KEY_CART;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemMapper itemMapper;


    public TaotaoResult editCatItemNum(Long userId, Long itemId, Integer num, String method) {
        //查询该用户的购物车
        List<CartItem> catItems = (List<CartItem>) getCat(userId).getData();
        if (catItems != null) {
            //如果购物中有该商品，加数量
            for (CartItem catItem1 : catItems) {
                if (catItem1.getId().compareTo(itemId) == 0) {
                    if (method.equals("add")) {
                        catItem1.setNum(catItem1.getNum() + num);
                    } else if (method.equals("edit")) {
                        catItem1.setNum(num);
                    }
                    RedisUtil.set(jedisClient, REDIS_KEY_CART + ":" + userId, catItems);
                    return TaotaoResult.ok(catItems);
                }
            }
        }
        //获取商品信息，并封装层购物车项
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        CartItem catItem = new CartItem();
        catItem.setId(item.getId());
        catItem.setImage(item.getImage());
        catItem.setPrice(item.getPrice());
        catItem.setTitle(item.getTitle());
        catItem.setNum(num);
        //添加到redis中
        //如果该用户没有购物车，创建购物车，并添加购物项
        if (catItems == null) {
            catItems = new ArrayList<>();
        }
        //否则，添加该商品到购物车
        catItems.add(catItem);
        RedisUtil.set(jedisClient, REDIS_KEY_CART + ":" + userId, catItems);
        return TaotaoResult.ok(catItems);
    }

    @Override
    public TaotaoResult getCat(Long userId) {
        List<CartItem> catItems = RedisUtil.getToList(jedisClient, REDIS_KEY_CART + ":" + userId, CartItem.class);
        return TaotaoResult.ok(catItems);
    }

    @Override
    public TaotaoResult clearCat(Long userId) {
        return TaotaoResult.ok(jedisClient.del(REDIS_KEY_CART + ":" + userId));
    }


    public TaotaoResult delCartItemList(Long userId, String itemIds) {
        List<Long> longs = JsonUtils.jsonToList(itemIds, Long.class);
        return delCartItemList(userId, longs);
    }

    private TaotaoResult delCartItemList(Long userId, List<Long> itemIds) {
        //查询该用户的购物车
        List<CartItem> catItems = (List<CartItem>) getCat(userId).getData();
        for (Long itemId : itemIds) {
            for (CartItem catItem1 : catItems) {
                if (catItem1.getId().compareTo(itemId) == 0) {
                    catItems.remove(catItem1);
                    break;
                }
            }
        }
        RedisUtil.set(jedisClient, REDIS_KEY_CART + ":" + userId, catItems);
        return TaotaoResult.ok(catItems);
    }
}
