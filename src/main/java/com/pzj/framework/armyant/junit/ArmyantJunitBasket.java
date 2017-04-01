package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.fastjson.FastjsonLoaderBuild;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;
import com.pzj.framework.armyant.load.fastjson.parsers.BasketParser;
import com.pzj.framework.armyant.load.fastjson.parsers.MultiParser;
import com.pzj.framework.armyant.load.fastjson.parsers.SchemaParser;

import java.util.List;

/**
 * Created by Saber on 2017/3/29.
 */
public class ArmyantJunitBasket implements Basket{
    public static ArmyantJunitBasket build(String resourceClassPaht){

        FastjsonResource resource = new FastjsonResource(resourceClassPaht);

        SchemaParser parser = new SchemaParser();
        parser.registerParserSchema("data", "dataClass");
        parser.registerParserSchema("case", "caseClass");

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
        return (List<Basket>) basket.get("datas");
    }

    @Override
    public Object get() {
        return basket.get();
    }

    @Override
    public Object get(String key) {
        return basket.get(key);
    }
}
