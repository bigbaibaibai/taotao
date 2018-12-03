package xyz.thishome.portal.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.thishome.portal.pojo.CartItem;
import xyz.thishome.portal.pojo.OrderPojo;
import xyz.thishome.portal.service.CartService;
import xyz.thishome.portal.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String toOrderPage(HttpServletRequest request, Model model) {
        try {
            List<CartItem> cartItemList = cartService.getCartItemList(request);
            model.addAttribute("cartList", cartItemList);
            return "order-cart";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "创建订单出错，请稍后重试");
            return "error/exception";
        }
    }

    @RequestMapping("/create")
    public String createOrder(OrderPojo orderPojo, Model model, HttpServletRequest request) {
        try {
            String orderId = orderService.createOrder(request, orderPojo);
            model.addAttribute("orderId", orderId);
            model.addAttribute("payment", orderPojo.getPayment());
            model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "创建订单出错，请稍后重试");
            return "error/exception";
        }

    }

}
