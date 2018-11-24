package xyz.thishome.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.rest.service.RedisService;

/**
 * 同步缓存处理器
 */
@RestController
@RequestMapping("/cache/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentCId}")
    public TaotaoResult syncContent(@PathVariable("contentCId") Long contentCId) {
        return redisService.syncContent(contentCId);
    }

}
