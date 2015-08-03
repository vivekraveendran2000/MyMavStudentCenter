package uta.com.Cart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import uta.com.search.SearchInput;
import uta.com.studentcenter.CurrentStudentHome;
import uta.com.studentcenter.ProspectiveHome;
import uta.com.studentcenter.R;
import uta.com.studentcenter.Webservice;

/**
 * Created by vivekraveendran on 7/18/2015.
 */
public class CartHome extends Activity implements View.OnClickListener{

    Button searchBtn, viewBtn;
    ImageButton backBtn;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

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

        }else if(v.equals(viewBtn)){

            progressDialog = ProgressDialog.show(context, "Cart", "Retrieving cart items ..", true);
            new GetCartBackground().execute("");

        }else if (v.equals(backBtn)){

            finish();
        }
    }

    class GetCartBackground extends AsyncTask<String, String, String> {

        private Exception exception;
        String cartDetails;
        String term;
        String netId;

        protected String doInBackground(String... urls) {
            try {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                term = prefs.getString("search_term","");
                netId = prefs.getString("net_id","");

                cartDetails = Webservice.viewCart(netId,term);

            } catch (Exception e) {
                this.exception = e;
            }
            return cartDetails;
        }

        protected void onPostExecute(String result) {

            try {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                        if (cartDetails.equals("failed")){


                            Toast.makeText(getApplicationContext(), "Cart empty",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                            Intent viewCartIntent = new Intent(CartHome.this, ViewCart.class);
                            viewCartIntent.putExtra("result", cartDetails);
                            startActivity(viewCartIntent);
                        }
                    }
                }, 1000);

            }catch (Exception e){

            }
        }
    }
}
