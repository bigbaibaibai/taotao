package xyz.thishome.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.sso.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过传入内容和类型，简单验证，支持jsonp
     *
     * @param content
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{content}/{type}")
    public Object checkData(@PathVariable("content") String content, @PathVariable("type") Integer type, String callback) {
        TaotaoResult result = null;
        try {
            result = userService.checkData(content, type);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return returnResult(result, callback);
    }

    /**
     * 添加一个用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public TaotaoResult registerUser(TbUser user) {
        try {
            return userService.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 登录用户
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TaotaoResult loginUser(@RequestParam("username") String username, @RequestParam("password") String password,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {

            return userService.getLongin(username, password, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 通过token获取user，支持jsonp请求
     *
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/token/{token}")
    public Object getUser(@PathVariable String token, String callback) {
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return returnResult(result, callback);
    }

    /**
     * 安全退出
     *
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/logout/{token}")
    public Object logout(@PathVariable String token, String callback,
                         HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = null;
        try {
            result = userService.logout(token, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return returnResult(result, callback);
    }


    /**
     * 支持jsonp的返回，如果callback不为空，则为jsonp调用，否则为普通调用
     *
     * @param result   taotaoResult对象
     * @param callback jsonp回调方法名
     * @return
     */
    private Object returnResult(TaotaoResult result, String callback) {
        //如果callback为空证明不是jsonp请求
        if (StringUtils.isBlank(callback)) {
            return result;
        }
        MappingJacksonValue value = new MappingJacksonValue(result);
        value.setJsonpFunction(callback);
        return value;
    }

}
