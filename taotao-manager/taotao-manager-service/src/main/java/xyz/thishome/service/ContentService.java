package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbContent;

public interface ContentService {

    /**
     * 获取分页的商品内容列表信息
     *
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EasyUIResult getContentList(Integer page, Integer rows, Long categoryId);

    /**
     * 插入一条商品内容
     *
     * @param content
     * @return
     */
    TaotaoResult insertContent(TbContent content);
}
