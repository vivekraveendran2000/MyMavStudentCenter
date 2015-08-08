
package uta.com.Holds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uta.com.Application.ApplicationDetail;
import uta.com.Model.Application;
import uta.com.Model.Hold;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/7/2015.
 */
public class Holds extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Hold> holdsList;
    Hold tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_holds);
        context = this;

        try {

            initData();
            initView();
            loadData();

        }catch(Exception e){

            e.printStackTrace();
        }

    }

    void initData(){

        try {
            String result = "";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                result = extras.getString("result");
            }
            Log.e("result", result);

            JSONArray mJsonArray = new JSONArray(result);
            holdsList = new ArrayList<Hold>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempList = new Hold();
                if(mJsonObject.getString("name") != null) {
                    tempList.setName(mJsonObject.getString("name"));
                }
                if(mJsonObject.getString("description") != null) {
                    tempList.setDescription(mJsonObject.getString("description"));
                }
                holdsList.add(tempList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void initView(){

        backBtn = (ImageButton) findViewById(R.id.btn_view_hold_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.view_hold_list);
        listView.setAdapter(new ListAdapter(holdsList));
    }

    void loadData(){



    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    public class ListAdapter extends BaseAdapter implements  View.OnClickListener{

        private  ArrayList<Hold> holds;

        ListAdapter(ArrayList<Hold> holds) {
            this.holds = holds;
        }

        @Override
        public int getCount() {
            return holds.size();
        }

        @Override
        public Hold getItem(int position) {
            return holds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rowView;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.view_holds_list_item, null);
            TextView name = (TextView) rowView.findViewById(R.id.txt_view_hold_item_name);
            ImageButton arrowBtn = (ImageButton) rowView.findViewById(R.id.btn_view_hold_item_select);
            arrowBtn.setOnClickListener(new ClickHandler(position));

            Hold tempHld = getItem(position);
            name.setText(tempHld.getName());

            return rowView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class ClickHandler implements View.OnClickListener{

        int position;

        ClickHandler(int position){

            this.position = position;
        }

        @Override
        public void onClick(View v) {

            try {

                Hold tmpHld = new Hold();
                tmpHld = holdsList.get(position);

                Intent applicationDetailIntent = new Intent(context, HoldsDetails.class);
                applicationDetailIntent.putExtra("name", tmpHld.getName());
                applicationDetailIntent.putExtra("description", tmpHld.getDescription());
                startActivity(applicationDetailIntent);

            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }
}
