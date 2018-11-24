package xyz.thishome.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.thishome.portal.service.ContentService;

@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String toIndexPage(Model model) {
        String json = contentService.getContentList();
        model.addAttribute("ad1", json);
        return "index";
    }

}
