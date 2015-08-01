package uta.com.studentcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import uta.com.Cart.CartHome;
import uta.com.search.SearchInput;

/**
 * Created by vivekraveendran on 7/5/2015.
 */
public class CurrentStudentHome extends Activity implements View.OnClickListener{

    GridView grid;
    ImageButton signout;
    ProgressDialog progressDialog;
    Context context;
    Spinner termSpinner;

    String[] gridItems = {
            "Search",
            "Add to cart",
            "Enroll",
            "Current Schedule",
            "Drop",
            "Swap",
            "Financial",
            "Personal Info",
            "Holds"

    } ;
    int[] imageId = {
            R.drawable.search,
            R.drawable.addtocart,
            R.drawable.enroll,
            R.drawable.schedule,
            R.drawable.drop,
            R.drawable.swap,
            R.drawable.finance,
            R.drawable.personalinfo,
            R.drawable.holds
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_home);
        context = this;
        initView();
    }

    void initView(){

        grid = (GridView) findViewById(R.id.current_student_home_grid);
        grid.setAdapter(new CustomGrid(this,gridItems, imageId));
        termSpinner = (Spinner) findViewById(R.id.current_home_term_spinner);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences prefs = context.getSharedPreferences(
                        "studentcenter", Context.MODE_PRIVATE);
                prefs.edit().putString("home_term", termSpinner.getSelectedItem().toString()).apply();

                if (position == 0) {

                    Intent searchInputIntent = new Intent(CurrentStudentHome.this, SearchInput.class);
                    startActivity(searchInputIntent);

                }else if(position == 1){

                    Intent cartHomeIntent = new Intent(CurrentStudentHome.this, CartHome.class);
                    startActivity(cartHomeIntent);
                }
            }
        });
        signout = (ImageButton) findViewById(R.id.btn_signout);
        signout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.equals(signout)){

            progressDialog = ProgressDialog.show(context, "Logout", "Signing Out ..", true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            SharedPreferences prefs = context.getSharedPreferences("studentcenter", Context.MODE_PRIVATE);
                            prefs.edit().putString("net_id", "").apply();
                            prefs.edit().putString("student_status", "").apply();

                            progressDialog.dismiss();
                            Intent loginIntent = new Intent(CurrentStudentHome.this, login.class);
                            startActivity(loginIntent);
                            finish();

                        }
                    });
                }
            }, 1000);
        }
    }

    public class CustomGrid extends BaseAdapter {
        private Context mContext;
        private final String[] web;
        private final int[] Imageid;

        public CustomGrid(Context c,String[] web,int[] Imageid ) {
            mContext = c;
            this.Imageid = Imageid;
            this.web = web;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return web.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_item, null);
                TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                textView.setText(web[position]);
                imageView.setImageResource(Imageid[position]);
            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }
}
