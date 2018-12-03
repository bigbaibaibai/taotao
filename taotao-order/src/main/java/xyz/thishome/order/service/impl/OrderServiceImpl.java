package xyz.thishome.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.mapper.TbOrderItemMapper;
import xyz.thishome.mapper.TbOrderMapper;
import xyz.thishome.mapper.TbOrderShippingMapper;
import xyz.thishome.order.dao.JedisClient;
import xyz.thishome.order.service.OrderService;
import xyz.thishome.pojo.TbOrder;
import xyz.thishome.pojo.TbOrderItem;
import xyz.thishome.pojo.TbOrderShipping;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_KEY_ORDER_GEN}")
    private String REDIS_KEY_ORDER_GEN;

    @Value("${ORDER_GEN_INIT}")
    private String ORDER_GEN_INIT;

    @Value("${REDIS_KEY_DATAIL_GEN}")
    private String REDIS_KEY_DATAIL_GEN;

    @Override
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {

        //补全order
        //获取订单编号
        String orderId = jedisClient.incr(REDIS_KEY_ORDER_GEN) + "";
        if (orderId.equals("1")) {
            jedisClient.set(REDIS_KEY_ORDER_GEN, ORDER_GEN_INIT);
            orderId = ORDER_GEN_INIT;
        }
        order.setOrderId(orderId);
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        //0：未评价，1：已评价
        order.setBuyerRate(0);
        orderMapper.insert(order);

        //补全orderItemList
        for (TbOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderId);
            //生成id
            orderItem.setId(jedisClient.incr(REDIS_KEY_DATAIL_GEN) + "");
            orderItemMapper.insert(orderItem);
        }
        //补全orderShipping物流信息
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShipping.setOrderId(orderId);
        orderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderId);
    }
}
