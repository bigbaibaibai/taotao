package xyz.thishome.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.portal.pojo.CartItem;
import xyz.thishome.portal.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车相关controller
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     *
     * @param itemId
     * @param request
     * @param num
     * @return
     */
    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, HttpServletRequest request,
                              @RequestParam(defaultValue = "1") Integer num) {
        try {
            cartService.addCartItem(request, itemId, num);
            return "redirect:/cart/success.html";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/update/{itemId}")
    @ResponseBody
    public String updateCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request) {
        try {
            cartService.editCartItemNum(request, itemId, num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 跳转到成功页面
     *
     * @return
     */
    @RequestMapping("/success")
    public String addSuccess() {
        return "cartSuccess";
    }

    /**
     * 获取登录用户的购物车
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/cart")

    public String getCart(HttpServletRequest request, Model model) {
        try {
            List<CartItem> itemList = cartService.getCartItemList(request);
            model.addAttribute("cartList", itemList);
            return "cart";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", ExceptionUtil.getStackTrace(e));
            return "error/exception";
        }
    }

    /**
     * 删除某一项
     *
     * @param itemId
     * @param request
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String delCartItem(@PathVariable Long itemId, HttpServletRequest request) {
        try {
            cartService.delCartItem(request, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/cart.html";
    }

    /**
     * 批量删除
     *
     * @param itemId
     * @param request
     * @param itemIds
     * @return
     */
    @RequestMapping("/deletelist")
    public String delCartItem(@PathVariable Long itemId, HttpServletRequest request, Long[] itemIds) {
        try {
            cartService.delCartItems(request, itemIds);
        } catch (Exception e) {
            e.printStackTrace();
            TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return "redirect:" + request.getRequestURL();
    }

}
