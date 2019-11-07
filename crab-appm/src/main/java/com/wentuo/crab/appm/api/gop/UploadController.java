package com.wentuo.crab.appm.api.gop;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.modular.mini.service.upload.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 功能描述：上传相关控制器
 *
 * @author wangbencheng
 * @version 1.0
 * @className UploadController
 * @since 2019/8/20 12:49
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @RequestMapping(method = RequestMethod.POST, path = "/upload/image.do")
    @ResponseBody
    @NoPermission
    public Object uploadImage(@RequestPart("file") MultipartFile picture) {
        return uploadService.uploadImage(picture);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/upload/file.do")
    @ResponseBody
    @NoPermission
    public Object uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        return uploadService.uploadFile(multipartFile);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file/delete.do")
    @ResponseBody
    @NoPermission
    public Object deleteFile(String url) {
        return uploadService.deleteFile(url);
    }
}
