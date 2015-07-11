package uta.com.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uta.com.Model.Course;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchResult extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Course> search_courses;
    Course tempCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        try {
            initData();
            initViews();
            context = this;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initData(){

        search_courses = new ArrayList<Course>(10);
        for (int i = 0; i < 10; i++) {

            tempCourse = new Course();
            tempCourse.setCourse_name("Database systems 2");
            tempCourse.setCourse_num("CSE 5325");
            tempCourse.setCourse_strength("50");
            tempCourse.setCourse_time("Mon Wed 5:30 - 6:30");
            tempCourse.setEnd_date("7/15/2015");
            tempCourse.setInstructor_name("John Howard Robb");
            tempCourse.setRoom_no("WH 208");
            tempCourse.setStart_date("5/8/2015");
            search_courses.add(tempCourse);
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_search_result_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.search_result_list);
        listView.setAdapter(new ListAdapter(search_courses));
    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    public class ListAdapter extends BaseAdapter {

        private  ArrayList<Course> courses;

        ListAdapter(ArrayList<Course> courses) {
            this.courses = courses;
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Course getItem(int position) {
            return courses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                rowView = inflater.inflate(R.layout.search_list_item, null);
                TextView courseNumber = (TextView) rowView.findViewById(R.id.txt_search_list_course_number);
                TextView courseName = (TextView) rowView.findViewById(R.id.txt_search_list_course_name);
                TextView instructor = (TextView) rowView.findViewById(R.id.txt_search_list_instructor);

                Course tempCs = getItem(position);
                courseNumber.setText(tempCs.getCourse_num());
                courseName.setText(tempCs.getCourse_name());
                instructor.setText(tempCs.getInstructor_name());

            }else{

                rowView = (View) convertView;
            }

            return rowView;
        }
    }
}
