package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    /**
     * 获取商品分类列表，封装成异步tree的pojo
     *
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
