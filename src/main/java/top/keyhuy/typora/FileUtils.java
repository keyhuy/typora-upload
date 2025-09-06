package top.keyhuy.typora;

import java.util.UUID;

/**
 * 操作文件工具类
 *
 * @author Key
 * @date 2022/04/02/18:31
 **/
public class FileUtils {

    private FileUtils() {}

    /**
     * 文件允许的后缀扩展名
     */
    /* modify at 20250906 beg */
    //public static String[] FILE_SUFFIX =
    //        new String[] { "png", "bmp", "jpg", "jpeg", "pdf", "zip", "doc"};
    // 去掉nmp文件格式
    public static String[] FILE_SUFFIX =
            new String[] { "png", "jpg", "jpeg", "pdf", "zip", "doc"};
    /* modify at 20250906 end */

    /**
     * 根据原始文件名生成新的不重复的文件名
     * @param namePre 文件名前缀
     * @param originalName 原文件名
     * @return 返回不重复的文件名
     */
    public static String createFilename(String namePre, String originalName) {

        // 判断原始文件名是否合法
        if (! isLegal(originalName)) {
            // 不合法，返回null
            return null;
        }

        // 将文件名和后缀拆出来
        String[] preAndSuffix = originalName.split("\\.");

        // 使用UUID生成一个不重复的字符串，插到原名字后面，保证文件不重名
        return namePre + preAndSuffix[0] +
                UUID.randomUUID().toString().replace("-", "") +
                "." + preAndSuffix[1];
    }

    /**
     * 重载方法，不需要前缀
     * @param originalName 文件原始名
     * @return 返回生成的文件名
     */
    public static String createFilename(String originalName) {
        return createFilename("", originalName);
    }

    /**
     * 判断文件名是否合法
     */
    public static boolean isLegal(String filename) {

        if (null == filename || "".equals(filename)) {
            return false;
        }

        // 获取文件后缀名（.png、.jpg等）和除后缀名后的文件名
        String suf = filename.substring(filename.lastIndexOf(".") + 1);
        String pre = filename.substring(0, filename.lastIndexOf("."));

        // 判断前后缀名是否合法
        if (suf.length() == 0 || pre.length() == 0) {
            return false;
        }
        // 判断后缀是否在允许的文件后缀范围内
        for (String suffix : FILE_SUFFIX) {
            if (suf.equals(suffix) || suf.equals(suffix.toUpperCase())) {
                // 有一个匹配即可
                return true;
            }
        }

        return false;
    }

    /**
     * 从文件路径获取完整文件名
     */
    public static String getOriginalNameByPath(String localFilePath) {
        if (localFilePath.contains("/")) {
            return localFilePath.substring(localFilePath.lastIndexOf("/") + 1);
        } else if (localFilePath.contains("\\")) {
            return localFilePath.substring(localFilePath.lastIndexOf("\\") + 1);
        } else {
            return null;
        }
    }
}
