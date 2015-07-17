package uta.com.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uta.com.Model.Course;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchResult extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Course> search_courses;
    Course tempCourse;
    JSONObject searchJson;

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

        try {
            String result = "";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                result = extras.getString("result");
            }
            Log.e("result", result);

            JSONArray mJsonArray = new JSONArray(result);
            search_courses = new ArrayList<Course>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempCourse = new Course();
                String tmp = mJsonObject.getString("course_name");
                if(mJsonObject.getString("course_name") != null) {
                    tempCourse.setCourse_name(mJsonObject.getString("course_name"));
                }
                if(mJsonObject.getString("course_strength") != null) {
                    tempCourse.setCourse_strength(mJsonObject.getString("course_strength"));
                }
                if(mJsonObject.getString("course_time") != null) {
                    tempCourse.setCourse_time(mJsonObject.getString("course_time"));
                }
                if(mJsonObject.getString("room_no") != null) {
                    tempCourse.setRoom_no(mJsonObject.getString("room_no"));
                }
                if(mJsonObject.getString("start_date") != null) {
                    tempCourse.setStart_date(mJsonObject.getString("start_date"));
                }
                if(mJsonObject.getString("end_date") != null) {
                    tempCourse.setEnd_date(mJsonObject.getString("end_date"));
                }
                if(mJsonObject.getString("course_num") != null) {
                    tempCourse.setCourse_num(mJsonObject.getString("course_num"));
                }
                if(mJsonObject.getString("instructor_name") != null) {
                    tempCourse.setInstructor_name(mJsonObject.getString("instructor_name"));
                }

                search_courses.add(tempCourse);

            }
        }catch (Exception e){
            e.printStackTrace();
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
