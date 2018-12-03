package xyz.thishome.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.portal.service.ContentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String restBaseUrl;

    @Value("${REST_INDEX_AD_URL}")
    private String restIndexAdUrl;

    /**
     * 通过httpClient获取内容列表
     *
     * @return
     */
    @Override
    public String getContentList() {
        try {
            //发出http请求，接受反馈数据，json字符串
            String get = HttpClientUtil.doGet(restBaseUrl + restIndexAdUrl);
            //处理json字符串
            //把json字符串转化为一个taotaoResult对象，date为一个list集合，集合中元素为TbContent对象
            TaotaoResult result = TaotaoResult.formatToList(get, TbContent.class);
            //获取TbContent集合
            List<TbContent> contents = (List<TbContent>) result.getData();

            //组建返回结果的json
            List<Map> list = new ArrayList<>();
            for (TbContent content : contents) {
                Map map = new HashMap();
                map.put("src", content.getPic());
                map.put("height", 240);
                map.put("width", 670);
                map.put("alt", content.getSubTitle());
                map.put("href", content.getUrl());

                map.put("srcB", content.getPic2());
                map.put("heightB", 240);
                map.put("widthB", 550);

                list.add(map);
            }
            String json = JsonUtils.objectToJson(list);
            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
