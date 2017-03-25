package com.pzj.framework.armyant.load.fastjson.loaders;

import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public class MapBasket extends ObjectBasket {
    private Map<String, Object> targetData = null;

    public MapBasket(Object resource, Map<String, Object> targetData) {
        super(resource, targetData);
        this.targetData = targetData;
    }

    @Override
    public Map<String, Object> get() {
        return targetData;
    }

    @Override
    public Object get(String key) {
        if (this.targetData != null){
            return this.targetData.get(key);
        }
        return null;
    }

}
