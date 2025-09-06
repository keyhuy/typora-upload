package top.keyhuy.typora;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 上传文件
 *
 * @author Key
 * @date 2023/03/27 15:32
 **/
public class TyporaUpload {

    /**
     * 上传到七牛云
     */
    public String upload2QiNiuYun(String localFilePath) throws Exception {

        // 获取七牛云域名
        String endPoint = "https://qiniuyun.keyhuy.top";
        // 桶名
        String bucketName = "key-cdn";
        // 水印代码
        String watermark = "imageMogr2/blur/1x0/quality/75|watermark/2/text/QOi8nemVv-WFreWKoDE=/font/5b6u6L2v6ZuF6buR/fontsize/300/fill/I0FDQTlBOQ==/dissolve/51/gravity/SouthEast/dx/10/dy/5";

        // 密钥文件位置
        InputStream resource = this.getClass().getResourceAsStream("/cert/qiniuyun-oss.key");
        if (null == resource) {
            /* add by dev at 20250906 beg */
            System.err.println("上传失败");
            /* add by dev at 20250906 end */
            throw new RuntimeException("上传失败");
        }
        // 读取密钥
        List<String> keys = new ArrayList<>(2);
        Scanner sc = new Scanner(resource);
        while (sc.hasNextLine()) {
            keys.add(sc.nextLine());
        }

        // 密钥设置
        Auth auth = Auth.create(keys.get(0), keys.get(1));
        // 上传管理对象，华南用的是zone2
        UploadManager uploadManager =
                new UploadManager(new Configuration(Zone.zone2()));

        // 获取原始文件名
        String originalName = FileUtils.getOriginalNameByPath(localFilePath);
        if (null == originalName) {
            throw new RuntimeException("文件路径不合法");
        }

        // 生成最终的文件名
        String filename = FileUtils.createFilename(
                "images/blog-imgs/",
                originalName);

        if (null == filename) {
            // 文件格式不合法
            /* add by hot-fix at 20250906 beg */
            System.err.println("文件格式不合法");
            /* add by hot-fix at 20250906 end */
            throw new RuntimeException("文件格式不合法");
        }

        // 调用put方法上传，简单上传，直接根据文件路径上传
        Response resp = uploadManager.put(localFilePath, filename,
                auth.uploadToken(bucketName));

        if (resp.isOK() && resp.isJson()) {
            // 上传成功，返回文件路径+水印
            return endPoint + "/" + filename + "?" + watermark;
        }

        // 上传失败或格式错误
        return null;
    }

    /*上传到其他oss*/
}
