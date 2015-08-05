package uta.com.Enroll;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/4/2015.
 */
public class EnrollSubjectDetail extends Activity implements View.OnClickListener{

    String courseNumber, uniqueCode, courseName, instructor, startDate, endDate, room, strength, time, predecessorPage;
    TextView courseNumberTxt, courseNameTxt, instructorTxt, startDateTxt, endDateTxt, roomTxt, strengthTxt, timeTxt;
    ImageButton backButton;
    Button dropBtn, swapBtn;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_subject_detail);

        context = this;
        try {
            initData();
            initView();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void initData() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            courseName = extras.getString("name");
            uniqueCode = extras.getString("unique_code");
            courseNumber = extras.getString("number");
            strength = extras.getString("strength");
            time = extras.getString("time");
            endDate = extras.getString("end_date");
            instructor = extras.getString("instructor");
            room = extras.getString("room");
            startDate = extras.getString("start_date");
        }
    }

    void initView() {

        courseNumberTxt = (TextView) findViewById(R.id.txt_detail_number);
        courseNameTxt = (TextView) findViewById(R.id.txt_detail_name);
        instructorTxt = (TextView) findViewById(R.id.txt_detail_instructor);
        startDateTxt = (TextView) findViewById(R.id.txt_detail_start_date);
        endDateTxt = (TextView) findViewById(R.id.txt_detail_end_date);
        roomTxt = (TextView) findViewById(R.id.txt_detail_room);
        strengthTxt = (TextView) findViewById(R.id.txt_detail_strength);
        timeTxt = (TextView) findViewById(R.id.txt_detail_time);

        backButton = (ImageButton) findViewById(R.id.btn_search_subject_detail);
        backButton.setOnClickListener(this);

        dropBtn = (Button) findViewById(R.id.btn_enroll_subject_drop);
        dropBtn.setOnClickListener(this);

        swapBtn = (Button) findViewById(R.id.btn_enroll_subject_swap);
        swapBtn.setOnClickListener(this);
    }

    void loadData() {

        courseNumberTxt.setText(courseNumber);
        courseNameTxt.setText(courseName);
        instructorTxt.setText(instructor);
        startDateTxt.setText(startDate);
        endDateTxt.setText(endDate);
        roomTxt.setText(room);
        strengthTxt.setText(strength);
        timeTxt.setText(time);
    }


    @Override
    public void onClick(View v) {


    }
}
