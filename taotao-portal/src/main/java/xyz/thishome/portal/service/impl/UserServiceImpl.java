package xyz.thishome.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Value("${SSO_USER_GET_URL}")
    private String SSO_USER_GET_URL;

    @Value("${SSO_PAGE_LOGIN_URL}")
    private String SSO_PAGE_LOGIN_URL;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            String get = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_GET_URL + token);
            if (!StringUtils.isBlank(get)) {
                TaotaoResult result = TaotaoResult.formatToPojo(get, TbUser.class);
                if (result.getStatus() == 200) {
                    return (TbUser) result.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLoginPageUrl() {
        return SSO_BASE_URL + SSO_PAGE_LOGIN_URL;
    }
}
