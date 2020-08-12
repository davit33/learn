package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initRecycler();
    }

    public  void btnHome(View v){
        MyFunction obj = new MyFunction();
        obj.btnlogin(this);
    }

    private void initRecycler(){
        final RecyclerView recycler = findViewById(R.id.recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(new AdapterRecycler(this,null));
    }



}