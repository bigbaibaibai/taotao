package xyz.thishome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.service.ItemParamService;

@RestController
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 响应规格模板分页信息获取请求
     *
     * @param page
     * @param rows
     * @return
     */
    //item/param/list?page=1&rows=30
    @RequestMapping("/list")
    public EasyUIResult getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        return itemParamService.getListItem(page, rows);
    }

    /**
     * 响应查询类别模板是否存在请求
     *
     * @return
     */
    ///item/param/query/itemcatid/{itemCatId}
    @RequestMapping("/query/itemcatid/{itemCatId}")
    public TaotaoResult getItemParam(@PathVariable Long itemCatId) {
        return itemParamService.getItemParam(itemCatId);
    }


}
