package com.pzj.framework.armyant.load.fastjson.loaders;

import com.pzj.framework.armyant.load.Resource;
import com.pzj.framework.armyant.load.Loader;

/**
 * Created by Saber on 2017/3/19.
 */
public class ObjectLoader<T> implements Loader {
    protected final Resource resource;

    protected final T targetData;

    public ObjectLoader(Resource resource, T targetData){
        this.resource = resource;
        this.targetData = targetData;
    }

    public Resource getResource(){
        return resource;
    }

    @Override
    public T get() {
        return targetData;
    }

    @Override
    public Object get(String key) {
        throw new RuntimeException();
    }
}
