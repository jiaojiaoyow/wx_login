package com.example.demo.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public String change_str(Date date){
        return df.format(date);
    }

    static public Date change_Date(String date) throws ParseException {
        return df.parse(date);
    }
}
