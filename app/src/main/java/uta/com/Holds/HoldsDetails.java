package uta.com.Holds;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/8/2015.
 */
public class HoldsDetails extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    TextView nameTxt, descriptionTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holds_details);
        initView();
        loadData();
    }

    void initView(){

        nameTxt = (TextView) findViewById(R.id.txt_hold_details_name);
        descriptionTxt = (TextView) findViewById(R.id.txt_hold_details_description);

        backBtn = (ImageButton) findViewById(R.id.btn_hold_details_back);
        backBtn.setOnClickListener(this);
    }

    void loadData(){

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String name = extras.getString("name");
            String description = extras.getString("description");
            nameTxt.setText(name);
            descriptionTxt.setText(description);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.equals(backBtn)){
            finish();
        }
    }
}
