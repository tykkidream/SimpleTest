package com.pzj.framework.armyant.load.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.pzj.framework.armyant.load.Resource;

/**
 * Created by Saber on 2017/3/18.
 */
public class FastjsonResource implements Resource {

    private Object sourceData;

    public FastjsonResource(String filePath){
        sourceData = LoadJsonFileUtil.readOneFromClasspath(filePath);
    }

    public Object getValue(String key) {
        if (sourceData instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)sourceData;
            return jsonObject.get(key);
        }
        return null;
    }

    public Object getResource() {
        return sourceData;
    }

}
