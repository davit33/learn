package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";

    private String data = "[\n" +
            "    {\n" +
            "        \"id\":\"1\",\n" +
            "        \"product\":\"Coffee Milk\",\n" +
            "        \"img\": \"https://www.baristainstitute.com/sites/default/files/styles/some_share/public/images/Tazza_Ice_Latte.jpg?itok=nb39ZGmv\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"2\",\n" +
            "        \"product\":\"Latte\",\n" +
            "        \"img\": \"https://www.nespresso.com/ncp/res/resized/w1200_h630/uploads/recipes/nespresso-recipes-Iced-Latte.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\":\"3\",\n" +
            "        \"product\":\"Coffee\",\n" +
            "        \"img\": \"https://media-cdn.tripadvisor.com/media/photo-s/0e/96/7f/97/coffe-latte-100-arabica.jpg\"\n" +
            "    }\n" +
            "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadItem();
    }

    public  void btnHome(View v){
        MyFunction obj = new MyFunction();
        obj.btnlogin(this);
    }

    private void initRecycler(final JSONArray arrData){
        final RecyclerView recycler = findViewById(R.id.recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(new AdapterRecycler(this,arrData));
    }

    private void loadItem(){
        try {
            final JSONArray arrItem = new JSONArray(data);
            initRecycler(arrItem);
        }catch (Exception e){
            Log.e("error","error");
        }
    }



}