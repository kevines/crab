package com.wentuo.crab.modular.mini.service.oss.impl;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.modular.mini.service.oss.OssFileService;
import com.wentuo.crab.util.AliyunOSSClientUtil;
import com.wentuo.crab.util.BizException;
import com.wentuo.crab.util.DateUtils;
import com.wentuo.crab.util.OssUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述：阿里云Oss文件操作服务接口实现
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName OssFileServiceImpl
 * @since 2019/10/28 11:23
 */
@Service
public class OssFileServiceImpl implements OssFileService {

    @Override
    public Object uploadImage(MultipartFile picture, Integer size) {
        String fileSavePath = "";
        String filename = picture.getOriginalFilename();
        try {
            boolean imageFile = OssUtil.checkFileSize(picture.getSize(), size, "M");
            if (!imageFile) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传图片不能超过20m");
            }
            if (filename.toUpperCase().indexOf(".JPG") > 0 || filename.toUpperCase().indexOf(".PNG") > 0 || filename.toUpperCase().indexOf(".JPEG") > 0) {
                fileSavePath = OssUtil.ossUpload(picture.getInputStream());
            } else {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "图片不符合格式");
            }
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }

        return ResponseData.success(fileSavePath);
    }

    /**
     * 上传压缩图片
     * @param multipartfile
     * @param size 图片大小
     * @return
     * @throws Exception
     */
    @Override
    public Object uploadImageMini(MultipartFile multipartfile, Integer size) throws Exception {
        String folderSecond = "";
        if (multipartfile == null) {
            throw new BizException(500, "上传图片参数不合法");
        }
        if (multipartfile.getSize() > size * 1024 * 1024) {
            throw new BizException(500, "上传图片大小不能超过" + size + "M!");
        }

        //设置统一图片后缀名
        String suffixName;

        //获取图片文件名(不带扩展名的文件名)
//        String prefixName = getFileNameWithoutEx(multipartfile.getOriginalFilename());
        String date = DateUtils.format(new Date(), DateUtils.DEFAULT1);
        String dateStr = DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS1);
        String prefixName = date + "/" + UUID.randomUUID().toString() + dateStr;

        //获取图片后缀名,判断如果是png的话就不进行格式转换,因为Thumbnails存在转png->jpg图片变红bug
        String suffixNameOrigin = getExtensionName(multipartfile.getOriginalFilename());

        if ("png".equals(suffixNameOrigin)) {
            suffixName = "png";
        } else {
            suffixName = "jpg";
        }

        //图片存储文件夹
        String filePath = "src/main/resources/";

        //图片在项目中的地址(项目位置+图片名,带后缀名)
        String contextPath = filePath + prefixName + "." + suffixName;
        //存的项目的中模版图片
        File tempFile = null;
        //上传时从项目中拿到的图片
        File f = null;
        InputStream inputStream = null;
        try {
            //图片在项目中的地址(项目位置+图片名,带后缀名)
            tempFile = new File(contextPath);
            if (!tempFile.exists()) {
                //生成图片文件
                FileUtils.copyInputStreamToFile(multipartfile.getInputStream(), tempFile);
            }

            /*
             * size(width,height) 若图片横比1920小，高比1080小，不变
             * 若图片横比1920小，高比1080大，高缩小到1080，图片比例不变 若图片横比1920大，高比1080小，横缩小到1920，图片比例不变
             * 若图片横比1920大，高比1080大，图片按比例缩小，横为1920或高为1080
             * 图片格式转化为jpg,质量不变
             */
            BufferedImage image = ImageIO.read(multipartfile.getInputStream());
            if (image.getHeight() > 1080 || image.getWidth() > 1920) {
                if (!"png".equals(suffixName)) {
                    Thumbnails.of(contextPath).size(1920, 1080).outputQuality(1f).outputFormat("jpg").toFile(contextPath);
                } else {
                    Thumbnails.of(contextPath).size(1920, 1080).outputQuality(1f).toFile(contextPath);
                }
            } else {
                if (!"png".equals(suffixName)) {
                    Thumbnails.of(contextPath).outputQuality(1f).scale(1f).outputFormat("jpg").toFile(contextPath);
                } else {
                    Thumbnails.of(contextPath).outputQuality(1f).scale(1f).toFile(contextPath);
                }
            }

            //获取压缩后的图片
            f = new File(contextPath);
            inputStream = new FileInputStream(f);

            //设置三级文件夹名
            String folderThird = "/";

            //设置OSS上的二级文件目录
            String folderPath = folderSecond + folderThird;

            //设置图片存储在oss上的名字
            String fileName = prefixName + "." + suffixName;

            //上传图片到OSS,返回图书路径
            String resultUrl = AliyunOSSClientUtil.uploadImg2Oss(inputStream, folderPath, fileName);
            return resultUrl;
        } catch (Exception e) {
            throw new BizException(500, "图片上传失败");
        } finally {
            //将临时文件删除
            tempFile.delete();
            f.delete();
            inputStream.close();
        }
    }

    @Override
    public Object uploadFile(MultipartFile multipartFile, Integer size) {
        String fileSavePath = "";
        String filename = multipartFile.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf(".")+1);
        try {
            boolean file = OssUtil.checkFileSize(multipartFile.getSize(), size, "M");
            if (!file) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传文件不能超过100m");
            }
            fileSavePath = OssUtil.ossUploadFile(multipartFile.getInputStream(),prefix);
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        return ResponseData.success(fileSavePath);
    }

    @Override
    public Object deleteFile(String url) {
        String result = OssUtil.deleteFile(url);
        if (result.equals("success")) {
            return ResponseData.success(result);
        } else {
            return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "删除失败");
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param filename 文件
     * @return
     */
    private static String getFileNameWithoutEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
}
