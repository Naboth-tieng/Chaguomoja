package com.example.vote_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvtext=findViewById(R.id.tvscreen);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String ID = extras.getString("ID");
            tvtext.setText(ID);
        }
    }
}