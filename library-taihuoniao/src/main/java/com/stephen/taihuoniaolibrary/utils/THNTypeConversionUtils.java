package com.stephen.taihuoniaolibrary.utils;

/**
 * Created by Stephen on 2017/3/21 19:08
 * Email: 895745843@qq.com
 */

public class THNTypeConversionUtils {

    public static int StringConvertInt(String str){
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String IntConvertString(int number){
        try {
            return String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long StringConvertLong(String str){
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String LongConvertString(long number){
        try {
            return String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static double StringConvertDouble(String str){
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0D;
    }
}
