package xyz.thishome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String toPage(@PathVariable String page) {
        return page;
    }

}
