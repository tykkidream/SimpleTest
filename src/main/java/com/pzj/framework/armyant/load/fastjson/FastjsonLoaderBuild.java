package com.pzj.framework.armyant.load.fastjson;

import com.pzj.framework.armyant.load.Parser;
import com.pzj.framework.armyant.load.Resource;
import com.pzj.framework.armyant.load.Loader;
import com.pzj.framework.armyant.load.fastjson.loaders.ObjectLoader;
import com.pzj.framework.armyant.load.fastjson.loaders.MapLoader;

import java.util.Map;

/**
 * Created by Saber on 2017/3/18.
 */
public class FastjsonLoaderBuild {

    public static Loader build(Resource resource, Parser parser){
        Object loadResult = parser.parseTheActualObject(resource.getResource());
        if (loadResult == null){
            return null;
        }
        if (loadResult instanceof Map){
            return new MapLoader(resource, (Map) loadResult);
        } else {
            return new ObjectLoader<>(resource, loadResult);
        }
    }
}
