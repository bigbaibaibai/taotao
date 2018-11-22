package xyz.thishome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.thishome.common.utils.JsonUtils;
import xyz.thishome.service.PictureService;

import java.util.Map;

/**
 * 上传文件控制器
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    public String uploadPicture(@RequestParam("uploadFile") MultipartFile multipartFile) {
        Map<String, Object> picture = pictureService.uploadPicture(multipartFile);
        return JsonUtils.objectToJson(picture);
    }

}
