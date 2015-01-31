package com.pennappspring15.bullethole.bullethole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends Activity {

    private String TAG = "FirstActivity";
    final Activity a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.first_page);
        Button b = (Button) findViewById(R.id.first_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.startActivity(new Intent(a, MainActivity.class));
                finish();
            }
        });
    }
}
