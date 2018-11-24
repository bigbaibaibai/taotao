package xyz.thishome.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.rest.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id获取内容列表
     *
     * @param contentCategoryId
     * @return
     */
    @RequestMapping("/list/{contentCategoryId}")
    public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            return TaotaoResult.ok(contentService.getContentByCatId(contentCategoryId));
        } catch (Exception e) {
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}
