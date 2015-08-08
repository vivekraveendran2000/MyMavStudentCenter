package uta.com.Financial;

import android.app.Activity;
import android.content.Context;
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

import uta.com.Model.FinancialDue;
import uta.com.Model.ToDoList;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 8/8/2015.
 */
public class FinancialHome extends Activity implements View.OnClickListener{

    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<FinancialDue> financialDueList;
    FinancialDue tempFinancialDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financial_home);
        context = this;

        try {

            initData();
            initViews();

        }catch (Exception e){

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
            financialDueList = new ArrayList<FinancialDue>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempFinancialDue = new FinancialDue();
                if(mJsonObject.getString("name") != null) {
                    tempFinancialDue.setName(mJsonObject.getString("name"));
                }
                if(mJsonObject.getString("description") != null) {
                    tempFinancialDue.setDescription(mJsonObject.getString("description"));
                }
                if(mJsonObject.getString("due_date") != null) {
                    tempFinancialDue.setDueDate(mJsonObject.getString("due_date"));
                }

                if(mJsonObject.getString("amount") != null) {
                    tempFinancialDue.setAmount(mJsonObject.getString("amount"));
                }

                financialDueList.add(tempFinancialDue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_financial_home_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.financial_home_list);
        listView.setAdapter(new ListAdapter(financialDueList));
    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    public class ListAdapter extends BaseAdapter{

        private  ArrayList<FinancialDue> financialDues;

        ListAdapter(ArrayList<FinancialDue> financialDues) {
            this.financialDues = financialDues;
        }

        @Override
        public int getCount() {
            return financialDues.size();
        }

        @Override
        public FinancialDue getItem(int position) {
            return financialDues.get(position);
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

            rowView = inflater.inflate(R.layout.view_finanacial_list_item, null);
            TextView name = (TextView) rowView.findViewById(R.id.txt_financial_home_name);
            TextView description = (TextView) rowView.findViewById(R.id.txt_financial_home_description);
            TextView dueDate = (TextView) rowView.findViewById(R.id.txt_financial_home_due_date);
            TextView dueAmount = (TextView) rowView.findViewById(R.id.txt_financial_home_due_amount);

            FinancialDue tempFin = getItem(position);
            name.setText(tempFin.getName());
            description.setText(tempFin.getDescription());
            dueDate.setText(tempFin.getDueDate());
            dueAmount.setText(tempFin.getAmount());

            return rowView;
        }
    }
}
