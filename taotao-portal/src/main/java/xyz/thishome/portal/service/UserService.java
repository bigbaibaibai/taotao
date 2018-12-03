package xyz.thishome.portal.service;

import xyz.thishome.pojo.TbUser;

/**
 * 用户相关service
 */
public interface UserService {

    /**
     * 通过token从redis的session中查询的登录用户
     *
     * @param token
     * @return
     */
    TbUser getUserByToken(String token);

    /**
     * 获取登录
     * @return
     */
    String getLoginPageUrl();

}
