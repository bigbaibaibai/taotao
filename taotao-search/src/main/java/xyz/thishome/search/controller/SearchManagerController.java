package xyz.thishome.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @RequestMapping("/importall")
    public TaotaoResult importAll() {
        return searchItemService.importAllItems();
    }

}
