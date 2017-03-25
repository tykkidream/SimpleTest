package com.pzj.framework.armyant.load.fastjson.parsers;

/**
 * Created by Saber on 2017/3/25.
 */
public class BaseParser extends BeanParser {

    public BaseParser(){
        super(null);
    }

    public BaseParser(Class clazz){
        super(clazz);
    }

    @Override
    public Object parseTheActualObject(Object resource) {


        return super.parseTheActualObject(resource);
    }
}
