package com.pzj.framework.armyant.load;

/**
 * Created by Saber on 2017/3/18.
 */
public interface LoadParser {
    Object parseTheActualObject(LoadResource resource);

    Object parseTheActualObject(LoadResource resource, String key);

    <T> T parseTheActualObject(LoadResource resource, Class<T> clazz);

    <T> T parseTheActualObject(LoadResource resource, Class<T> clazz, String key);
}
