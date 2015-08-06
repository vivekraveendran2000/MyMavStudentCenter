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

import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 8/6/2015.
 */
public class Swap extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Button swapBtn;

    TextView selectedNumberTxt, selectedNameTxt, selectedInstructorTxt;
    TextView swapWithNumberTxt, swapWithNameTxt, swapWithInstructorTxt;
    String selectedNumber, selectedName, selectedInstructor, selectedUniqueCode;
    String swapWithNumber, swapWithName, swapWithInstructor, swapWithUniqueCode;

    Context context;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swap);
        context = this;
        initData();
        initView();
        loadData();
    }

    void initData(){

        SharedPreferences prefs = context.getSharedPreferences(
                "studentcenter", Context.MODE_PRIVATE);
        selectedNumber = prefs.getString("swap_selected_sub_name","");
        selectedName = prefs.getString("swap_selected_sub_number","");
        selectedInstructor = prefs.getString("swap_selected_sub_insrtructor","");
        selectedUniqueCode = prefs.getString("swap_selected_sub_unique_code","");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            swapWithNumber = extras.getString("number");
            swapWithName  = extras.getString("name");
            swapWithInstructor  = extras.getString("instructor");
            swapWithUniqueCode = extras.getString("unique_code");
        }

    }

    void initView(){

        selectedNumberTxt = (TextView) findViewById(R.id.txt_swap_selected_number);
        selectedNameTxt = (TextView) findViewById(R.id.txt_swap_selected_name);
        selectedInstructorTxt = (TextView) findViewById(R.id.txt_swap_selected_instructor);

        swapWithNumberTxt = (TextView) findViewById(R.id.txt_swap_with_number);
        swapWithNameTxt = (TextView) findViewById(R.id.txt_swap_with_name);
        swapWithInstructorTxt = (TextView) findViewById(R.id.txt_swap_with_instructor);

        backBtn = (ImageButton) findViewById(R.id.btn_swap_back);
        backBtn.setOnClickListener(this);

        swapBtn = (Button) findViewById(R.id.btn_swap_course);
        swapBtn.setOnClickListener(this);
    }

    void loadData(){

        selectedNumberTxt.setText(selectedNumber);
        selectedNameTxt.setText(selectedName);
        selectedInstructorTxt.setText(selectedInstructor);

        swapWithNumberTxt.setText(swapWithNumber);
        swapWithNameTxt.setText(swapWithName);
        swapWithInstructorTxt.setText(swapWithInstructor);

    }

    @Override
    public void onClick(View v) {

        if(v.equals(swapBtn)) {

            progressDialog = ProgressDialog.show(context, "Swap", "Swapping course ..", true);
            new SwapBackground().execute("");

        }else if (v.equals(backBtn)){

            SharedPreferences prefs = context.getSharedPreferences(
                    "studentcenter", Context.MODE_PRIVATE);
            prefs.edit().putString("swap_status", "fail").apply();
            finish();
        }
    }

    class SwapBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String response;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                String term =  prefs.getString("search_term", "");
                String netId = prefs.getString("net_id", "");

                response = Webservice.swap(netId, term, selectedUniqueCode, swapWithUniqueCode);

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
                            Toast.makeText(getApplicationContext(), "Course couldnt be swapped",
                                    Toast.LENGTH_LONG).show();
                        }
                    }, 1000);


                }else{

                    Toast.makeText(getApplicationContext(), "Course swap success !!",
                            Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            SharedPreferences prefs = context.getSharedPreferences(
                                    "studentcenter", Context.MODE_PRIVATE);
                            prefs.edit().putString("swap_status", "success").apply();
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
}
