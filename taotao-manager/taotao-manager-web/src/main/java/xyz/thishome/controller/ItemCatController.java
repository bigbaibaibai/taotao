package xyz.thishome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.EasyUITreeNode;
import xyz.thishome.service.ItemCatService;

import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    public List<EasyUITreeNode> getNodes(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return itemCatService.getItemCatList(parentId);
    }


}
