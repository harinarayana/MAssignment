package hari.oassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        findViewById(R.id.list_view_button).setOnClickListener(this);
        findViewById(R.id.recycler_view_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recycler_view_button:
                startActivity(new Intent(this, OAssignmentActivity.class));
                break;
            case R.id.list_view_button:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }
}
