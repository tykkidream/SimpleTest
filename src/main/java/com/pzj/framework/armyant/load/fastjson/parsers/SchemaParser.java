package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Parser;
import com.pzj.framework.armyant.load.Loader;

import java.util.Map;

/**
 * Created by Saber on 2017/3/23.
 */
public class SchemaParser extends ParentParser {
    private String schemaKey;
    private Parser parser = null;

    public SchemaParser(String schemaKey){
        super();
        setSchemaKey(schemaKey);
    }

    public String getSchemaKey(){
        return schemaKey;
    }

    public void setSchemaKey(String schemaKey){
        this.schemaKey = schemaKey;
    }


    @Override
    public Object parseTheActualObject(Object resource) {
        if (resource instanceof Map){
            this.parser = loadParserOfMap((Map) resource);
            if (this.parser != null) {
                return this.parser.parseTheActualObject(resource);
            }
        }
        return null;
    }


    private Parser loadParserOfMap(Map schema) {
        Object schemaObject = schema.get(schemaKey);
        if (schemaObject instanceof String){
            return loadParserOfString((String)schemaObject);
        } else if (schemaObject instanceof Map){
            return loadParserOfMap((Map)schemaObject);
        }
        return null;
    }

    private Parser loadParserOfString(String schemaString){
        Class clazz = classOfString(schemaString);
        if (clazz == null){
            return null;
        }
        if (Loader.class.isAssignableFrom(clazz)){
            return new LoaderParser();
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
