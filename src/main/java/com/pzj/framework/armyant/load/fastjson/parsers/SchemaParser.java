package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.Parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Saber on 2017/3/23.
 */
public class SchemaParser extends MultiParser {
    private Map<String, String> schemaMap = new HashMap<>();

    public void registerParserSchema(String key, String schema){
        this.schemaMap.put(key, schema);
    }

    public void registerParserSchema(String key){
        this.schemaMap.put(key, key + "Schema");
    }


    @Override
    public Map<String, Object> parseTheActualObject(Object resource) {
        if (resource instanceof Map){
            Map resourceMap = (Map)resource;
            initParserMap(this,resourceMap, schemaMap);
            return doParse(resourceMap, getParserMap());
        }
        return null;
    }

    private void initParserMap(MultiParser parser, Map resourceMap, Map<String, String> schemaMap) {
        if (resourceMap == null || schemaMap == null){
            return;
        }

        Iterator<Map.Entry<String, String>> schemaIterator = schemaMap.entrySet().iterator();
        while (schemaIterator.hasNext()){
            Map.Entry<String, String> next = schemaIterator.next();
            String dataKey = next.getKey();
            String dataSchema = next.getValue();

            Object schemaObject = resourceMap.get(dataSchema);

            Object dataObject = resourceMap.get(dataKey);

            Parser schemaParser = schemaParser(dataObject, schemaObject);

            parser.registerParser(dataKey, schemaParser);
        }
    }

    private Parser schemaParser(Object dataObject, Object schemaObject){
        if (schemaObject == null){
            return null;
        }

        if (schemaObject instanceof Map){
            if (dataObject instanceof Map) {
                return loadParserOfMap(dataObject, (Map) schemaObject);
            }
            if (dataObject instanceof List){
                return loadParserOfList(dataObject, (Map) schemaObject);
            }
        }
        if (schemaObject instanceof String){
            return loadParserOfString((String) schemaObject);
        }
        throw new RuntimeException();
    }

    private Parser loadParserOfMap(Object dataObject, Map schemaMap){
        MultiParser multiParser = new MultiParser();
        Iterator<Map.Entry<String, Object>> iterator = schemaMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();

            Parser parser = schemaParser(dataObject, next.getValue());
            multiParser.registerParser(next.getKey(), parser);
        }
        return multiParser;
    }

    private Parser loadParserOfList(Object dataObject, Map schemaObject) {
        BasketParser basketParser = new BasketParser();
        Iterator<Map.Entry<String, Object>> iterator = schemaObject.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();

            Parser parser = schemaParser(dataObject, next.getValue());
            basketParser.registerParser(next.getKey(), parser);
        }
        return basketParser;
    }

    private Parser loadParserOfString(String schemaString){
        Class clazz = classOfString(schemaString);
        if (clazz == null){
            return null;
        }
        if (Basket.class.isAssignableFrom(clazz)){
            return new BasketParser();
        } else {
            return new BeanParser(clazz);
        }
    }

    private Class classOfString(String schemaString) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(schemaString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }
}
