package xyz.thishome.portal.service;

import xyz.thishome.common.pojo.SearchResult;

/**
 * 调用搜索的服务
 */
public interface SearchService {

    /**
     * 搜索，并分页，一页60行
     *
     * @param queryString
     * @param page
     * @return
     */
    SearchResult getList(String queryString, Integer page);

}
