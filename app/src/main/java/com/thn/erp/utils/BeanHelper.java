package com.thn.erp.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stephen on 2018/4/10 15:57
 * Email: 895745843@qq.com
 */

public class BeanHelper {
    public static HashMap<String, Object> objToHash(Object obj){
        HashMap<String, Object> hashMap = new HashMap<>();
        Class clazz = obj.getClass();
        List<Class> clazzs = new ArrayList<>();
        do {
            clazzs.add(clazz);
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));
        for (Class iClazz : clazzs) {
            Field[] fields = iClazz.getDeclaredFields();
            for (Field field : fields) {
                Object objVal = null;
                field.setAccessible(true);
                try {
                    objVal = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                hashMap.put(field.getName(), objVal);
            }
        }
        LogUtil.e("---------- hashMap : " + hashMap.toString());
        return hashMap;
    }
}
