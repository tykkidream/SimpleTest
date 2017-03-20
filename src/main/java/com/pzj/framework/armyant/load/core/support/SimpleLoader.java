package com.pzj.framework.armyant.load.core.support;

import com.pzj.framework.armyant.load.core.LoadDataMap;
import com.pzj.framework.armyant.load.LoadParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saber on 2017/3/18.
 */
public class SimpleLoader {
    private Map<String, LoadParser> loadDataParserMap;

    private LoadDataMap loadDataMap;

    public SimpleLoader(LoadDataMap loadDataMap){
        this.loadDataParserMap = new HashMap<>();
        this.loadDataMap = loadDataMap;
    }

    public <T extends LoadParser> void registerParser(String key, Class<T> parserClass){
        try {
            T parser = parserClass.newInstance();
            registerParser(key, parser);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public void registerParser(String key, LoadParser loadDataParser){
        loadDataParserMap.put(key, loadDataParser);
    }

    public LoadDataMap getLoadDataMap() {
        return loadDataMap;
    }

    public Object getLoadData() {

        return loadDataMap.getValue();
    }
}
