package xyz.thishome.portal.service;

import xyz.thishome.portal.pojo.OrderPojo;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单service
 */
public interface OrderService {

    /**
     * 创建一个订单
     *
     * @param orderPojo
     * @return
     */
    String createOrder(HttpServletRequest request, OrderPojo orderPojo);

}
