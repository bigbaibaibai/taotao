package xyz.thishome.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.thishome.common.pojo.TaotaoResult;
import xyz.thishome.common.utils.HttpClientUtil;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.pojo.TbItem;
import xyz.thishome.pojo.TbItemDesc;
import xyz.thishome.pojo.TbItemParamItem;
import xyz.thishome.portal.service.ItemService;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_ITEM_INFO_URL}")
    private String REST_ITEM_INFO;

    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;

    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public TbItem getItemById(Long itemId) {
        try {
            String itemString = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_INFO + itemId);
            if (!StringUtils.isBlank(itemString)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(itemString, TbItem.class);
                if (taotaoResult.getStatus() == 200) {
                    return (TbItem) taotaoResult.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getItemDescById(Long itemId) {
        try {
            String itemString = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
            if (!StringUtils.isBlank(itemString)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(itemString, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemDesc desc = (TbItemDesc) taotaoResult.getData();
                    return desc.getItemDesc();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getItemParamById(Long itemId) {
        try {
            String itemString = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
            if (!StringUtils.isBlank(itemString)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(itemString, TbItemParamItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemParamItem paramItem = (TbItemParamItem) taotaoResult.getData();

                    //生成html
                    // 把规格参数json数据转换成java对象
                    List<Map> jsonList = JsonUtils.jsonToList(paramItem.getParamData(), Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for (Map m1 : jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for (Map m2 : list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                            sb.append("            <td>" + m2.get("v") + "</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
