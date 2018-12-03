package xyz.thishome.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.CookieUtils;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.mapper.TbUserMapper;
import xyz.thishome.pojo.TbUser;
import xyz.thishome.pojo.TbUserExample;
import xyz.thishome.sso.dao.JedisClient;
import xyz.thishome.sso.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_KEY_USER_SESSION}")
    private String REDIS_KEY_USER_SESSION;

    @Value("${REDIS_EXPIRE_SSO}")
    private Integer REDIS_EXPIRE_SSO;


    @Override
    public TaotaoResult checkData(String content, Integer type) {
        //简单验证类型
        if (type != 1 && type != 2 && type != 3) {
            return TaotaoResult.build(400, "检查类型有误");
        }

        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(content);
        } else {
            criteria.andEmailEqualTo(content);
        }
        List<TbUser> users = userMapper.selectByExample(userExample);

        if (users == null || users.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult insertUser(TbUser user) {
        //补全信息
        user.setUpdated(new Date());
        user.setCreated(new Date());
        //对密码进行md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insertSelective(user);
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult getLongin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //转化为md5比较
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<TbUser> users = userMapper.selectByExample(example);

        if (users == null || users.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码输入错误");
        }
        TbUser user = users.get(0);
        //清空密码
        user.setPassword(null);
        String token = UUID.randomUUID().toString();
        //把用户信息存redis
        jedisClient.set(REDIS_KEY_USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_KEY_USER_SESSION + ":" + token, REDIS_EXPIRE_SSO);
        //把token写入cookie
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //从redis中获取user
        String userString = jedisClient.get(REDIS_KEY_USER_SESSION + ":" + token);
        //如果查到
        if (!StringUtils.isBlank(userString)) {
            //重新设置过期时间
            jedisClient.expire(REDIS_KEY_USER_SESSION + ":" + token, REDIS_EXPIRE_SSO);
            TbUser user = JsonUtils.jsonToPojo(userString, TbUser.class);
            return TaotaoResult.ok(user);
        }
        return TaotaoResult.build(400, "用户未登录或用户登录时间已过期");
    }

    @Override
    public TaotaoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");
        //如果session存在于redis中，则删除该session，否则未登录
        if (1 == jedisClient.del(REDIS_KEY_USER_SESSION + ":" + token)) {
            return TaotaoResult.ok("");
        }
        return TaotaoResult.build(400, "未登录");
    }

}
