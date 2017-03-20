package com.pzj.framework.armyant.load.fastjson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONReader;

/**
 * Created by wuliqing on 2016-7-28.
 */
public class LoadJsonFileUtil {

    private static JSONReader getJsonReader(String path) {
        InputStream inputStream = LoadJsonFileUtil.class.getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new JSONReader(inputStreamReader);
    }

    public static <T> T readOneFromClasspath(String path, Class<T> clazz){
        try {
            JSONReader reader = getJsonReader(path);
            T obj = reader.readObject(clazz);
            reader.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object readOneFromClasspath(String path){
        try {
            JSONReader reader = getJsonReader(path);
            Object obj = reader.readObject();
            reader.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> readListFromClasspath(String path, Class<T> clazz){
        try {
            JSONReader reader = getJsonReader(path);
            List<T> result = new ArrayList<>();
            reader.startArray();
            while (reader.hasNext()){
                T obj = reader.readObject(clazz);
                result.add(obj);
            }
            reader.endArray();
            reader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> Set<T> readSetFromClasspath(String path, Class<T> clazz){
        try {
            JSONReader reader = getJsonReader(path);
            Set<T> result = new HashSet<>();
            reader.startArray();
            while (reader.hasNext()){
                T obj = reader.readObject(clazz);
                result.add(obj);
            }
            reader.endArray();
            reader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
