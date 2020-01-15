package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class Forget_password extends AppCompatActivity {

    EditText et_email;
    Button tv_forget_pw;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        tv_forget_pw=findViewById(R.id.tv_forget_pw);
        et_email=findViewById(R.id.et_email);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //**************
        tv_forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Et_email=et_email.getText().toString();

                if (!Et_email.isEmpty()){
                    if (Connectivity.isConnected(Forget_password.this)){
                        String url= Base_Url.BaseUrl+Base_Url.forgotpassword;
                        Forget_Pw(url,Et_email);

                    }else {
                        Toast.makeText(Forget_password.this, "Please check internet", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (Et_email.isEmpty()){
                        et_email.setError("Please enter Email id");
                    }
                }

            }
        });

    }

    private void Forget_Pw(String url, String et_email) {

        final ProgressDialog progressDialog = new ProgressDialog(Forget_password.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

            Log.e("show_email_url"," "+url);
            AndroidNetworking.post(url)
                    .addBodyParameter("email",et_email)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            progressDialog.dismiss();
                            Log.e("forget_response = ", jsonObject.toString());

                            try {
                                progressDialog.dismiss();
                                String message = jsonObject.getString("msg");
                                String result = jsonObject.getString("result");

                                Toast.makeText(Forget_password.this, "Password send your Email!", Toast.LENGTH_SHORT).show();

//                                if (message.equalsIgnoreCase("successfull")){
//                                    JSONObject jsonObject1=jsonObject.getJSONObject("result");
//
//                                    String user_id = jsonObject1.getString("id");
//                                    String username = jsonObject1.getString("username");
//
//                                    Intent intent=new Intent(Forget_password.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.dismiss();
                            Log.e("error_menu_show", String.valueOf(anError));

                        }
                    });


        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
