package xyz.thishome.search.dao.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.thishome.common.pojo.SearchItem;
import xyz.thishome.common.pojo.SearchResult;
import xyz.thishome.search.dao.SearchDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery solrQuery) throws Exception {
        //创建返回结果对象
        SearchResult searchResult = new SearchResult();
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取出商品信息，一个list集合
        SolrDocumentList results = response.getResults();
        //取出查询总记录数，设置到返回结果中
        searchResult.setRecordCount(results.getNumFound());
        //取出所有高亮信息
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        ArrayList<SearchItem> searchItems = new ArrayList<>();
        for (SolrDocument document : results) {
            //根据搜索信息封装商品信息对象
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) document.get("id"));

            //取出高亮显示信息title
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) document.get("item_title");
            }
            searchItem.setTitle(title);
            searchItem.setCategoryName((String) document.get("item_category_name"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setPrice((Long) document.get("item_price"));
            searchItem.setSellPoint((String) document.get("item_sell_point"));
            //添加到list集合中
            searchItems.add(searchItem);
        }
        //把商品信息集合添加到返回结果对象中
        searchResult.setItemList(searchItems);

        return searchResult;
    }
}
