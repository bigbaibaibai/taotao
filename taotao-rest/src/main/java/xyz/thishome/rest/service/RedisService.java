package xyz.thishome.rest.service;

import xyz.thishome.common.pojo.TaotaoResult;

/**
 * 操作redis缓存的服务
 */
public interface RedisService {

    /**
     * 同步分类id的广告内容，修改数据库内容后，删除相关的缓存，从数据库中重新查询
     *
     * @param catId
     * @return
     */
    TaotaoResult syncContent(Long catId);

}
