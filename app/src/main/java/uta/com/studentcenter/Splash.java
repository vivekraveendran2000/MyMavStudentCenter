package uta.com.studentcenter;

import android.app.Activity;
import android.content.Intent;
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent loginIntent = new Intent(Splash.this, login.class);
                startActivity(loginIntent);
                Splash.this.finish();
            }
        }, 3000);
    }
}
