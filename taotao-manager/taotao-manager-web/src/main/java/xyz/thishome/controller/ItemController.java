package xyz.thishome.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.service.ItemService;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品控制器
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过id获取商品
     */
    @RequestMapping("/item/{id}")
    public Map<String, Object> getItemById(@PathVariable("id") long id) {
        TbItem itemById = itemService.getItemById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("item", itemById);
        return map;
    }


    /**
     * 获商品列表，分页后商品信息
     */
    @RequestMapping("/item/list")
    public EasyUIResult getList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "rows", defaultValue = "30") int rows) {

        return itemService.getListItem(page, rows);
    }

    /**
     * 添加商品
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    public TaotaoResult itemSave(TbItem item, @RequestParam("desc") String desc) {
        return itemService.createItem(item, desc);
    }


}
