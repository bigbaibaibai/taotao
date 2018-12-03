package xyz.thishome.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.portal.service.ItemService;


/**
 * 商品信息展示controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 获取商品基础信息
     *
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping("/item/{itemId}")
    public String getItemInfo(@PathVariable("itemId") Long itemId, Model model) {
        TbItem item = itemService.getItemById(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping(value = "/item/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId) {
        return itemService.getItemDescById(itemId);
    }

    @RequestMapping(value = "/item/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId) {
        return itemService.getItemParamById(itemId);
    }

}
