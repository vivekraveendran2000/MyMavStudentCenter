package uta.com.studentcenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by vivekraveendran on 6/28/2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        SharedPreferences prefs = this.getSharedPreferences(
                "studentcenter", Context.MODE_PRIVATE);
        String netId = prefs.getString("net_id", "");
        String studentStatus = prefs.getString("student_status","");

        if (netId.equals("")){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent loginIntent = new Intent(Splash.this, login.class);
                    startActivity(loginIntent);
                    Splash.this.finish();
                }
            }, 2500);

        }else {

            if (studentStatus.equals("current")) {

                Intent currentStudentHomeIntent = new Intent(Splash.this, CurrentStudentHome.class);
                startActivity(currentStudentHomeIntent);
                finish();
            } else {

                Intent prospectiveHomeIntent = new Intent(Splash.this, ProspectiveHome.class);
                startActivity(prospectiveHomeIntent);
                finish();
            }
        }
    }
}
