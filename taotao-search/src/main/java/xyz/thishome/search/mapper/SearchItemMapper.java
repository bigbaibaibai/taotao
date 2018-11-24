package xyz.thishome.search.mapper;

import xyz.thishome.search.pojo.SearchItem;

import java.util.List;

/**
 * 搜索项映射接口
 */
public interface SearchItemMapper {

    /**
     * 从数据库中查询所有商品数据，封装成solr需要的pojo并返回
     *
     * @return
     */
    List<SearchItem> getListSearchItem();
}
