package uta.com.studentcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class login extends Activity implements View.OnClickListener{

    Button loginBtn;
    EditText userNameTxt, passwordTxt;
    Context context;
    ProgressDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();
        context = this;
    }

    void initViews(){

        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
        userNameTxt = (EditText) findViewById(R.id.txt_login_user_name);
        passwordTxt = (EditText) findViewById(R.id.txt_login_user_password);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(loginBtn)){

            if (userNameTxt.getText().toString().equals("") || passwordTxt.getText().toString().equals("") ){

                Toast.makeText(getApplicationContext(), "Login credentials are empty", Toast.LENGTH_SHORT).show();

            }else{

                hideKeyboard();
                loginProgressDialog = ProgressDialog.show(context, "Login", "Signing In ..", true);

                new LoginBackground().execute("");
            }
        }
    }

    void hideKeyboard(){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    class LoginBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String loginStatus;
        String studentStatus;
        JSONObject loginJson;

        protected String doInBackground(String... urls) {
            try {

                loginJson = Webservice.login(userNameTxt.getText().toString(), passwordTxt.getText().toString());
                if (loginJson.getString("success").equals("1")){
                        loginStatus = "Success";
                    if(loginJson.getString("type").equals("C")){
                       studentStatus = "current";
                    }else{
                        studentStatus = "prospective";
                    }
                }else{
                    loginStatus = "failure";
                }


            } catch (Exception e) {
                this.exception = e;
            }
            return loginStatus;
        }

        protected void onPostExecute(String result) {

            try {
                if (loginStatus == "Success") {

                    Toast.makeText(getApplicationContext(), "Login successful",
                            Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = context.getSharedPreferences(
                            "studentcenter", Context.MODE_PRIVATE);
                    prefs.edit().putString("net_id", userNameTxt.getText().toString()).apply();
                    prefs.edit().putString("student_status", studentStatus).apply();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loginProgressDialog.dismiss();
                                }
                            });
                            if (studentStatus == "current") {

                                Intent currentStudentHomeIntent = new Intent(login.this, CurrentStudentHome.class);
                                context.startActivity(currentStudentHomeIntent);
                                finish();
                            } else {

                                Intent prospectiveHomeIntent = new Intent(login.this, ProspectiveHome.class);
                                context.startActivity(prospectiveHomeIntent);
                                finish();
                            }
                        }
                    }, 1000);

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginProgressDialog.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Invalid user name and password",
                            Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){

            }
        }
    }
}
