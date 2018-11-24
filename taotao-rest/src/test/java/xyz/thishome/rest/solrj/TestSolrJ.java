package xyz.thishome.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {

    /**
     * 测试添加solr加点
     *
     * @throws Exception
     */
    @Test
    public void testAddDocument() throws Exception {
        //创建sql服务，HttpSolrServer表示单机版，CloudSolrServer表示集群版
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.131:8080/solr");
        //创建一个solr节点
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "1001");
        document.addField("item_title", "测试标题");
        document.addField("item_price", 1000);
        //把节点添加到服务中
        solrServer.add(document);

        //提交服务，必须提交服务，否则无效
        solrServer.commit();
    }

    /**
     * 测试删除solr节点
     *
     * @throws Exception
     */
    @Test
    public void testDelDocument() throws Exception {
        //创建sql服务，HttpSolrServer表示单机版，CloudSolrServer表示集群版
        SolrServer solrServer = new HttpSolrServer("http://192.168.153.131:8080/solr");
        //删除一个solr节点
        solrServer.deleteById("1001");

        //提交服务，必须提交服务，否则无效
        solrServer.commit();
    }

}
