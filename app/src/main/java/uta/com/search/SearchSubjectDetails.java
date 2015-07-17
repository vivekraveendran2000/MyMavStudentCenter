package uta.com.search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchSubjectDetails extends Activity {

    String courseNumber, courseName, instructor, startDate, endDate, room, strength, time;
    TextView courseNumberTxt, courseNameTxt, instructorTxt, startDateTxt, endDateTxt, roomTxt, strengthTxt, timeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_subject_detail);
        try {
            initData();
            initView();
            loadData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initData(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            courseName = extras.getString("name");
            courseNumber = extras.getString("number");
            strength = extras.getString("strength");
            time = extras.getString("time");
            endDate = extras.getString("end_date");
            instructor = extras.getString("instructor");
            room = extras.getString("room");
            startDate = extras.getString("start_date");
        }
    }

    void initView(){

        courseNumberTxt = (TextView) findViewById(R.id.txt_detail_number);
        courseNameTxt = (TextView) findViewById(R.id.txt_detail_name);
        instructorTxt = (TextView) findViewById(R.id.txt_detail_instructor);
        startDateTxt = (TextView) findViewById(R.id.txt_detail_start_date);
        endDateTxt = (TextView) findViewById(R.id.txt_detail_end_date);
        roomTxt = (TextView) findViewById(R.id.txt_detail_room);
        strengthTxt = (TextView) findViewById(R.id.txt_detail_strength);
        timeTxt = (TextView) findViewById(R.id.txt_detail_time);
    }

    void loadData(){

        courseNumberTxt.setText(courseNumber);
        courseNameTxt.setText(courseName);
        instructorTxt.setText(instructor);
        startDateTxt.setText(startDate);
        endDateTxt.setText(endDate);
        roomTxt.setText(room);
        strengthTxt.setText(strength);
        timeTxt.setText(time);
    }
}
