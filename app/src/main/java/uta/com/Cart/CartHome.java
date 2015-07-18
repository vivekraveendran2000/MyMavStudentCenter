package uta.com.Cart;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import uta.com.search.SearchInput;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/18/2015.
 */
public class CartHome extends Activity implements View.OnClickListener{

    Button searchBtn, viewBtn;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carthome);
        initViews();
    }

    void initViews(){

        searchBtn = (Button) findViewById(R.id.btn_cart_home_search);
        viewBtn = (Button) findViewById(R.id.btn_cart_home_view);
        backBtn = (ImageButton) findViewById(R.id.btn_cart_home_back);

        searchBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(searchBtn)){

            Intent searchIntent = new Intent(CartHome.this, SearchInput.class);
            startActivity(searchIntent);

        }else if (v.equals(backBtn)){

            finish();
        }
    }
}
