package xyz.thishome.portal.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.thishome.common.utils.CookieUtils;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.portal.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    //在hadler方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //从cookie获取token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //使用token从redis获取用户对象
        TbUser user = userService.getUserByToken(token);
        if (user == null) {
            //如果用户不存在，跳转页面
            response.sendRedirect(userService.getLoginPageUrl() + "?redirect="
                    + request.getRequestURL());
            return false;
        }
        request.setAttribute("user", user);
        return true;
    }

    //执行handle方法之后，modelAndView之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    //请求结束之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
