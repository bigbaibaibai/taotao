package xyz.thishome.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.rest.service.ItemService;

/**
 * 商品信息controller
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过商品Id获取商品基本信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    public TaotaoResult getItem(@PathVariable Long id) {
        return itemService.getItemBaseInfo(id);
    }

    /**
     * 通过商品id获取商品描述信息
     * @param id
     * @return
     */
    @RequestMapping("/desc/{id}")
    public TaotaoResult getItemDesc(@PathVariable Long id) {
        return itemService.getItemDesc(id);
    }

    /**
     * 通过商品id获取商品规格信息
     * @param id
     * @return
     */
    @RequestMapping("/param/{id}")
    public TaotaoResult getItemParam(@PathVariable Long id) {
        return itemService.getItemParam(id);
    }

}
