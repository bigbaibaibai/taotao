package xyz.thishome.portal.service;

import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车service
 */
public interface CartService {

    /**
     * 添加一个商品到购物车中
     *
     * @param itemId 商品id
     * @param num    商品数量
     * @return
     */
    TaotaoResult addCartItem(HttpServletRequest request, Long itemId, Integer num);

    /**
     * 获取购物车列表
     *
     * @param request
     * @return
     */
    List<CartItem> getCartItemList(HttpServletRequest request);

    /**
     * 更新登录用户购物层中某个购物项的个数
     *
     * @param request
     * @param itemId
     * @param num
     * @return
     */
    TaotaoResult editCartItemNum(HttpServletRequest request, Long itemId, Integer num);

    /**
     * 删除用户购物车中某一个商品项
     *
     * @param request
     * @param itemId
     * @return
     */
    TaotaoResult delCartItem(HttpServletRequest request, Long itemId);

    /**
     * 批量删除用户的多个商品项
     *
     * @param request
     * @param itemIds
     * @return
     */
    TaotaoResult delCartItems(HttpServletRequest request, Long[] itemIds);

    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    TaotaoResult clearCart(HttpServletRequest request);
}
