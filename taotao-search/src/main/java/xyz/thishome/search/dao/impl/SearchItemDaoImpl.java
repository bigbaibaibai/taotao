package xyz.thishome.search.dao.impl;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.thishome.common.pojo.SearchItem;
import xyz.thishome.search.dao.SearchItemDao;

import java.io.IOException;
import java.util.List;

@Repository
public class SearchItemDaoImpl implements SearchItemDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public void importListItems(List<SearchItem> listSearchItem) throws Exception {
        for (SearchItem searchItem : listSearchItem) {
            //封装成SolrInputDocument对象
            SolrInputDocument document = searchItemToSolrInputDocument(searchItem);
            //添加到服务器中
            solrServer.add(document);
        }
        solrServer.commit();
    }

    @Override
    public void importItem(SearchItem searchItem) throws Exception {
        SolrInputDocument document = searchItemToSolrInputDocument(searchItem);
        solrServer.add(document);
        solrServer.commit();
    }

    @Override
    public void delItemList(List<String> ids) throws Exception {
        solrServer.deleteById(ids);
        solrServer.commit();
    }

    @Override
    public void delItem(String id) throws Exception {
        solrServer.deleteById(id);
        solrServer.commit();
    }

    /**
     * 把searchItem对象封装成solr需要的SolrInputDocument对象
     *
     * @param searchItem
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    private SolrInputDocument searchItemToSolrInputDocument(SearchItem searchItem) throws IOException, SolrServerException {
        //创建solr需要的document节点，根据数据库中信息封装
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", searchItem.getId());
        document.setField("item_title", searchItem.getTitle());
        document.setField("item_sell_point", searchItem.getSellPoint());
        document.setField("item_price", searchItem.getPrice());
        document.setField("item_image", searchItem.getImage());
        document.setField("item_category_name", searchItem.getCategoryName());
        document.setField("item_desc", searchItem.getItemDesc());
        return document;
    }

}
