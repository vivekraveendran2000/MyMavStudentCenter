package uta.com.studentcenter;

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
import android.widget.Toast;

import org.json.JSONObject;

import uta.com.Application.ApplicationResult;
import uta.com.PersonalInfo.PersonalHome;
import uta.com.ToDoList.ToDoListResult;


/**
 * Created by vivekraveendran on 7/17/2015.
 */
public class ProspectiveHome extends Activity implements View.OnClickListener{

    ImageButton signOutBtn;
    Context context;
    Button applicationBtn, todoBtn, personalInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prospective_home);
        context = this;
        initViews();
    }

    void initViews(){

        signOutBtn = (ImageButton) findViewById(R.id.btn_prospective_signout);
        signOutBtn.setOnClickListener(this);
        applicationBtn = (Button) findViewById(R.id.btn_prospective_home_application);
        todoBtn = (Button) findViewById(R.id.btn_prospective_home_todo);
        applicationBtn.setOnClickListener(this);
        todoBtn.setOnClickListener(this);
        personalInfo = (Button) findViewById(R.id.btn_prospective_home_personal_info);
        personalInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(signOutBtn)) {


            progressDialog = ProgressDialog.show(context, "Logout", "Signing Out ..", true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            SharedPreferences prefs = context.getSharedPreferences("studentcenter", Context.MODE_PRIVATE);
                            prefs.edit().putString("net_id", "").apply();
                            prefs.edit().putString("student_status", "").apply();

                            progressDialog.dismiss();
                            Intent loginIntent = new Intent(ProspectiveHome.this, login.class);
                            startActivity(loginIntent);
                            finish();
                        }
                    });
                }
            }, 1000);


        }else if(v.equals(applicationBtn)){

            try {

                progressDialog = ProgressDialog.show(context, "Application Status", "Retrieving applications ..", true);
                new ApplicationListBackground().execute("");

            }catch (Exception e){

            }

        }else if (v.equals(todoBtn)){

            try {

                progressDialog = ProgressDialog.show(context, "To Do List", "Retrieving to do list ..", true);
                new ToDoListBackground().execute("");

            }catch (Exception e){

            }

        }else if (v.equals(personalInfo)){


            try {

                progressDialog = ProgressDialog.show(context, "Personal Information", "Retrieving personal information ..", true);
                new GetPersonalInfo().execute("");

            }catch (Exception e){

            }
        }
    }

    class ApplicationListBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String applicationData;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences("studentcenter", Context.MODE_PRIVATE);
                String netid = prefs.getString("net_id","");
                applicationData = Webservice.getApplications(netid);

            } catch (Exception e) {
                this.exception = e;
            }
            return "";
        }

        protected void onPostExecute(String result) {

            try {
                if (applicationData.equals("failed")) {

                    Toast.makeText(getApplicationContext(), "No applications found",
                            Toast.LENGTH_SHORT).show();

                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });

                            Intent applicationResultIntent = new Intent(ProspectiveHome.this, ApplicationResult.class);
                            applicationResultIntent.putExtra("result", applicationData);
                            startActivity(applicationResultIntent);

                        }
                    }, 1000);
                }
            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }


    class ToDoListBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String toDoListData;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences("studentcenter", Context.MODE_PRIVATE);
                String netid = prefs.getString("net_id","");
                toDoListData = Webservice.getToDoList(netid);

            } catch (Exception e) {
                this.exception = e;
            }
            return "";
        }

        protected void onPostExecute(final String result) {

            try {
                if (toDoListData.equals("failed")) {

                    Toast.makeText(getApplicationContext(), "To do list not found",
                            Toast.LENGTH_SHORT).show();

                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });

                            Intent toDoListIntent = new Intent(ProspectiveHome.this, ToDoListResult.class);
                            toDoListIntent.putExtra("result", toDoListData);
                            startActivity(toDoListIntent);

                        }
                    }, 1000);
                }
            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }

    class GetPersonalInfo extends AsyncTask<String, String, String> {

        private Exception exception;
        String personalInfo;
        String netId;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                netId = prefs.getString("net_id","");

                personalInfo = Webservice.getPersonalInfo(netId);

            } catch (Exception e) {
                this.exception = e;
            }
            return personalInfo;
        }

        protected void onPostExecute(String result) {

            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        if (personalInfo.equals("failed")){

                            Toast.makeText(getApplicationContext(), "Personal data not available",
                                    Toast.LENGTH_LONG).show();

                        }else{

                            Intent personaInfoIntent = new Intent(ProspectiveHome.this, PersonalHome.class);
                            personaInfoIntent.putExtra("result",personalInfo);
                            startActivity(personaInfoIntent);
                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }
}
