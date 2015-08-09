package uta.com.PersonalInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uta.com.Model.PersonalInfo;
import uta.com.Model.ToDoList;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/8/2015.
 */
public class PersonalHome extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    EditText mailingAddressTxt, homeAddressTxt, numberTxt, emailTxt;
    Context context;
    PersonalInfo personalInfo;

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
        }
    }
}
