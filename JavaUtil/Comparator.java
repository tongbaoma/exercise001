package jp.microad.blade.batch.kizasi.utils;

import java.math.BigDecimal;

public class Comparator {

    /**
     *  順序付けのために 2 つの数字を比較する。
     * 
     * 
     * @param arg0 比較対象
     * @param arg1 比較対象
     * @return 
     */
    public int compare(byte[] arg0, byte[] arg1) {
        return arg0.toString().compareTo(arg1.toString());
    }

    /**
     *  順序付けのために 2 つの数字を比較する。
     *
     *
     * @param arg0 比較対象
     * @param arg1 比較対象
     * @return arg1>arg0:-1,arg1<arg0:1,arg1=arg0:0
     */
    public int compare(Object arg0, Object arg1) {
        BigDecimal b1;
        BigDecimal b2;

        try {
            b1 = new BigDecimal(arg0.toString());
        } catch (NumberFormatException e) {
            b1 = BigDecimal.ZERO;
        }

        try {
            b2 = new BigDecimal(arg1.toString());
        } catch (NumberFormatException e) {
            b2 = BigDecimal.ZERO;
        }

        return b1.compareTo(b2);
    }

    /**
     *  順序付けのために 2 つの数字を比較する。
     * 
     * 
     * @param arg0 比較対象
     * @param arg1 比較対象
     * @return arg1>arg0:-1,arg1<arg0:1,arg1=arg0:0
     */
    public int compare(String arg0, String arg1) {
        if (Integer.parseInt(arg0) < Integer.parseInt(arg1)) {
            return -1;
        } else if (Integer.parseInt(arg0) == Integer.parseInt(arg1)) {
            return 0;
        } else {
            return 1;
        }
    }
}
