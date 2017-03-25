package com.pzj.framework.armyant.load.fastjson.parsers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pzj.framework.armyant.load.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public abstract class ParentParser implements Parser {
    public ParentParser(){
        super();
    }

    @Override
    public Object parseTheActualObject(Object resource) {
        return autoParse(resource, null);
    }

    public static  Object parseTheActualObject(Object resource, Class clazz) {
        return autoParse(resource, clazz);
    }

    public static Object parseTheActualObject(Object resource, String key) {
        return parseTheActualObject(resource, key, null);
    }

    public static  Object parseTheActualObject(Object resource, String key, Class clazz) {
        if (resource instanceof Map){
            Map resourceMap = (Map)resource;

            Object value = resourceMap.get(key);

            return autoParse(value, clazz);
        }
        return null;
    }

    /**
     * 解析成实际对象
     * @param sourceValue
     * @return
     */
    public static Object autoParse(Object sourceValue, Class clazz){
        if (sourceValue == null){
            return null;
        }

        Object actualObject;

        if (sourceValue.getClass().equals(clazz)) {
            actualObject = sourceValue;
        } else if (sourceValue instanceof JSONObject){
            JSONObject json = (JSONObject)sourceValue;

            if (clazz != null){
                actualObject = JSON.toJavaObject(json, clazz);
            } else {
                actualObject = new HashMap<>(json);
            }

        } else if (sourceValue instanceof JSONArray) {
            List<?> json = (List<?>)sourceValue;
            List<Object> list = new ArrayList<>(json.size());

            for (Object source : json){
                source = autoParse(source, clazz);
                list.add(source);
            }

            actualObject = list;
        } else {
            actualObject = parseTheBasicObject(sourceValue, clazz);
        }

        return actualObject;
    }

    public static Object parseTheBasicObject(Object data, Class clazz){
        Object convertObject = data;

        if (clazz == Integer.class){
            convertObject = new Integer(data.toString());
        } else if (clazz == String.class){
            convertObject = data.toString();
        } else if (clazz == Long.class){
            convertObject = new Long(data.toString());
        }
        return convertObject;
    }
}
