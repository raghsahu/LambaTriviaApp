package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WalletRedeemActivity extends AppCompatActivity {
    ImageView iv_back;
    LinearLayout ll_cash,ll_card_request;
    TextView tv_avail_naira,tv_withdraw_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_redeem);
        iv_back=findViewById(R.id.iv_back);
        ll_cash=findViewById(R.id.ll_cash);
        ll_card_request=findViewById(R.id.ll_card_request);
        tv_avail_naira=findViewById(R.id.tv_avail_naira);
        tv_withdraw_history=findViewById(R.id.tv_withdraw_history);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            String json_array = AppPreference.getJsondata(this);
            if (json_array != null) {
                try {
                    JSONArray jsoArray = new JSONArray(json_array);
                    for (int i = 0; i < jsoArray.length(); i++) {
                        JSONObject job = jsoArray.getJSONObject(i);
                        String total_naira = job.getString("total_naira");

                        tv_avail_naira.setText(total_naira);

                    }

                } catch (Exception e) {

                }
            }
        } catch (Exception e) {

        }

        ll_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WalletRedeemActivity.this, AddBankAccountActivity.class);
                startActivity(intent);
            }
        });

        ll_card_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WalletRedeemActivity.this, Card_request_Activity.class);
                startActivity(intent);

            }
        });

        tv_withdraw_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WalletRedeemActivity.this, CashWithdrawHistory_Activity.class);
                startActivity(intent);

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
