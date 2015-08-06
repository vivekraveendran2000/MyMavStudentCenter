package uta.com.Enroll;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/6/2015.
 */
public class Swap extends Activity{

    TextView selectedNumberTxt, selectedNameTxt, selectedInstructorTxt;
    TextView swapWithNumberTxt, swapWithNameTxt, swapWithInstructorTxt;
    String selectedNumber, selectedName, selectedInstructor, selectedUniqueCode;
    String swapWithNumber, swapWithName, swapWithInstructor, swapWithUniqueCode;

    Context context;


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
    }

    void loadData(){

        selectedNumberTxt.setText(selectedNumber);
        selectedNameTxt.setText(selectedName);
        selectedInstructorTxt.setText(selectedInstructor);

        swapWithNumberTxt.setText(swapWithNumber);
        swapWithNameTxt.setText(swapWithName);
        swapWithInstructorTxt.setText(swapWithInstructor);

    }
}
