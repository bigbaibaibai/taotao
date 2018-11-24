package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUITreeNode;
import xyz.thishome.common.pojo.TaotaoResult;

import java.util.List;

/**
 * 商品内容service
 */
public interface ContentCategoryService {

    /**
     * 获取id下的所有分类子节点
     *
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCategory(Long parentId);

    /**
     * 添加一个新的分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult createContentCat(Long parentId, String name);

    /**
     * 删除分类节点
     *
     * @param parentId
     * @param id
     */
    void deleteContentCat(Long parentId, Long id);

    /**
     * 更新指定id分类节点的name
     * @param id
     * @param name
     */
    void updateContentCat(Long id, String name);
}
