package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public class MultiParser extends ParentParser implements Parser {

    private Map<String, Parser> parserMap = new HashMap<>();

    public MultiParser() {
        super();
    }

    public Map<String, Parser> getParserMap() {
        return parserMap;
    }

    public void registerParser(String key, Parser parser){
        parserMap.put(key, parser);
    }


    public Parser parserOfRegister(String key){
        return parserMap.get(key);
    }

    @Override
    public Map<String, Object> parseTheActualObject(Object resource) {
        if (resource instanceof Map){
            Map resourceMap = (Map)resource;
            return doParse(resourceMap, parserMap);
        }
        return null;
    }

    protected Map<String, Object> doParse(Map resourceMap, Map<String, Parser> parserMap){
        Map<String, Object> parserResult = new HashMap<>(resourceMap);

        Iterator<Map.Entry<String, Parser>> iterator = parserMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Parser> next = iterator.next();
            String key = next.getKey();
            Parser parser = next.getValue();

            Object resourceObject = resourceMap.get(key);

            if (resourceObject == null){
                continue;
            }

            Object parserObject = parser.parseTheActualObject(resourceObject);

            parserResult.put(key, parserObject);
        }

        return parserResult;
    }
}
