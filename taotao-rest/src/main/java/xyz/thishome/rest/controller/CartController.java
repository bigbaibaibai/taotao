package xyz.thishome.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.rest.service.CartService;

/**
 * 购物车controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("/get/{userId}")
    public TaotaoResult getCat(@PathVariable Long userId) {
        try {
            return cartService.getCat(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 添加到购物车一件商品
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @param num    商品数量
     * @return
     */
    @RequestMapping("/edit/{userId}")
    public TaotaoResult addCatItem(@PathVariable("userId") Long userId, @RequestParam("itemId") Long itemId,
                                   @RequestParam("num") Integer num, @RequestParam("method") String method) {
        try {
            return cartService.editCatItemNum(userId, itemId, num, method);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    @RequestMapping("/clear/{userId}")
    public TaotaoResult clearCat(@PathVariable Long userId) {
        try {
            return cartService.clearCat(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 批量删除购物车中的购物项
     *
     * @param userId
     * @param itemIds
     * @return
     */
    @RequestMapping("/del/list/{userId}")
    public TaotaoResult delCartItems(@PathVariable Long userId, String itemIds) {
        try {
            return cartService.delCartItemList(userId, itemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
