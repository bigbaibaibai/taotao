package xyz.thishome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.EasyUITreeNode;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.service.ContentCategoryService;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    ContentCategoryService contentCategoryService;

    /**
     * 响应获取叶子节点请求
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    public List<EasyUITreeNode> getContentCategory(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategory(parentId);
    }

    /**
     * 添加一个分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    public TaotaoResult createContentCat(@RequestParam("parentId") Long parentId,
                                         @RequestParam("name") String name) {

        return contentCategoryService.createContentCat(parentId, name);
    }

    /**
     * 分类节点删除
     *
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public TaotaoResult deleteContentCat(@RequestParam("parentId") Long parentId,
                                         @RequestParam("id") Long id) {
        contentCategoryService.deleteContentCat(parentId, id);
        return TaotaoResult.ok();
    }

    /**
     * 更新节点名称
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/update")
    public TaotaoResult updateContentCat(@RequestParam("id") Long id,
                                         @RequestParam("name") String name) {
        contentCategoryService.updateContentCat(id, name);
        return TaotaoResult.ok();
    }


}
