package com.pzj.framework.armyant.load.fastjson;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.Parser;
import com.pzj.framework.armyant.load.Resource;
import com.pzj.framework.armyant.load.fastjson.loaders.MapBasket;
import com.pzj.framework.armyant.load.fastjson.loaders.ObjectBasket;

import java.util.Map;

/**
 * Created by Saber on 2017/3/18.
 */
public class FastjsonLoaderBuild {

    public static Basket build(Resource resource, Parser parser){
        Object loadResult = parser.parseTheActualObject(resource.getResource());
        if (loadResult == null){
            return null;
        }
        if (loadResult instanceof Map){
            return new MapBasket(resource, (Map) loadResult);
        } else {
            return new ObjectBasket<>(resource, loadResult);
        }
    }
}
