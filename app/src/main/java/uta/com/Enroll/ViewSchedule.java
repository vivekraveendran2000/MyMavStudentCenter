package uta.com.Enroll;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import uta.com.search.SearchSubjectDetails;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 8/4/2015.
 */
public class ViewSchedule extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Course> scheduleCourses;
    Course tempCourse;
    JSONObject cartJson;
    ProgressDialog progressDialog;
    String result;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);

        context = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            result = extras.getString("result");
        }
        initData(result);
        initViews();
    }

    void initData(String result){

        try {

            JSONArray mJsonArray = new JSONArray(result);
            scheduleCourses = new ArrayList<Course>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempCourse = new Course();
                if(mJsonObject.getString("course_name") != null) {
                    tempCourse.setCourse_name(mJsonObject.getString("course_name"));
                }

                if(mJsonObject.getString("unique_code") != null) {
                    tempCourse.setUniqueNumber(mJsonObject.getString("unique_code"));
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

                scheduleCourses.add(tempCourse);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_view_schedule_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.view_schedule_list);
        adapter = new ListAdapter(scheduleCourses);
        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                progressDialog = ProgressDialog.show(context, "Cart", "Retrieving cart items ..", true);
                new GetScheduleBackground().execute("");
            }
        }
    }

    class GetScheduleBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String schedule;
        String term;
        String netId;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                term = prefs.getString("search_term","");
                netId = prefs.getString("net_id","");

                schedule = Webservice.viewSchedule(netId, term);

            } catch (Exception e) {
                this.exception = e;
            }
            return schedule;
        }

        protected void onPostExecute(String result) {

            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (schedule.equals("failed")){

                            scheduleCourses = new ArrayList<Course>();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = null;
                                    adapter = new ListAdapter(scheduleCourses);
                                    listView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                }
                            });
                            ;
                        }else{

                            initData(schedule);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = null;
                                    adapter = new ListAdapter(scheduleCourses);
                                    listView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }

    public class ListAdapter extends BaseAdapter implements  View.OnClickListener{

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

            rowView = inflater.inflate(R.layout.search_list_item, null);
            TextView courseNumber = (TextView) rowView.findViewById(R.id.txt_search_list_course_number);
            TextView courseName = (TextView) rowView.findViewById(R.id.txt_search_list_course_name);
            TextView instructor = (TextView) rowView.findViewById(R.id.txt_search_list_instructor);
            ImageButton arrowBtn = (ImageButton) rowView.findViewById(R.id.btn_list_item_select);
            arrowBtn.setOnClickListener(new ClickHandler(position));

            Course tempCs = getItem(position);
            courseNumber.setText(tempCs.getCourse_num());
            courseName.setText(tempCs.getCourse_name());
            instructor.setText(tempCs.getInstructor_name());


            return rowView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class ClickHandler implements View.OnClickListener{

        int position;

        ClickHandler(int position){

            this.position = position;
        }

        @Override
        public void onClick(View v) {

            Course course = scheduleCourses.get(position);
            Intent subjectDetailIntent = new Intent(context, EnrollSubjectDetail.class);
            subjectDetailIntent.putExtra("name",course.getCourse_name());
            subjectDetailIntent.putExtra("unique_code",course.getUniqueNumber());
            subjectDetailIntent.putExtra("number",course.getCourse_num());
            subjectDetailIntent.putExtra("strength",course.getCourse_strength());
            subjectDetailIntent.putExtra("time",course.getCourse_time());
            subjectDetailIntent.putExtra("end_date",course.getEnd_date());
            subjectDetailIntent.putExtra("instructor",course.getInstructor_name());
            subjectDetailIntent.putExtra("room", course.getRoom_no());
            subjectDetailIntent.putExtra("start_date",course.getStart_date());

            startActivityForResult(subjectDetailIntent,1);
        }
    }
}
