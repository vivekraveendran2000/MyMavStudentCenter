package uta.com.ToDoList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/17/2015.
 */
public class ToDoListDetail extends Activity implements View.OnClickListener{

    TextView nameTxt, numberTxt, descriptionTxt, dateTxt;
    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_detail);
        initViews();
        loadData();
    }

    void initViews(){

        nameTxt = (TextView) findViewById(R.id.txt_todo_detail_name);
        numberTxt = (TextView) findViewById(R.id.txt_todo_detail_number);
        descriptionTxt = (TextView) findViewById(R.id.txt_todo_detail_description);
        dateTxt = (TextView) findViewById(R.id.txt_todo_detail_date);

        backBtn = (ImageButton) findViewById(R.id.btn_todo_detail_back);
        backBtn.setOnClickListener(this);
    }

    void loadData(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            nameTxt.setText(extras.getString("name"));
            numberTxt.setText(extras.getString("number"));
            descriptionTxt.setText(extras.getString("description"));
            dateTxt.setText(extras.getString("date"));
        }
    }

    @Override
    public void onClick(View v) {

        if (v.equals(backBtn)){
            finish();
        }
    }
}
