package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        requestQueue = Volley.newRequestQueue(this);
        loadItem();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver), new IntentFilter("fcm"));
    }

    private void initRecycler(final JSONArray arrData) {
        final RecyclerView recycler = findViewById(R.id.recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(new AdapterRecycler(this, arrData));
    }

    private void loadItem() {
        final MyFunction obj = new MyFunction();
        obj.showProgress(this, R.id.progress, View.VISIBLE);
        requestQueue.add(obj.requestDataApi(Request.Method.GET, "https://api.mocki.io/v1/affa9a12", null, new ListenerVolley() {
            @Override
            public void response(String res) {
                try {
                    final JSONArray arrItem = new JSONArray(res);
                    initRecycler(arrItem);
                } catch (Exception e) {
                    Log.e("Error", "error");
                }
                obj.showProgress(HomeActivity.this, R.id.progress, View.GONE);
            }

            @Override
            public void error(VolleyError err) {
                obj.showProgress(HomeActivity.this, R.id.progress, View.GONE);
                Log.e("Error", err.getMessage() + "");
            }

        }));
    }

    public void btnMap(View v) {
        MyFunction obj = new MyFunction();
        obj.openActivity(HomeActivity.this, Maps.class);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyFunction obj = new MyFunction();
            obj.showCustomDialog(HomeActivity.this);
        }
    };


}