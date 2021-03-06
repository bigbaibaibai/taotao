package xyz.thishome.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.thishome.common.pojo.SearchResult;
import xyz.thishome.portal.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "q") String queryString,
                         Model model) {
        if (!StringUtils.isBlank(queryString)) {
            try {
                queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SearchResult searchResult = searchService.getList(queryString, page);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);
        model.addAttribute("query", queryString);

        return "search";
    }

}
