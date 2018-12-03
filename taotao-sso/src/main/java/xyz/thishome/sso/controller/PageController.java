package xyz.thishome.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户跳转controller
 */
@Controller
@RequestMapping("/page")
public class PageController {

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(String redirect, Model model) {
        //回调url
        model.addAttribute("redirect", redirect);
        return "login";
    }


}
