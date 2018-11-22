package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;

public interface ItemParamService {

    /**
     * 获取分页规格参数模板信息
     *
     * @param page 当前页码
     * @param rows 每页多少行
     * @return
     */
    EasyUIResult getListItem(Integer page, Integer rows);

    /**
     * 通过id获取模板信息，封装到TaotaoResult中
     *
     * @param itemCatId
     * @return
     */
    TaotaoResult getItemParam(Long itemCatId);
}
