package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("Err","Testing");
            }
        };

        IntentFilter filter = new IntentFilter("msg");
        registerReceiver(broadcastReceiver,filter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver), new IntentFilter("fcm"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Err", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast

                        Log.e("Token", token);
                    }
                });

    }

    public void login(View v) {

        final EditText edtGmail = (EditText) findViewById(R.id.edtgmail);
        final EditText edtPass = (EditText) findViewById(R.id.edtPass);
        final MyFunction obj = new MyFunction();
        if (obj.isNetworkConnected(this)) {
            loadMain(edtGmail.getText().toString(), edtPass.getText().toString());
        } else
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
    }

    private void loadMain(final String username, final String password) {
        final MyFunction obj = new MyFunction();
        final HashMap<String, String> param = new HashMap<>();
        param.put("user", username);
        param.put("pwd", password);
        obj.showProgress(this, R.id.progress, View.VISIBLE);
        requestQueue.add(obj.requestDataApi(Request.Method.POST, "https://semsonghyml.000webhostapp.com/services/login_post.php", param, new ListenerVolley() {
            @Override
            public void response(String res) {
                try {
                    Log.e("Response", res);
                    if (obj.isJSONValid(res)) {
                        obj.openActivity(MainActivity.this, HomeActivity.class);
                        Signton.getInstance().setA(10);
                    } else {
                        final int code = Integer.parseInt(res);
                        Toast.makeText(MainActivity.this, "Incorrect Username Or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                obj.showProgress(MainActivity.this, R.id.progress, View.GONE);
            }

            @Override
            public void error(VolleyError err) {
                obj.showProgress(MainActivity.this, R.id.progress, View.GONE);
                Log.e("Err", err.getMessage() + "");
            }
        }));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyFunction obj = new MyFunction();
            obj.showCustomDialog(MainActivity.this);
        }
    };
}