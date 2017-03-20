package com.pzj.framework.armyant.load.fastjson.loader;

import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.Loader;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;

/**
 * Created by Saber on 2017/3/19.
 */
public abstract class ParentLoader<T extends LoadParser> implements Loader {
    protected T parser;

    protected FastjsonResource resource;

    private Object targetData;

    public ParentLoader(T parser){
        this.parser = parser;
    }

    @Override
    public void parser(FastjsonResource resource) {
        this.resource = resource;
        this.targetData = parser.parseTheActualObject(resource);
    }

    public T getParser(){
        return parser;
    }

    @Override
    public Object get() {
        return targetData;
    }
}
