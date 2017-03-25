package com.pzj.framework.armyant.load.fastjson.loaders;

import com.pzj.framework.armyant.load.Resource;

import java.util.Map;

/**
 * Created by Saber on 2017/3/19.
 */
public class MapLoader extends ObjectLoader<Map<String, Object>> {

    public MapLoader(Resource resource, Map<String, Object> targetData) {
        super(resource, targetData);
    }

    @Override
    public Map<String, Object> get() {
        return super.get();
    }

    @Override
    public Object get(String key) {
        if (this.targetData != null){
            return this.targetData.get(key);
        }
        return null;
    }

}
