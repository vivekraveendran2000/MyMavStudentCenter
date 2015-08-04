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
import android.widget.Toast;

import uta.com.Cart.ViewCart;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 8/4/2015.
 */
public class EnrollHome extends Activity implements View.OnClickListener{

    Button enrollBtn, viewScheduleBtn;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_home);
        context = this;
        initViews();

    }

    void initViews(){

        enrollBtn = (Button) findViewById(R.id.btn_enroll_home_enroll);
        viewScheduleBtn = (Button) findViewById(R.id.btn_enroll_home_schedule);

        enrollBtn.setOnClickListener(this);
        viewScheduleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(viewScheduleBtn)){

            progressDialog = ProgressDialog.show(context, "Schedule", "Retreiving schedule ..", true);
            new GetScheduleBackground().execute("");

        }else if(v.equals(enrollBtn)){

            progressDialog = ProgressDialog.show(context, "Cart", "Retrieving cart items ..", true);
            new GetCartBackground().execute("");

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

                        progressDialog.dismiss();
                        if (schedule.equals("failed")){

                        }else{

                            Intent viewScheduleIntent = new Intent(EnrollHome.this, ViewSchedule.class);
                            viewScheduleIntent.putExtra("result",schedule);
                            startActivity(viewScheduleIntent);
                        }
                    }
                }, 1000);

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

                            Intent viewCartIntent = new Intent(EnrollHome.this, ViewCart.class);
                            viewCartIntent.putExtra("result", cartDetails);
                            startActivity(viewCartIntent);
                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }
}
