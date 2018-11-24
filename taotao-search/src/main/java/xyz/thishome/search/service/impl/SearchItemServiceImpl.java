package xyz.thishome.search.service.impl;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.search.mapper.SearchItemMapper;
import xyz.thishome.search.pojo.SearchItem;
import xyz.thishome.search.service.SearchItemService;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllItems() {
        try {
            //查询所有搜索需要的商品信息
            List<SearchItem> listSearchItem = searchItemMapper.getListSearchItem();

            for (SearchItem searchItem : listSearchItem) {
                //创建solr需要的document节点，根据数据库中信息封装
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", searchItem.getId());
                document.setField("item_title", searchItem.getTitle());
                document.setField("item_sell_point", searchItem.getSellPoint());
                document.setField("item_price", searchItem.getPrice());
                document.setField("item_image", searchItem.getImage());
                document.setField("item_category_name", searchItem.getCategoryName());
                document.setField("item_desc", searchItem.getItemDesc());
                //添加节点到solr服务器中
                solrServer.add(document);
            }
            //提交操作
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
