package xyz.thishome.portal.service;

import xyz.thishome.pojo.TbItem;

/**
 * 获取商品信息service，远程调用rest服务
 */
public interface ItemService {

    /**
     * 通过id获取商品信息
     *
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    /**
     * 通过商品id获取详细介绍
     *
     * @param itemId
     * @return
     */
    String getItemDescById(Long itemId);

    /**
     * 通过商品id获取商品规格说明
     *
     * @param itemId
     * @return
     */
    String getItemParamById(Long itemId);
}
