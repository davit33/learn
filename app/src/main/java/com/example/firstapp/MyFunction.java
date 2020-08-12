package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

public class MyFunction {

   public void login (Context context, EditText edtgmail,EditText edtPass){

      if(edtgmail.getText().toString().equals("admin") &&
              edtPass.getText().toString().equals("123456") && edtPass.length() != 0){
         if(edtPass.length()<6){
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
         }else{
            Intent intent = new Intent(context,HomeActivity.class);
            context.startActivity(intent);
         }
      }else{
         Toast.makeText(context, "Login Unsuccess", Toast.LENGTH_SHORT).show();
      }

   }

   public void btnlogin(Context context){
      Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
   }
}
