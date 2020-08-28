package com.example.firstapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class MyFunction {

    private static final MyFunction ourInstance = new MyFunction();

    public static MyFunction getInstance() {
        return ourInstance;
    }

    public void openActivity(Context context, Class act) {
        Intent intent = new Intent(context, act);
        context.startActivity(intent);
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public StringRequest requestDataApi(final int method, final String url, final HashMap<String, String> param, final ListenerVolley listenerVolley) {
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listenerVolley.response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listenerVolley.error(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }
        };
        return stringRequest;
    }

    //Check status progressbar
    public void showProgress(Context context, @IdRes final int id, final int status) {
        final ProgressBar progress = ((Activity) context).findViewById(id);
        progress.setVisibility(status);
    }

    //Check internet
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    /*show dialog*/
    public void showCustomDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final TextView txt = dialog.findViewById(R.id.content);
        txt.setText("Hello User");

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    /*save user login*/
    public boolean isHistory(final Context context) {
        return getText(context, Global.INFO_FILE).length() > 0;
    }

    public String getText(final Context context, String filename) {
        String text = "";
        try {
            File file = getFile(context, filename);
            if (file != null && file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                Reader reader = new InputStreamReader(fin, "UTF-8");
                BufferedReader br = new BufferedReader(reader);
                final StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                }
                br.close();
                fin.close();
                text = sb.toString();
                text = decryptJSONAES(text, generatePassword(context));
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return text;
    }

    /* decrypt android, ios, php */
    public String decryptJSONAES(String text, String key) {
        try {
            return AES256Cipher.AES_Decode(text, key);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return "";
    }

    public String generatePassword(Context context) {
        String password = "";
        for (int i = 0; i < 4; i++) {
            password += context.getPackageName().hashCode() + "";
        }
        return password.substring(0, 32);
    }

    private File getFile(final Context context, final String filename) {
        if (filename != null) {
            File file = new File(context.getFilesDir(), filename.hashCode() + "");
            return file;
        }
        return null;
    }

    public void saveText(final Context context, final String filename, final String text) {
        try {
            File file = getFile(context, filename);
            if (file != null) {
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                out.write(encryptJSONAES(text, generatePassword(context)));
                out.close();
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "-");
        }
    }

    /* encrypt android, ios, php */
    public String encryptJSONAES(String text, String key) {
        try {
            return AES256Cipher.AES_Encode(text, key);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return "";
    }

    public void finishActivity(final Context context) {
        try {
            ((Activity) context).finish();
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }
}
