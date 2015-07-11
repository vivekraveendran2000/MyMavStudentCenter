package uta.com.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/7/2015.
 */
public class SearchInput extends Activity implements View.OnClickListener{

    ImageButton searchBtn,backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_input);
        initViews();
    }

    public  void initViews(){

        searchBtn = (ImageButton) findViewById(R.id.btn_search_input);
        searchBtn.setOnClickListener(this);
        backBtn = (ImageButton) findViewById(R.id.btn_search_input_back);
        backBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v.equals(searchBtn)){

            Intent searchResultIntent = new Intent(SearchInput.this, SearchResult.class);
            startActivity(searchResultIntent);

        }else if (v.equals(backBtn)) {
            finish();
        }
    }
}
