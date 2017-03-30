package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.Parser;

import java.util.HashMap;
import java.util.Iterator;
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

            Parser schemaParser = schemaParser(schemaObject);

            parser.registerParser(dataKey, schemaParser);
        }
    }

    private Parser schemaParser(Object schemaObject){
        if (schemaObject == null){
            return null;
        }

        Parser schemaParser ;

        if (schemaObject instanceof Map){
            schemaParser = loadParserOfMap((Map) schemaObject);
        } else if (schemaObject instanceof String){
            schemaParser = loadParserOfString((String) schemaObject);
        } else {
            throw new RuntimeException();
        }
        return schemaParser;
    }
    private Parser loadParserOfMap(Map schemaMap){
        MultiParser multiParser = new MultiParser();
        Iterator<Map.Entry<String, Object>> iterator = schemaMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();

            Parser parser = schemaParser(next.getValue());
            multiParser.registerParser(next.getKey(), parser);
        }
        return multiParser;
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
