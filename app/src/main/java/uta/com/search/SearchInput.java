package uta.com.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchInput extends Activity implements View.OnClickListener{

    ImageButton searchBtn,backBtn;
    Spinner departmentSpinner, termSpinner, courseRangeSpinner;
    EditText courseRangeTxt;
    ProgressDialog searchProgressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_input);
        context = this;
        initViews();
    }

    public  void initViews(){

        searchBtn = (ImageButton) findViewById(R.id.btn_search_input);
        searchBtn.setOnClickListener(this);
        backBtn = (ImageButton) findViewById(R.id.btn_search_input_back);
        backBtn.setOnClickListener(this);

        departmentSpinner = (Spinner) findViewById(R.id.spn_search_input_department);
        courseRangeSpinner = (Spinner) findViewById(R.id.spn_search_input_course_range);
        termSpinner = (Spinner) findViewById(R.id.spn_search_input_term);
        courseRangeTxt = (EditText) findViewById(R.id.txt_search_input_course_num);
    }


    @Override
    public void onClick(View v) {

        if(v.equals(searchBtn)){

            if(courseRangeTxt.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Course number cannot be empty", Toast.LENGTH_SHORT).show();
            }else{

                try {

                    hideKeyboard();
                    searchProgressDialog = ProgressDialog.show(context, "Search", "Searching classes ..", true);
                    new SearchBackground().execute("");

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

        }else if (v.equals(backBtn)) {
            finish();
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

    class SearchBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String searchResult;
        String departmentName;
        String restriction;
        String courseNumber;
        String term;

        protected String doInBackground(String... urls) {
            try {

                departmentName = getDepartmentName();
                term = getTerm();
                restriction = getRestriction();
                courseNumber = courseRangeTxt.getText().toString();

                searchResult = Webservice.Search(departmentName, term, courseNumber, restriction);
                Log.e("Search Result",searchResult);

            } catch (Exception e) {
                this.exception = e;
            }
            return searchResult;
        }

        String getDepartmentName(){

            String department = "";

            switch (departmentSpinner.getSelectedItemPosition()){

                case 0:
                    department = "CSE";
                    break;
                case 1:
                    department = "ELE";
                    break;
                case 2:
                    department = "MEC";
                    break;
                case 3:
                    department = "CIV";
                    break;
                case 4:
                    department = "IND";
                    break;
                case 5:
                    department = "EM";
                    break;
            }

            return  department;
        }

        String getTerm(){

            String term = "";

            switch (termSpinner.getSelectedItemPosition()){

                case 0:
                    term = "Summer 2015";
                    break;
                case 1:
                    term = "Fall 2015";
                    break;
                case 2:
                    term = "Spring 2016";
                    break;
            }

            return  term;
        }

        String getRestriction(){

            String restriction = "";

            switch (courseRangeSpinner.getSelectedItemPosition()){

                case 0:
                    restriction = ">";
                    break;
                case 1:
                    restriction = "=";
                    break;
                case 2:
                    restriction = "<";
                    break;
            }

            return restriction;
        }

        protected void onPostExecute(String result) {

            try {
                if (searchResult.equals("failed") ) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchProgressDialog.dismiss();
                                }
                            });
                            Toast.makeText(getApplicationContext(), "No classes found", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);

                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchProgressDialog.dismiss();
                                }
                            });

                            Intent searchResultIntent = new Intent(SearchInput.this, SearchResult.class);
                            searchResultIntent.putExtra("result", searchResult);
                            startActivity(searchResultIntent);
                        }
                    }, 1000);

                }
            }catch (Exception e){

            }
        }
    }
}
