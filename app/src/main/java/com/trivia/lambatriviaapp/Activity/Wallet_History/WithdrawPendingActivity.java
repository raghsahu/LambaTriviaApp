package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;

public class WithdrawPendingActivity extends AppCompatActivity {

    TextView tv_withdraw_thanks,tv_thanks;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_pending);

        iv_back=findViewById(R.id.iv_back);
        tv_withdraw_thanks=findViewById(R.id.tv_withdraw_thanks);
        tv_thanks=findViewById(R.id.tv_thanks);

        try {
            if (getIntent()!=null){
                String thanks=getIntent().getStringExtra("msg");
                tv_withdraw_thanks.setText(thanks);
            }
        }catch (Exception e){

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        Intent intent=new Intent(WithdrawPendingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
