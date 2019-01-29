package org.succlz123.commoncode.bt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by succlz123 on 2017/3/25.
 */

public class Sha1Hash {

    /**
     * 返回一个20byte的sha1字符串
     */
    public static String toHex(byte[] node) {
        try {
            MessageDigest d = MessageDigest.getInstance("SHA1");  //指定算法 sha1摘要
            d.update(node);
            return to20Byte(d.digest()); //摘要的结果是 20byte 转16进制 得到字符串
        } catch (NoSuchAlgorithmException ignored) {
        }
        return "";
    }

    /**
     * byte数组转16进制
     */
    private static String to20Byte(byte[] b) {
        String tmp = "";
        String des = "";
        for (byte aB : b) {
            tmp = Integer.toHexString(aB & 0xFF);
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des.toUpperCase();
    }
}
