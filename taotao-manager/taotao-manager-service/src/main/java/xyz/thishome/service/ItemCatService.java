package xyz.thishome.service;

import xyz.thishome.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);
}
