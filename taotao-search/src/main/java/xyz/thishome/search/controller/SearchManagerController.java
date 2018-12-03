package xyz.thishome.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.SearchItem;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.search.service.SearchItemService;

/**
 * 索引库维护
 */
@RestController
@RequestMapping("/manager")
public class SearchManagerController {

    @Autowired
    SearchItemService searchItemService;

    /**
     * 导入所有索引到solr服务器中
     *
     * @return
     */
    @RequestMapping("/import/all")
    public TaotaoResult importAll() {
        return searchItemService.importAllItems();
    }

    /**
     * 导入新的索引
     *
     * @param searchItem
     * @return
     */
    @RequestMapping(value = "/import/item", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8"})
    public TaotaoResult importItem(SearchItem searchItem) {
        return searchItemService.importItem(searchItem);
    }

    /**
     * 批量删除索引
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del/list")
    public TaotaoResult delItemList(@RequestParam("ids") String ids) {
        return searchItemService.delItemList(ids);
    }

    /**
     * 删除单个索引
     *
     * @param id
     * @return
     */
    @RequestMapping("/del/{id}")
    public TaotaoResult delItem(String id) {
        return searchItemService.delItem(id);
    }
}
