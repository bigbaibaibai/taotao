package xyz.thishome.portal.pojo;

import xyz.thishome.pojo.TbOrder;
import xyz.thishome.pojo.TbOrderItem;
import xyz.thishome.pojo.TbOrderShipping;

import java.util.List;

//用于接收请求参数的pojo
public class OrderPojo extends TbOrder {

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
