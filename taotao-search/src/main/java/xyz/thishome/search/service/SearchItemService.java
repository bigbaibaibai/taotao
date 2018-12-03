package xyz.thishome.search.service;

import xyz.thishome.common.pojo.SearchItem;
import xyz.thishome.common.pojo.TaotaoResult;

/**
 * 搜索商品内容有关的服务
 */
public interface SearchItemService {

    /**
     * 把所有的商品导入solr服务器中
     *
     * @return
     */
    TaotaoResult importAllItems();

    /**
     * 导入单个商品到搜索服务器中
     *
     * @return
     */
    TaotaoResult importItem(SearchItem searchItem);

    /**
     * 批量删除搜索索引
     *
     * @return
     */
    TaotaoResult delItemList(String idsJson);

    /**
     * 删除单个索引
     *
     * @param id
     * @return
     */
    TaotaoResult delItem(String id);
}
