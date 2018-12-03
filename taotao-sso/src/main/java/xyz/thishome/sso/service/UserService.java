package xyz.thishome.sso.service;

import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户相关业务
 */
public interface UserService {

    /**
     * 检查用户注册时，用户名，手机号，邮箱地址，数据库中是否重复，简单验证
     *
     * @param content 检查内容
     * @param type    检查类型，1：username，2：phone，3：email
     * @return 返回200表示检查成功，data的值为false表示不可用，data值为true表示可用
     */
    TaotaoResult checkData(String content, Integer type);

    /**
     * 注册一个用户
     *
     * @param user
     * @return
     */
    TaotaoResult insertUser(TbUser user);

    /**
     * 登录用户，把token写入redis的session中，并写入cookie中，返回taotaoResult中data为token
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    TaotaoResult getLongin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过token这个id，从缓存中查询已登录用户
     *
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 安全退出
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    TaotaoResult logout(String token, HttpServletRequest request, HttpServletResponse response);
}
