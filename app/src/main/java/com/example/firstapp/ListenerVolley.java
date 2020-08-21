package com.example.firstapp;

import com.android.volley.VolleyError;

public interface ListenerVolley {
    void response(String res);
    void error(VolleyError err);
}
