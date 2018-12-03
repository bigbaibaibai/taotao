package xyz.thishome.order.service;

import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbOrder;
import xyz.thishome.pojo.TbOrderItem;
import xyz.thishome.pojo.TbOrderShipping;

import java.util.List;

/**
 * 订单service
 */
public interface OrderService {

    /**
     * 创建一个订单
     *
     * @param order
     * @param orderItem
     * @param orderShipping
     * @return
     */
    TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping);

}
