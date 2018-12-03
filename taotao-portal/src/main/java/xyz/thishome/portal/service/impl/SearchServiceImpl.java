package xyz.thishome.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.SearchResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.portal.service.SearchService;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Value("${SEARCH_QUERY_URL}")
    private String SEARCH_QUERY_URL;

    @Override
    public SearchResult getList(String queryString, Integer page) {
        //封装请求参数
        Map<String, String> map = new HashMap();
        map.put("page", page + "");
        map.put("q", queryString);
        try {
            //发送请求
            String get = HttpClientUtil.doGet(SEARCH_BASE_URL + SEARCH_QUERY_URL, map);
            //处理请求返回json
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(get, SearchResult.class);
            if (taotaoResult.getStatus() == 200) {
                SearchResult data = (SearchResult) taotaoResult.getData();
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
