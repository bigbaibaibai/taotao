package xyz.thishome.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.EasyUIResult;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.mapper.TbItemParamMapper;
import xyz.thishome.pojo.TbItemParam;
import xyz.thishome.pojo.TbItemParamExample;
import xyz.thishome.service.ItemParamService;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;


    /**
     * 获取分页模板信息信息
     *
     * @param page 当前页码
     * @param rows 每页多少行
     * @return
     */
    @Override
    public EasyUIResult getListItem(Integer page, Integer rows) {
        //启用分页插件分页
        PageHelper.startPage(page, rows);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(null);
        PageInfo<TbItemParam> itemParamPageInfo = new PageInfo<>(itemParams);
        return new EasyUIResult(itemParamPageInfo.getTotal(), itemParamPageInfo.getList());
    }

    /**
     * 通过id获取模板信息，封装到TaotaoResult中
     *
     * @param itemCatId
     * @return
     */
    @Override
    public TaotaoResult getItemParam(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if (itemParams != null && itemParams.size() > 0) {
            return TaotaoResult.ok(itemParams.get(0));
        }
        return TaotaoResult.ok();
    }

    /**
     * 保存一个模板到数据库中，返回TaotaoResult,status为200表示成功
     *
     * @return
     */
    @Override
    public TaotaoResult saveItemParam(TbItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insertSelective(itemParam);
        return TaotaoResult.ok();
    }
}
