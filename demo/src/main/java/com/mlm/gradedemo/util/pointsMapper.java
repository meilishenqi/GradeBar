package com.mlm.gradedemo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiongwei , An Android project Engineer.
 * Data : 2015-11-22  22:20
 * Describe:
 * Version : 1.0
 * Open Source ：GradeBar
 */
public class pointsMapper {

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "女汉子");
        map.put(1000, "萌妹子");
        map.put(8000, "小清新");
        map.put(36000, "白富美");
        map.put(96000, "女神");
        map.put(250000, "女王");
        return map;
    }

    /**
     * 根据积分获取对应称号
     *
     * @param points
     * @return
     */
    public static String getPointsName(String points) {
        Map<Integer, String> map = getMap();
        try {
            Integer i = Integer.valueOf(points);
            int max = 0;
            String name = null;
            int jf = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                jf = entry.getKey();
                if (i >= jf && max <= jf) {
                    /**
                     * 找到在目标points下面一级的名称
                     */
                    max = jf;
                    name = entry.getValue();
                }
            }
            return name;
        } catch (Exception e) {
            return map.get(0);
        }
    }
}
