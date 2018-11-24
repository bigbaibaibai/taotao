package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbItem;

public interface ItemService {
    /**
     * 通过id获取商品
     *
     * @param id
     * @return
     */
    TbItem getItemById(Long id);

    /**
     * 获取所有商品
     *
     * @return
     */
    EasyUIResult getListItem(int page, int rows);

    /**
     * 添加一个商品，填充id,状态码（1正常），上架时间，更新时间，添加到数据库中
     *
     * @param item       表单封装商品信息
     * @param itemParams 商品参数规格JSON
     * @return
     */
    TaotaoResult createItem(TbItem item, String desc, String itemParams);

}
