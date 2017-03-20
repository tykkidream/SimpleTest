package com.pzj.framework.armyant.load.fastjson.loader;

import com.pzj.framework.armyant.load.fastjson.parsers.SingleParser;

/**
 * Created by Saber on 2017/3/19.
 */
public class SingleLoader extends ParentLoader<SingleParser> {

    public SingleLoader(SingleParser parser) {
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
