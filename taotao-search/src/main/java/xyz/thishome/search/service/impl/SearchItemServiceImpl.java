package xyz.thishome.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.SearchItem;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.ExceptionUtil;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.search.dao.SearchItemDao;
import xyz.thishome.search.mapper.SearchItemMapper;
import xyz.thishome.search.service.SearchItemService;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SearchItemDao searchItemDao;

    @Override
    public TaotaoResult importAllItems() {
        try {
            //查询所有搜索需要的商品信息
            List<SearchItem> listSearchItem = searchItemMapper.getListSearchItem();
            searchItemDao.importListItems(listSearchItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult importItem(SearchItem searchItem) {
        try {
            searchItemDao.importItem(searchItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delItemList(String idsJson) {
        try {
            List<String> ids = JsonUtils.jsonToList(idsJson, String.class);
            searchItemDao.delItemList(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delItem(String id) {
        try {
            searchItemDao.delItem(id);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }


}
