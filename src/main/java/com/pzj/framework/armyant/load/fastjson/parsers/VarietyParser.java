package com.pzj.framework.armyant.load.fastjson.parsers;

import com.alibaba.fastjson.JSONObject;
import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.LoadResource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public class VarietyParser extends ParentParser implements LoadParser{
    private Map<String, Object> parserMap = new HashMap<>();

    public void registerParser(String key, Class parserClass){
        parserMap.put(key, parserClass);
    }

    public void registerParser(String key, String schemaKey){
        parserMap.put(key, schemaKey);
    }

    public void registerParserSchema(String key){
        parserMap.put(key, key + "Schema");
    }

    public Object registerParser(String key){
        return parserMap.get(key);
    }

    @Override
    public Map<String, Object> parseTheActualObject(LoadResource resource) {
        return doParse(resource);
    }

    private Map<String, Object> doParse(LoadResource resource){
        Object resourceObject = resource.getResource();

        if (resourceObject instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)resourceObject;
            return doParseJSONObject(jsonObject, parserMap);
        }
        return null;
    }

    private Map<String, Object> doParseJSONObject(JSONObject jsonObject, Map<String, Object> parserMap){
        Map<String, Object> reuslt = new HashMap<>(jsonObject.size());

        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();

            Object parserObject = parserMap.get(key);
            if (parserObject instanceof Class){
                Object targetObject = autoParse(value, (Class) parserObject);
                reuslt.put(key, targetObject);
            } else {

            }
        }

        return null;
    }
}
