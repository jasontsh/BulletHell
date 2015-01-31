package com.pennappspring15.bullethole.bullethole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndActivity extends Activity {
    private String TAG = "EndActivity";
    final Activity a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.end_page);
        Button b = (Button) findViewById(R.id.end_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.startActivity(new Intent(a, MainActivity.class));
                finish();
            }
        });
    }

}
