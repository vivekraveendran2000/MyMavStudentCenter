package uta.com.search;

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

import org.json.JSONObject;

import uta.com.studentcenter.CurrentStudentHome;
import uta.com.studentcenter.ProspectiveHome;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchSubjectDetails extends Activity implements View.OnClickListener {

    String courseNumber, uniqueCode, courseName, instructor, startDate, endDate, room, strength, time, predecessorPage;
    TextView courseNumberTxt, courseNameTxt, instructorTxt, startDateTxt, endDateTxt, roomTxt, strengthTxt, timeTxt;
    ImageButton backButton, removeFromCartBtn;
    Button addToCart;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_subject_detail);
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
            predecessorPage = extras.getString("coming_from");
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

        removeFromCartBtn = (ImageButton) findViewById(R.id.btn_cart_remove);
        removeFromCartBtn.setOnClickListener(this);

        addToCart = (Button) findViewById(R.id.btn_search_detail_addcart);
        addToCart.setOnClickListener(this);

        if (predecessorPage.equals("view_cart")){
            addToCart.setText("Enroll");
            removeFromCartBtn.setVisibility(View.VISIBLE);
        }else{
            addToCart.setText("Add to cart");
        }
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

        }else if(v.equals(addToCart)){

            if (predecessorPage.equals("search_result")) {

                progressDialog = ProgressDialog.show(context, "Cart", "Adding to your cart ..", true);
                new AddToCartBackground().execute("");
            }else{

                progressDialog = ProgressDialog.show(context, "Enroll", "Enrolling ...", true);
                new Enroll().execute("");
            }
        }else if (v.equals(removeFromCartBtn)){

            progressDialog = ProgressDialog.show(context, "Cart", "Removing from cart ..", true);
            new RemoveFromCartBackground().execute("");
        }
    }


    class AddToCartBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String response;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                String term =  prefs.getString("search_term", "");
                String netId = prefs.getString("net_id", "");
                String uniquecode = uniqueCode;

                response = Webservice.addToCart(netId, uniquecode,term);

            } catch (Exception e) {
                this.exception = e;
            }
            return response;
        }

        protected void onPostExecute(String result) {

            try {

                JSONObject mJsonObject = new JSONObject(response);
                final String statusMessage = mJsonObject.getString("message");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), statusMessage,
                                Toast.LENGTH_LONG).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                            }
                        });
                    }
                }, 1000);
            }catch (Exception e){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                    }
                });
                Toast.makeText(getApplicationContext(), "Error",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    class RemoveFromCartBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String response;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                String term =  prefs.getString("search_term", "");
                String netId = prefs.getString("net_id", "");
                String uniquecode = uniqueCode;

                response = Webservice.removeFromCart(netId, term, uniquecode);

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
                            Toast.makeText(getApplicationContext(), "Failed to remove from cart",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, 1000);


                }else{

                    Toast.makeText(getApplicationContext(), "Course removed from cart",
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

    class Enroll extends AsyncTask<String, String, String> {

        private Exception exception;
        String response;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                String term =  prefs.getString("search_term", "");
                String netId = prefs.getString("net_id", "");
                String uniquecode = uniqueCode;

                response = Webservice.enroll(netId, term, uniquecode);

            } catch (Exception e) {
                this.exception = e;
            }
            return response;
        }

        protected void onPostExecute(String result) {

            try {

                JSONObject mJsonObject = new JSONObject(response);
                final String statusMessage = mJsonObject.getString("message");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), statusMessage,
                                Toast.LENGTH_LONG).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                }, 1000);
            }catch (Exception e){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                    }
                });
                Toast.makeText(getApplicationContext(), "Error",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}