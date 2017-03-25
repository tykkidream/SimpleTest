package com.pzj.framework.armyant.load.fastjson.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Saber on 2017/3/25.
 */
public class DatePareser extends ParentParser  {
    private String format;
    private SimpleDateFormat sdf;

    public DatePareser(){
        super();
    }

    public DatePareser(String format){
        super();
        this.format = format;
        this.sdf = new SimpleDateFormat(this.format);
    }

    @Override
    public Object parseTheActualObject(Object resource) {
        if (resource instanceof String){
            try {
                Date parse = sdf.parse((String) resource);
                return parse;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
