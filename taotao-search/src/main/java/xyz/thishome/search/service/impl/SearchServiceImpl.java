package xyz.thishome.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.SearchResult;
import xyz.thishome.search.dao.SearchDao;
import xyz.thishome.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        solrQuery.setQuery(queryString);
        //设置默认搜索的列
        solrQuery.set("df", "item_keywords");
        //设置高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //获取查询结果
        SearchResult searchResult = searchDao.search(solrQuery);
        //计算总页数
        Long pageCount = searchResult.getRecordCount() / rows;
        if (searchResult.getRecordCount() % rows != 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page.longValue());
        return searchResult;
    }
}
