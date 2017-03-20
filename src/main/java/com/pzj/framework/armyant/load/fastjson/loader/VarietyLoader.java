package com.pzj.framework.armyant.load.fastjson.loader;

import com.alibaba.fastjson.JSONObject;
import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;
import com.pzj.framework.armyant.load.fastjson.parsers.VarietyParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public class VarietyLoader extends ParentLoader<VarietyParser> {

    private Map<String, Object> targetData;

    public Map<String, Object> get(){
        return targetData;
    }

    public VarietyLoader(VarietyParser parser) {
        super(parser);
    }

    @Override
    public void parser(FastjsonResource resource) {
        this.resource = resource;
        this.targetData = doParse(resource);
    }

    private Map<String, Object> doParse(FastjsonResource resource){
        Object resourceObject = resource.getResource();

        if (resourceObject instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)resourceObject;
            return doParseJSONObject(jsonObject);
        }
        return null;
    }

    private Map<String, Object> doParseJSONObject(JSONObject jsonObject){
        Object resourceObject = resource.getResource();

        if (resourceObject instanceof JSONObject){
            JSONObject json = (JSONObject)resourceObject;

            Map<String, Object> result = new HashMap<>();

            Iterator<Map.Entry<String, Object>> iterator = json.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();

                Object schemaObject = getParser().registerParser(key);
                if (schemaObject == null){
                    continue;
                }

                Class schemaClass = null;
                if (schemaObject instanceof String){

                } else if (schemaObject instanceof Class){
                    schemaClass = (Class)schemaObject;
                } else {

                }

                Object sourceObject = next.getValue();

                Object targetObject = getParser().autoParse(sourceObject, schemaClass);

                result.put(key, targetObject);
            }

            return result;
        }
        return null;
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return parser.parseTheActualObject(resource, clazz);
    }


    @Override
    public Object get(String key) {
        return targetData.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object targetValue = targetData.get(key);

        if (targetValue != null && targetValue.getClass().equals(clazz)){
            return (T)targetValue;
        } else {
            return parser.parseTheActualObject(resource, clazz, key);
        }
    }
}
