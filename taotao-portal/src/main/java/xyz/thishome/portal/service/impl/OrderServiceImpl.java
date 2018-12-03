package xyz.thishome.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.portal.pojo.OrderPojo;
import xyz.thishome.portal.service.CartService;
import xyz.thishome.portal.service.OrderService;
import xyz.thishome.portal.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_ORDER_URL}")
    private String ORDER_CREATE_ORDER_URL;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Override
    public String createOrder(HttpServletRequest request, OrderPojo orderPojo) {
        try {
            //获取用户id
            TbUser user = (TbUser) request.getAttribute("user");
            orderPojo.setUserId(user.getId());
            orderPojo.setBuyerNick(user.getUsername());
            String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_ORDER_URL, JsonUtils.objectToJson(orderPojo));
            if (!StringUtils.isBlank(json)) {
                TaotaoResult result = TaotaoResult.format(json);
                if (result.getStatus() == 200) {
                    cartService.clearCart(request);
                    return result.getData().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
