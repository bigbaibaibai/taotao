package xyz.thishome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbContent;
import xyz.thishome.service.ContentService;


@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 分页获取商品列表
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/query/list")
    public EasyUIResult getContentList(@RequestParam("page") Integer page,
                                       @RequestParam("rows") Integer rows,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return contentService.getContentList(page, rows, categoryId);
    }

    /**
     * 插入商品内容信息
     *
     * @param content
     * @return
     */
    @RequestMapping("/save")
    public TaotaoResult insertContent(TbContent content) {
        return contentService.insertContent(content);
    }

}
