package com.mlm.gradedemo;

import android.app.Activity;
import android.os.Bundle;


import com.mlm.gradebar.GradeBar;
import com.mlm.gradedemo.util.pointsMapper;

public class MainActivity extends Activity {

    GradeBar gradeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gradeBar = (GradeBar) findViewById(R.id.gradebar);
        initData();
    }

    private void initData() {
        String points = getJsonNet();
        setGradeFromServer(points);
    }


    /**
     * your must open a thread to get json from service and parser json to get the score
     *
     * @return
     */
    private String getJsonNet() {
        //just if the points is 32670
        return "22670";
    }


    /**
     * set grade
     *
     * @param points
     */
    private void setGradeFromServer(String points) {
        if (points != null && !"".equals(points)) {
            int pointsInt = Integer.parseInt(points);
            gradeBar.setDividerList(pointsMapper.getMap(), true);
            gradeBar.setGrade(pointsInt);
        }
    }


}
