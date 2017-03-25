package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Parser;

/**
 * Created by Saber on 2017/3/19.
 */
public class BeanParser extends ParentParser implements Parser {
    private Class clazz = null;

    public BeanParser(Class clazz){
        super();
        this.clazz = clazz;
    }

    public void setClazz(Class clazz){
        this.clazz = clazz;
    }

    public Class getClazz(){
        return this.clazz;
    }

    @Override
    public Object parseTheActualObject(Object resource) {
        return parseTheActualObject(resource, clazz);
    }

}
