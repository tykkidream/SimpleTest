package com.pzj.framework.armyant.load.fastjson.loader;

import com.pzj.framework.armyant.load.Loader;
import com.pzj.framework.armyant.load.fastjson.parsers.SimpleParser;

/**
 * Created by Saber on 2017/3/19.
 */
public class SimpleLoader extends ParentLoader<SimpleParser> implements Loader {

    public SimpleLoader(SimpleParser parser) {
        super(parser);
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return parser.parseTheActualObject(resource, clazz);
    }


    @Override
    public Object get(String key) {
        return parser.parseTheActualObject(resource, key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return parser.parseTheActualObject(resource, clazz, key);
    }
}
