package xyz.thishome.rest.service;

import xyz.thishome.common.pojo.TaotaoResult;

/**
 * 购物车业务
 */
public interface CartService {

    /**
     * 修改购物车中商品数量，如果style为add则是在原有的商品数量之上进行添加，如果是edit则是把商品数量直接修改成Num值
     *
     * @param userId
     * @param itemId
     * @param num
     * @param method "add"或"edit"
     * @return
     */
    TaotaoResult editCatItemNum(Long userId, Long itemId, Integer num, String method);

    /**
     * 获取购物车
     *
     * @param userId 用户id
     * @return
     */
    TaotaoResult getCat(Long userId);

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    TaotaoResult clearCat(Long userId);

    /**
     * 批量删除购物车中购物项
     *
     * @param userId
     * @param itemIds
     * @return
     */
    TaotaoResult delCartItemList(Long userId, String itemIds);
}
