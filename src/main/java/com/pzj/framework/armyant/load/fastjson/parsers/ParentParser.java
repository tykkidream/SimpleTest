package com.pzj.framework.armyant.load.fastjson.parsers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.LoadResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public abstract class ParentParser implements LoadParser{

    @Override
    public Object parseTheActualObject(LoadResource resource) {
        return autoParse(resource.getResource(), null);
    }

    @Override
    public <T> T parseTheActualObject(LoadResource resource, Class<T> clazz) {
        return (T)autoParse(resource.getResource(), clazz);
    }

    @Override
    public Object parseTheActualObject(LoadResource resource, String key) {
        return parseTheActualObject(resource, null, key);
    }

    @Override
    public <T> T parseTheActualObject(LoadResource resource, Class<T> clazz, String key) {
        Object resourceObject = resource.getResource();
        if (resourceObject instanceof Map){
            Map resourceMap = (Map)resourceObject;

            Object value = resourceMap.get(key);

            return (T)autoParse(value, clazz);
        }

        return null;
    }

    /**
     * 解析成实际对象
     * @param sourceValue
     * @return
     */
    public Object autoParse(Object sourceValue, Class clazz){
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

    protected Object parseTheBasicObject(Object data, Class clazz){
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
