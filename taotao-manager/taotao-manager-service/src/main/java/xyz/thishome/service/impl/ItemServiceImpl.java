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
import xyz.thishome.mapper.TbItemParamItemMapper;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.pojo.TbItemDesc;
import xyz.thishome.pojo.TbItemParamItem;
import xyz.thishome.service.ItemService;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

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
     * 分页获取商品列表
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
     * @param item       表单中商品信息
     * @param itemParams 商品规格说明JSON
     * @param decs       商品描述
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item, String decs, String itemParams) {
        //补全id
        long id = IDUtils.genItemId();
        item.setId(id);
        //补全状态码，1正常
        item.setStatus((byte) 1);
        //补全更新时间
        item.setUpdated(new Date());
        //补全上架时间
        item.setCreated(new Date());
        //添加商品到数据库中
        itemMapper.insertSelective(item);
        //添加商品规格说明到数据库中
        if (insertItemParams(id, itemParams).getStatus() != 200) {
            throw new RuntimeException("添加商品规格参数异常！");
        }
        //添加商品描述到数据库中
        if (createDesc(id, decs).getStatus() != 200) {
            throw new RuntimeException("添加商品描述异常！");
        }
        return TaotaoResult.ok();
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

    /**
     * 添加商品规格说明
     *
     * @param itemParams 商品参数规格JSON
     * @param id         商品id
     */
    private TaotaoResult insertItemParams(Long id, String itemParams) {
        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setItemId(id);
        paramItem.setParamData(itemParams);
        paramItem.setCreated(new Date());
        paramItem.setUpdated(new Date());
        itemParamItemMapper.insert(paramItem);
        return TaotaoResult.ok();
    }
}
