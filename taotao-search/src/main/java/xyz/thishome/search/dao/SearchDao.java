package xyz.thishome.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import xyz.thishome.search.pojo.SearchResult;

/**
 * 搜索商品的dao
 */
public interface SearchDao {

    /**
     * 传入搜索条件对象，返回搜索需要信息
     *
     * @param solrQuery
     * @return
     */
    SearchResult search(SolrQuery solrQuery) throws Exception;
}
