package com.pzj.framework.armyant.load.fastjson;

import com.pzj.framework.armyant.load.LoadParser;
import com.pzj.framework.armyant.load.Loader;
import com.pzj.framework.armyant.load.fastjson.loader.ParentLoader;
import com.pzj.framework.armyant.load.fastjson.loader.SimpleLoader;
import com.pzj.framework.armyant.load.fastjson.loader.SingleLoader;
import com.pzj.framework.armyant.load.fastjson.loader.VarietyLoader;
import com.pzj.framework.armyant.load.fastjson.parsers.SimpleParser;
import com.pzj.framework.armyant.load.fastjson.parsers.SingleParser;
import com.pzj.framework.armyant.load.fastjson.parsers.VarietyParser;

/**
 * Created by Saber on 2017/3/18.
 */
public class FastjsonLoader implements Loader {

    private Loader loader;

    public FastjsonLoader(){
        super();
    }

    public void parser(FastjsonResource resources){
        SimpleParser parser = new SimpleParser();
        SimpleLoader loader = new SimpleLoader(parser);
        doParser(resources, loader);
    }

    public void parser(FastjsonResource resources, Class<?> clazz){
        SingleParser parser = new SingleParser(clazz);
        SingleLoader loader = new SingleLoader(parser);
        doParser(resources, loader);
    }

    public void parser(FastjsonResource resources, LoadParser parser){
        VarietyLoader loader = new VarietyLoader((VarietyParser)parser);
        doParser(resources, loader);
    }

    private void doParser(FastjsonResource resources, ParentLoader loader){
        loader.parser(resources);
        this.loader = loader;
    }

    @Override
    public Object get() {
        return loader.get();
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return loader.get(clazz);
    }


    @Override
    public Object get(String key) {
        return loader.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return loader.get(key, clazz);
    }
}
