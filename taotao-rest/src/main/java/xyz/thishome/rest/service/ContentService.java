package xyz.thishome.rest.service;

import xyz.thishome.pojo.TbContent;

import java.util.List;

public interface ContentService {

    /**
     * 通过内容分类id获取内容列表
     *
     * @param CatId
     * @return
     */
    List<TbContent> getContentByCatId(Long CatId);
}
