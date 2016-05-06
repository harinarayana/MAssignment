package hari.oassignment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView title = (TextView) findViewById(R.id.splash_screen_title);
        animateRow(title);
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
    /**
     * Method to animate view
     * @param viewToBeAnimated - view that needs to be animated
     * **/
    private void animateRow(View viewToBeAnimated) {
        Animation animation = AnimationUtils.loadAnimation(this,  R.anim.up_from_bottom);
        viewToBeAnimated.startAnimation(animation);
    }
}
