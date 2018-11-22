package xyz.thishome.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.IDUtils;
import xyz.thishome.mapper.TbItemDescMapper;
import xyz.thishome.mapper.TbItemMapper;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.pojo.TbItemDesc;
import xyz.thishome.service.ItemService;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    /**
     * 根据id获取商品
     *
     * @param id
     * @return
     */
    @Override
    public TbItem getItemById(Long id) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(id);
        return tbItem;
    }

    /**
     * 获取所有商品
     *
     * @return
     */
    @Override
    public EasyUIResult getListItem(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> tbItems = itemMapper.selectByExample(null);
        PageInfo pageInfo = new PageInfo(tbItems);
        EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
        return easyUIResult;
    }

    /**
     * 添加商品
     *
     * @param item
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item, String decs) {
        //补全id
        long id = IDUtils.genItemId();
        item.setId(id);
        //补全状态码，1正常
        item.setStatus((byte) 1);
        //补全更新时间
        item.setUpdated(new Date());
        //补全上架时间
        item.setCreated(new Date());
        //添加到数据库中
        itemMapper.insertSelective(item);
        return createDesc(id, decs);
    }

    /**
     * 添加商品描述
     *
     * @param id
     * @param desc
     * @return
     */
    private TaotaoResult createDesc(Long id, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(id);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insertSelective(itemDesc);
        return TaotaoResult.ok();
    }
}
