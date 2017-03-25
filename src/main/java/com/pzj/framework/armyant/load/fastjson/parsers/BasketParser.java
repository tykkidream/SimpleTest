package com.pzj.framework.armyant.load.fastjson.parsers;

import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.Parser;
import com.pzj.framework.armyant.load.fastjson.loaders.MapBasket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Saber on 2017/3/24.
 */
public class BasketParser extends ParentParser {
    private MultiParser parser = new MultiParser();

    public BasketParser() {
        super();
    }


    public void registerParser(String key, Parser parser){
        this.parser.registerParser(key, parser);
    }


    public Parser parserOfRegister(String key){
        return this.parserOfRegister(key);
    }

    @Override
    public Object parseTheActualObject(Object resource) {
        if (resource instanceof List){
            return doParseList((List)resource, parser);
        } else if (resource instanceof Map){
            return doParseMap((Map) resource, parser);
        }
        return null;
    }

    private List<Basket> doParseList(List resources, Parser parser){
        List<Basket> parseResult = new ArrayList<>(resources.size());
        for (Object resource : resources){
            if (resource instanceof Map){
                Basket basket = doParseMap((Map) resource, parser);
                parseResult.add(basket);
            } else {
                throw new RuntimeException();
            }
        }
        return parseResult;
    }

    private Basket doParseMap(Map resources, Parser parser){
        Object parseObject = parser.parseTheActualObject(resources);
        if (parseObject instanceof Map){
            return new MapBasket(resources, (Map)parseObject);
        }
        return null;
    }
}
