package com.wentuo.crab.appm.api.gop;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.service.oss.OssFileService;
import com.wentuo.crab.modular.mini.service.oss.impl.OssFileServiceImpl;
import com.wentuo.crab.modular.mini.service.upload.UploadService;
import com.wentuo.crab.util.BizException;
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

    @Resource
    private OssFileService ossFileService;

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

    @RequestMapping(path = "/upload/mini/image.do", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPicture(MultipartFile file) {
        try {
             Object pictureUrl = ossFileService.uploadImageMini(file,  20);
            return WTResponse.success("上传图片成功", pictureUrl);
        } catch (Exception e) {
            return WTResponse.error("上传图片失败");
        }
    }
}
