package uta.com.Application;

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

import uta.com.Model.Application;
import uta.com.Model.Course;
import uta.com.search.SearchSubjectDetails;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/17/2015.
 */
public class ApplicationResult extends Activity  implements  View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<Application> applicationList;
    Application tempApplication;
    JSONObject applicationJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list);

        try {
            initData();
            initViews();
            context = this;


        }catch (Exception e){
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
            applicationList = new ArrayList<Application>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempApplication = new Application();
                if(mJsonObject.getString("application_name") != null) {
                    tempApplication.setName(mJsonObject.getString("application_name"));
                }
                if(mJsonObject.getString("application_number") != null) {
                    tempApplication.setNumber(mJsonObject.getString("application_number"));
                }
                if(mJsonObject.getString("application_status") != null) {
                    tempApplication.setStatus(mJsonObject.getString("application_status"));
                }
                if(mJsonObject.getString("application_date") != null) {
                    tempApplication.setDate(mJsonObject.getString("application_date"));
                }
                applicationList.add(tempApplication);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_application_result_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.application_result_list);
        listView.setAdapter(new ListAdapter(applicationList));
    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    public class ListAdapter extends BaseAdapter implements  View.OnClickListener{

        private  ArrayList<Application> applications;

        ListAdapter(ArrayList<Application> applications) {
            this.applications = applications;
        }

        @Override
        public int getCount() {
            return applications.size();
        }

        @Override
        public Application getItem(int position) {
            return applications.get(position);
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

            rowView = inflater.inflate(R.layout.application_list_item, null);
            TextView name = (TextView) rowView.findViewById(R.id.txt_application_list_item_name);
            TextView number = (TextView) rowView.findViewById(R.id.txt_application_list_item_number);
            ImageButton arrowBtn = (ImageButton) rowView.findViewById(R.id.btn_application_list_item_select);
            arrowBtn.setOnClickListener(new ClickHandler(position));

            Application tempAppl = getItem(position);
            name.setText(tempAppl.getName());
            number.setText(tempAppl.getNumber());

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


        }
    }
}
