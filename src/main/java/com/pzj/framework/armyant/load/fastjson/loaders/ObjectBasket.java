package com.pzj.framework.armyant.load.fastjson.loaders;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.Resource;

/**
 * Created by Saber on 2017/3/19.
 */
public class ObjectBasket implements Basket {
    protected final Object resource;

    protected final Object targetData;

    public ObjectBasket(Object resource, Object targetData){
        this.resource = resource;
        this.targetData = targetData;
    }

    public Object getResource(){
        return resource;
    }

    @Override
    public Object get() {
        return targetData;
    }

    @Override
    public Object get(String key) {
        throw new RuntimeException();
    }
}
