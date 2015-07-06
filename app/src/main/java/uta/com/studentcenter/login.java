package uta.com.studentcenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import uta.com.academics.AcademicsHome;


public class login extends Activity implements View.OnClickListener{

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this );
    }

    @Override
    public void onClick(View v) {

        if(v.equals(loginBtn)){
            Intent currentStudentHomeIntent = new Intent(login.this, CurrentStudentHome.class);
            this.startActivity(currentStudentHomeIntent);
            finish();
        }
    }
}
