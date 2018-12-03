package xyz.thishome.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.portal.pojo.CartItem;
import xyz.thishome.portal.service.CartService;
import xyz.thishome.portal.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CART_ITEM_EDIT_URL}")
    private String REST_CART_ITEM_EDIT_URL;

    @Value("${REST_CART_ITEM_LIST_URL}")
    private String REST_CART_ITEM_LIST_URL;

    @Value("${REST_CART_ITEM_DEL_URL}")
    private String REST_CART_ITEM_DEL_URL;

    @Value("${REST_CART_CLEAR_URL}")
    private String REST_CART_CLEAR_URL;

    @Autowired
    private UserService userService;

    @Override
    public TaotaoResult addCartItem(HttpServletRequest request, Long itemId, Integer num) {
        return editCartItemNum(request, itemId, num, "add");
    }

    @Override
    public TaotaoResult editCartItemNum(HttpServletRequest request, Long itemId, Integer num) {
        return editCartItemNum(request, itemId, num, "edit");
    }

    @Override
    public TaotaoResult delCartItem(HttpServletRequest request, Long itemId) {
        return delCartItems(request, new Long[]{itemId});
    }

    @Override
    public TaotaoResult delCartItems(HttpServletRequest request, Long[] itemIds) {
        //获取用户id
        //封装属性
        Map<String, String> param = new HashMap<>();
        //把商品id转化为集合转化为json字符串
        List<Long> itemIdsList = Arrays.asList(itemIds);
        String json = JsonUtils.objectToJson(itemIdsList);
        param.put("itemIds", json);
        String doGet = HttpClientUtil.doGet(REST_BASE_URL + REST_CART_ITEM_DEL_URL + getUserId(request), param);
        if (!StringUtils.isBlank(doGet)) {
            TaotaoResult result = TaotaoResult.formatToPojo(doGet, CartItem.class);
            if (result.getStatus() == 200)
                return result;
        }
        return TaotaoResult.build(400, "删除失败");
    }

    @Override
    public TaotaoResult clearCart(HttpServletRequest request) {
        //获取用户id
        String doGet = HttpClientUtil.doGet(REST_BASE_URL + REST_CART_CLEAR_URL + getUserId(request));
        if (!StringUtils.isBlank(doGet)) {
            TaotaoResult result = TaotaoResult.formatToPojo(doGet, CartItem.class);
            if (result.getStatus() == 200)
                return result;
        }
        return TaotaoResult.build(400, "清空失败");
    }

    /**
     * 修改购物车中商品数量，如果style为add则是在原有的商品数量之上进行添加，如果是edit则是把商品数量直接修改成Num值
     *
     * @param request
     * @param itemId
     * @param num
     * @param method
     * @return
     */
    private TaotaoResult editCartItemNum(HttpServletRequest request, Long itemId, Integer num, String method) {
        //获取用户id

        //封装属性
        Map<String, String> param = new HashMap<>();
        param.put("itemId", itemId + "");
        param.put("num", num + "");
        param.put("method", method);
        String doGet = HttpClientUtil.doGet(REST_BASE_URL + REST_CART_ITEM_EDIT_URL + getUserId(request), param);
        if (!StringUtils.isBlank(doGet)) {
            TaotaoResult result = TaotaoResult.formatToPojo(doGet, CartItem.class);
            if (result.getStatus() == 200)
                return result;
        }
        return TaotaoResult.build(400, "添加失败");
    }


    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request) {
        try {
            //获取登录用户id
            String doGet = HttpClientUtil.doGet(REST_BASE_URL + REST_CART_ITEM_LIST_URL + getUserId(request));
            if (!StringUtils.isBlank(doGet)) {
                TaotaoResult result = TaotaoResult.formatToList(doGet, CartItem.class);
                return (List<CartItem>) result.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Long getUserId(HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("user");
        return user.getId();
    }

}
