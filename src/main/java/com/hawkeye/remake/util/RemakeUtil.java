package com.hawkeye.remake.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawkeye.remake.entity.Country;
import com.hawkeye.remake.entity.Data;
import com.hawkeye.remake.entity.Segment;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemakeUtil {
    public static int pre;
    public static Map<Segment,String> map;
    public static List<Segment> segments;

    public static void getData(){
        pre = 0;
        map = new HashMap<>();
        segments = new ArrayList<>();
        JSONArray array = JSON.parseArray(Data.getData());
        if (array != null) {
            for (Object o : array) {
                JSONObject jsonObject = (JSONObject) o;
                Country country = JSON.toJavaObject(jsonObject, Country.class);
                int birthCount = (int) (country.getBirthRate() * country.getPopulation());
                Segment s = new Segment(pre, pre + birthCount);
                segments.add(s);
                pre += birthCount;
                map.put(s, country.getName());
            }
        }
    }
}
