package com.pzj.framework.armyant.junit.load;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.fastjson.FastjsonLoaderBuild;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;
import com.pzj.framework.armyant.load.fastjson.parsers.SchemaParser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Saber on 2017/3/29.
 */
public class ArmyantJunitBasket {
    public static ArmyantJunitBasket build(String resourceClassPaht){

        FastjsonResource resource = new FastjsonResource(resourceClassPaht);

        SchemaParser parser = new SchemaParser();
        parser.registerParserSchema("data", "dataClass");

        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        if (basket == null){
            return null;
        }

        ArmyantJunitBasket ajbasket = new ArmyantJunitBasket();
        ajbasket.basket = basket;
        return ajbasket;
    }

    private ArmyantJunitBasket(){

    }

    private Basket basket;

    public Object getData(){
        return basket.get("data");
    }

    public List<Basket> getDatas(){
        Object data = basket.get("data");
        if (data == null){
            return null;
        }
        if (data instanceof List){
            return (List)data;
        }
        return Arrays.asList((Basket)data);
    }


    public Object get(String key) {
        return basket.get(key);
    }
}
