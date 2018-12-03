package xyz.thishome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.order.pojo.OrderPojo;
import xyz.thishome.order.service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建一个订单
     *
     * @param orderPojo
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public TaotaoResult createOrder(@RequestBody OrderPojo orderPojo) {
        try {
            return orderService.createOrder(orderPojo, orderPojo.getOrderItems(), orderPojo.getOrderShipping());
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
