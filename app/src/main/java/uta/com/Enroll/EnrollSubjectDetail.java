package uta.com.Enroll;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uta.com.Cart.ViewCart;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 8/4/2015.
 */
public class EnrollSubjectDetail extends Activity implements View.OnClickListener{

    String courseNumber, uniqueCode, courseName, instructor, startDate, endDate, room, strength, time, predecessorPage;
    TextView courseNumberTxt, courseNameTxt, instructorTxt, startDateTxt, endDateTxt, roomTxt, strengthTxt, timeTxt;
    ImageButton backButton;
    Button dropBtn, swapBtn;
    Context context;
    ProgressDialog progressDialog;

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

        backButton = (ImageButton) findViewById(R.id.btn_enroll_subject_detail_back);
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

        if (v.equals(backButton)){

            finish();

        } else if (v.equals(dropBtn)){

            progressDialog = ProgressDialog.show(context, "Drop", "Dropping course ..", true);
            new DroptBackground().execute("");

        }else if (v.equals(swapBtn)){

            progressDialog = ProgressDialog.show(context, "Cart", "Retrieving cart items ..", true);
            new GetCartBackground().execute("");
        }

    }

    class DroptBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String response;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                String term =  prefs.getString("search_term", "");
                String netId = prefs.getString("net_id", "");
                String uniquecode = uniqueCode;

                response = Webservice.drop(netId, term, uniquecode);

            } catch (Exception e) {
                this.exception = e;
            }
            return response;
        }

        protected void onPostExecute(String result) {

            try {
                if (response.equals("failed")){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    progressDialog.dismiss();
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Course couldnt be dropped",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, 1000);


                }else{

                    Toast.makeText(getApplicationContext(), "Course dropped",
                            Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }, 1500);
                }
            }catch (Exception e){

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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                        if (cartDetails.equals("failed")){


                            Toast.makeText(getApplicationContext(), "Cart empty",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                            SharedPreferences prefs = context.getSharedPreferences(
                                    "studentcenter", Context.MODE_PRIVATE);
                            prefs.edit().putString("swap_selected_sub_name", courseName).apply();
                            prefs.edit().putString("swap_selected_sub_number", courseNumber).apply();
                            prefs.edit().putString("swap_selected_sub_insrtructor", instructor).apply();
                            prefs.edit().putString("swap_selected_sub_unique_code", instructor).apply();

                            Intent viewCartIntent = new Intent(EnrollSubjectDetail.this, ViewCart.class);
                            viewCartIntent.putExtra("result", cartDetails);
                            viewCartIntent.putExtra("coming_from","enroll_subject_details");
                            startActivity(viewCartIntent);
                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }
}
