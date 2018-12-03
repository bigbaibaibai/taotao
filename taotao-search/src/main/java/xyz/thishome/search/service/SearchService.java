package xyz.thishome.search.service;


import xyz.thishome.common.pojo.SearchResult;

/**
 * 搜索服务
 */
public interface SearchService {
    /**
     * 传入搜索条件，页码，每页条数，返回搜索信息
     *
     * @param queryString
     * @param page
     * @param rows
     * @return
     */
    SearchResult search(String queryString, Integer page, Integer rows) throws Exception;

}
