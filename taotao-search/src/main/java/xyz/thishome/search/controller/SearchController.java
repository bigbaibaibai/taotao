package xyz.thishome.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.search.service.SearchService;

/**
 * 搜索控制器
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品信息，400搜索信息为空，500搜索异常，200正常
     *
     * @param queryString 搜索字符串
     * @param page        第几页，默认第一页
     * @param rows        一页多少行，默认60行
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public TaotaoResult search(@RequestParam(value = "q", defaultValue = "") String queryString,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "rows", defaultValue = "60") Integer rows) {
        try {
            if (StringUtils.isBlank(queryString)) {
                return TaotaoResult.build(400, "搜索信息不能为空");
            }
            //解决get请求参数乱码问题
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            return TaotaoResult.ok(searchService.search(queryString, page, rows));
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}
