package com.pzj.framework.armyant.load;

import com.pzj.framework.armyant.load.fastjson.FastjsonResource;

/**
 * Created by Saber on 2017/3/18.
 */
public interface Loader {
    void parser(FastjsonResource resources);

    Object get();

    <T> T get(Class<T> clazz);

    Object get(String key);

    <T> T get(String key, Class<T> clazz);
}
