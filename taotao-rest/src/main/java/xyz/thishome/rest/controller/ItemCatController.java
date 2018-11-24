package xyz.thishome.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.rest.pojo.CatResult;
import xyz.thishome.rest.service.ItemCatService;

@RestController
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;


    /**
     * 返回商品类别信息服务
     *
     * @param callback
     * @return
     */
//    @RequestMapping(value = "/itemcat/all", produces = {MediaType.APPLICATION_JSON_VALUE
//            + ";charset=utf-8"})
//    public String getItemCatAll(@RequestParam("callback") String callback) {
//        CatResult catList = itemCatService.getItemCatList();
//        String json = JsonUtils.objectToJson(catList);
//        //拼串，js方法(json串); jsonp直接调用
//        json = callback + "(" + json + ")" + ";";
//        return json;
//    }
    @RequestMapping(value = "/itemcat/all", produces = {MediaType.APPLICATION_JSON_VALUE
            + ";charset=utf-8"})
    public Object getItemCatAll(@RequestParam("callback") String callback) {
        //获取需要返回的数据
        CatResult catList = itemCatService.getItemCatList();
        //创建一个MappingJacksonValue对象，传入需要返回的数据
        MappingJacksonValue jacksonValue = new MappingJacksonValue(catList);
        //设置jsonp调用的方法名
        jacksonValue.setJsonpFunction(callback);
        //返回这个MappingJacksonValue对象
        return jacksonValue;
    }


}
