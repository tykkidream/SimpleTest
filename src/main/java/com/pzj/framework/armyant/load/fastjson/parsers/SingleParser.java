package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.LoadResource;

/**
 * Created by Saber on 2017/3/19.
 */
public class SingleParser extends ParentParser implements LoadParser {
    private Class clazz = null;

    public SingleParser(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public Object parseTheActualObject(LoadResource resource) {
        return parseTheActualObject(resource, clazz);
    }

}
