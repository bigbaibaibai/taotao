package xyz.thishome.rest.service;

import xyz.thishome.common.pojo.TaotaoResult;

/**
 * 商品信息表的操作
 */
public interface ItemService {

    /**
     * 查询商品的基本信息
     *
     * @param itemId
     * @return
     */
    TaotaoResult getItemBaseInfo(Long itemId);

    /**
     * 查询商品的描述信息
     *
     * @param itemId
     * @return
     */
    TaotaoResult getItemDesc(Long itemId);

    /**
     * 查询商品规格参数
     *
     * @param itemId
     * @return
     */
    TaotaoResult getItemParam(Long itemId);
}
