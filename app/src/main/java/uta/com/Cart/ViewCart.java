package uta.com.Cart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uta.com.Enroll.Swap;
import uta.com.Model.Course;
import uta.com.search.SearchSubjectDetails;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 7/25/2015.
 */
public class ViewCart extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Course> cartCourses;
    Course tempCourse;
    JSONObject cartJson;
    ProgressDialog progressDialog;
    String result;
    ListAdapter adapter;
    String predecessorPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_cart);
        context = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            result = extras.getString("result");
        }
        predecessorPage = extras.getString("coming_from");
        initData(result);
        initViews();
    }

    void initData(String result){

        try {

            JSONArray mJsonArray = new JSONArray(result);
            cartCourses = new ArrayList<Course>(mJsonArray.length());
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

                cartCourses.add(tempCourse);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_view_cart_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.view_cart_list);
        adapter = new ListAdapter(cartCourses);
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
                new GetCartBackground().execute("");

            }
        }else if (requestCode == 2){

            SharedPreferences prefs = context.getSharedPreferences(
                    "studentcenter", Context.MODE_PRIVATE);
            String swapStatus = prefs.getString("swap_status", "");

            if (swapStatus.equals("success")) {
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    class GetCartBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String cartDetails;
        String term;
        String netId;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                term = prefs.getString("search_term","");
                netId = prefs.getString("net_id","");

                cartDetails = Webservice.viewCart(netId, term);

            } catch (Exception e) {
                this.exception = e;
            }
            return cartDetails;
        }

        protected void onPostExecute(String result) {

            try {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if (cartDetails.equals("failed")){

                            cartCourses = new ArrayList<Course>();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = null;
                                    adapter = new ListAdapter(cartCourses);
                                    listView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                }
                            });
                            ;
                        }else{

                            initData(cartDetails);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = null;
                                    adapter = new ListAdapter(cartCourses);
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

            Intent tempIntent;
            Course course = cartCourses.get(position);
            if(predecessorPage.equals("enroll_subject_details")){
                tempIntent = new Intent(context, Swap.class);

                tempIntent.putExtra("name",course.getCourse_name());
                tempIntent.putExtra("unique_code",course.getUniqueNumber());
                tempIntent.putExtra("number",course.getCourse_num());
                tempIntent.putExtra("instructor",course.getInstructor_name());

                startActivityForResult(tempIntent,2);

            }else {
                tempIntent = new Intent(context, SearchSubjectDetails.class);
                tempIntent.putExtra("name",course.getCourse_name());
                tempIntent.putExtra("unique_code",course.getUniqueNumber());
                tempIntent.putExtra("number",course.getCourse_num());
                tempIntent.putExtra("strength",course.getCourse_strength());
                tempIntent.putExtra("time",course.getCourse_time());
                tempIntent.putExtra("end_date",course.getEnd_date());
                tempIntent.putExtra("instructor",course.getInstructor_name());
                tempIntent.putExtra("room", course.getRoom_no());
                tempIntent.putExtra("start_date",course.getStart_date());
                tempIntent.putExtra("coming_from", "view_cart");
                startActivityForResult(tempIntent,1);
            }
        }
    }
}
