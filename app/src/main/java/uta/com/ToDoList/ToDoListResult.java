package uta.com.ToDoList;

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
import uta.com.Model.ToDoList;
import uta.com.studentcenter.R;

/**
 * Created by vivekraveendran on 7/17/2015.
 */
public class ToDoListResult extends Activity implements View.OnClickListener{


    ImageButton backBtn;
    Context context;
    ListView listView;
    ArrayList<ToDoList> toDoList;
    ToDoList tempToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.todo_list);
        context = this;
        initData();
        initViews();
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
            toDoList = new ArrayList<ToDoList>(mJsonArray.length());
            JSONObject mJsonObject = new JSONObject();
            for (int i = 0; i < mJsonArray.length(); i++) {

                mJsonObject = mJsonArray.getJSONObject(i);
                tempToDo = new ToDoList();
                if(mJsonObject.getString("todo_no") != null) {
                    tempToDo.setNumber(mJsonObject.getString("todo_no"));
                }
                if(mJsonObject.getString("due_date") != null) {
                    tempToDo.setDate(mJsonObject.getString("due_date"));
                }
                if(mJsonObject.getString("description") != null) {
                    tempToDo.setDescription(mJsonObject.getString("description"));
                }

                if(mJsonObject.getString("name") != null) {
                    tempToDo.setName(mJsonObject.getString("name"));
                }

                toDoList.add(tempToDo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initViews(){

        backBtn = (ImageButton) findViewById(R.id.btn_todo_result_back);
        backBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.todo_result_list);
        listView.setAdapter(new ListAdapter(toDoList));
    }

    @Override
    public void onClick(View v) {

        if(v.equals(backBtn)){
            finish();
        }
    }

    public class ListAdapter extends BaseAdapter implements  View.OnClickListener{

        private  ArrayList<ToDoList> toDoList;

        ListAdapter(ArrayList<ToDoList> toDoList) {
            this.toDoList = toDoList;
        }

        @Override
        public int getCount() {
            return toDoList.size();
        }

        @Override
        public ToDoList getItem(int position) {
            return toDoList.get(position);
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

            rowView = inflater.inflate(R.layout.todo_list_item, null);
            TextView number = (TextView) rowView.findViewById(R.id.txt_todo_list_item_number);
            ImageButton arrowBtn = (ImageButton) rowView.findViewById(R.id.btn_todo_list_item_select);
            arrowBtn.setOnClickListener(new ClickHandler(position));

            ToDoList tempToDo = getItem(position);
            number.setText(tempToDo.getName());

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

                ToDoList toDoTemp = new ToDoList();
                toDoTemp = toDoList.get(position);

                Intent toDoListDetailIntent = new Intent(context, ToDoListDetail.class);
                toDoListDetailIntent.putExtra("name", toDoTemp.getName());
                toDoListDetailIntent.putExtra("number", toDoTemp.getNumber());
                toDoListDetailIntent.putExtra("description", toDoTemp.getDescription());
                toDoListDetailIntent.putExtra("date", toDoTemp.getDate());
                startActivity(toDoListDetailIntent);

            }catch (Exception e){

                e.printStackTrace();
            }
        }
    }
}
