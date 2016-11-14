package jp.microad.blade.batch.kizasi.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

/**
 * 共通用メソッド
 */
public class ByteUtil {

    /**
     * trimByByte
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static String trimByByte(String str, int maxLength) {
        return trimByByte(str, maxLength, StandardCharsets.UTF_8);
    }

    /**
     * trimByByte
     *
     * @param str
     * @param maxLength
     * @param charset
     * @return
     */
    public static String trimByByte(String str, int maxLength, Charset charset) {
        if (StringUtils.isEmpty(str) || str.getBytes().length <= maxLength) {
            return str;
        }

        String maxStr = new String(str.getBytes(), 0, maxLength, charset);
        if (maxStr.getBytes().length == maxLength) {
            return maxStr;
        }
        return maxStr.substring(0, maxStr.length() - 1);
    }

}
