package xyz.thishome.search.service;

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
}
