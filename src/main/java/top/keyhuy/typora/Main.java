package top.keyhuy.typora;

import java.util.ArrayList;
import java.util.List;

/**
 * 主函数
 *
 * @author Key
 * @date 2023/03/27 16:09
 **/
public class Main {
    public static void main(String[] args) {
        if (args.length != 0) {
            List<String> urls = new ArrayList<>();
            TyporaUpload typoraUpload = new TyporaUpload();
            try {
                // 遍历文件路径
                for (String localFilePath : args) {
                    urls.add(typoraUpload.upload2QiNiuYun(localFilePath));
                }
                // 上传完成，打印
                System.out.println("Upload Success:");
                urls.forEach(System.out :: println);
            } catch (Exception e) {
                // 上传失败
                System.out.println("Upload Fail:" + e.getMessage());
            }
        } else {
            // 没有文件上传
            System.out.println("No File Upload");
        }
    }
}
