package xyz.thishome.search.dao;

import xyz.thishome.common.pojo.SearchItem;

import java.util.List;

/**
 * 搜索商品信息操作
 */
public interface SearchItemDao {

    /**
     * 批量添加商品信息到搜索索引中
     *
     * @param listSearchItem
     */
    void importListItems(List<SearchItem> listSearchItem) throws Exception;

    /**
     * 添加单个商品信息到搜索索引中
     *
     * @param searchItem
     */
    void importItem(SearchItem searchItem) throws Exception;

    /**
     * 批量删除索引
     *
     * @throws Exception
     */
    void delItemList(List<String> ids) throws Exception;

    /**
     * 删除单个索引
     *
     * @param id
     * @throws Exception
     */
    void delItem(String id) throws Exception;

}
