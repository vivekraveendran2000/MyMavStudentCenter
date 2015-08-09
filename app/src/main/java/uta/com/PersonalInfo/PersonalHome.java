package uta.com.PersonalInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uta.com.Model.PersonalInfo;
import uta.com.Model.ToDoList;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;
import uta.com.studentcenter.login;

/**
 * Created by vivekraveendran on 8/8/2015.
 */
public class PersonalHome extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    EditText mailingAddressTxt, homeAddressTxt, numberTxt, emailTxt;
    Context context;
    PersonalInfo personalInfo;
    Button updateBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_home);
        context = this;

        try{
            initData();
            initView();
            loadData();

        }catch (Exception e){

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
            JSONObject mJsonObject = new JSONObject();
            mJsonObject = mJsonArray.getJSONObject(0);

            personalInfo = new PersonalInfo();

            if(mJsonObject.getString("mailing_address") != null) {
                personalInfo.setMailingAddress(mJsonObject.getString("mailing_address"));
            }

            if(mJsonObject.getString("home_address") != null) {
                personalInfo.setHomeAddress(mJsonObject.getString("home_address"));
            }

            if(mJsonObject.getString("contact_no") != null) {
                personalInfo.setNumber(mJsonObject.getString("contact_no"));
            }

            if(mJsonObject.getString("email") != null) {
                personalInfo.setEmail(mJsonObject.getString("email"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initView(){

        backBtn = (ImageButton) findViewById(R.id.btn_personal_back);
        backBtn.setOnClickListener(this);

        mailingAddressTxt = (EditText) findViewById(R.id.etxt_personal_mailing_address);
        homeAddressTxt = (EditText) findViewById(R.id.etxt_personal_home_address);
        numberTxt = (EditText) findViewById(R.id.etxt_personal_number);
        emailTxt = (EditText) findViewById(R.id.etxt_personal_email);

        updateBtn = (Button) findViewById(R.id.btn_personal_update);
        updateBtn.setOnClickListener(this);
    }

    void loadData(){

        mailingAddressTxt.setText(personalInfo.getMailingAddress());
        homeAddressTxt.setText(personalInfo.getHomeAddress());
        numberTxt.setText(personalInfo.getNumber());
        emailTxt.setText(personalInfo.getEmail());
    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();

        }else if (v.equals(updateBtn)){

            progressDialog = ProgressDialog.show(context, "Personal Info", "Updating personal info ..", true);
            String mailingAddress = mailingAddressTxt.getText().toString();
            String homeAddress = homeAddressTxt.getText().toString();
            String email = emailTxt.getText().toString();
            String contactNumber = numberTxt.getText().toString();
            new UpdatePersonalInfo(mailingAddress,homeAddress,email,contactNumber).execute("");

        }
    }

    class UpdatePersonalInfo extends AsyncTask<String, String, String> {

        private Exception exception;
        String updateStatus;
        String netId;
        String mailingAddress, homeAddress, email, contactNumber;

        UpdatePersonalInfo(String mailingAddress, String homeAddress, String email, String contactNumber){

            this.mailingAddress = mailingAddress;
            this.homeAddress = homeAddress;
            this.email = email;
            this.contactNumber = contactNumber;
        }

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                netId = prefs.getString("net_id", "");

                updateStatus = Webservice.updatePersonalInfo(netId, mailingAddress, homeAddress, email, contactNumber);

            } catch (Exception e) {
                this.exception = e;
            }
            return updateStatus;
        }

        protected void onPostExecute(String result) {

            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        if (updateStatus.equals("failed")){

                            Toast.makeText(getApplicationContext(), "Update failed",
                                    Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(getApplicationContext(), "Update Success",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }
}
