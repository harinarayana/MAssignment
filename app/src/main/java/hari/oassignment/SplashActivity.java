package hari.oassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread background = new Thread() {
            public void run(){
                try{
                    sleep(5000);
                    Intent intent = new Intent(getApplicationContext(), SelectionActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            };
        };
            background.start();

    }
}
