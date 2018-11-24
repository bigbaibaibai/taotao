package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbItemParam;

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

    /**
     * 保存一个模板到数据库中，返回TaotaoResult,status为200表示成功
     *
     * @param itemParam 参数规格模板
     * @return
     */
    TaotaoResult saveItemParam(TbItemParam itemParam);
}
