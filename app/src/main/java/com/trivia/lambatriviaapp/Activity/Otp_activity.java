package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;

public class Otp_activity extends AppCompatActivity {

   Button tv_verify_nmbr;
   EditText et_otp;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);

        tv_verify_nmbr=findViewById(R.id.tv_verify_nmbr);
        et_otp=findViewById(R.id.et_otp);

        if (getIntent()!=null){
            otp=getIntent().getStringExtra("otp");
            et_otp.setText(otp);
        }

        tv_verify_nmbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Et_Otp=et_otp.getText().toString();
                if (Et_Otp.equalsIgnoreCase(otp)){
                    Toast.makeText(Otp_activity.this, "Otp match successfully", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Otp_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Otp_activity.this, "Wrong otp", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
