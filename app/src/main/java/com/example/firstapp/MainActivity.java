package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v){

        final EditText edtgmail = (EditText) findViewById(R.id.edtgmail);
        final EditText edtPass = (EditText) findViewById(R.id.edtPass);

        MyFunction obj = new MyFunction();
        obj.login(this,edtgmail,edtPass);
    }


}
