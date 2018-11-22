package xyz.thishome.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.thishome.common.utils.FtpUtil;
import xyz.thishome.common.utils.IDUtils;
import xyz.thishome.service.PictureService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_ADDRESS}")
    private String ftp_address;

    @Value("${FTP_PORT}")
    private Integer ftp_port;

    @Value("${FTP_BASE_PATH}")
    private String ftp_base_path;

    @Value("${FTP_USERNAME}")
    private String ftp_username;

    @Value("${FTP_PASSWORD}")
    private String ftp_password;

    @Value("${IMAGE_BASE_URL}")
    private String ImageBaseUrl;

    /**
     * 文件上传
     * 返回json格式
     * 成功： error:0    url:
     * 失败:  error:1    message:
     *
     * @param uploadFile
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, Object> uploadPicture(MultipartFile uploadFile) {
        Map map = new HashMap();
        try {
            String oldName = uploadFile.getOriginalFilename();
            //使用随机id作为文件名
            String newName = IDUtils.genImageName();
            //加上原文件的拓展名
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            //上传图片
            String filtPath = new DateTime().toString("/yyyy/MM/dd");
            boolean b = FtpUtil.uploadFile(ftp_address, ftp_port, ftp_username,
                    ftp_password, ftp_base_path, filtPath, newName,
                    uploadFile.getInputStream());
            //String host, int port, String username, String password, String basePath,
            //			String filePath, String filename, InputStream input

            if (b) {
                String url = ImageBaseUrl + filtPath + "/" + newName;
                map.put("url", url);
                map.put("error", 0);
                return map;
            }
            map.put("error", "1");
            map.put("message", "文件上传失败");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "1");
            map.put("message", "文件上传异常");
            return map;
        }
    }
}
