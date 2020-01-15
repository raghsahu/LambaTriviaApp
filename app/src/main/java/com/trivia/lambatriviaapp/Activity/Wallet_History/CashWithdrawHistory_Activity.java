package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Adapter.CardShowAdapter;
import com.trivia.lambatriviaapp.Adapter.WithdrawHistoryAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MyCardList;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.WithdrawHistoryModel;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.add_card_detail;
import static com.trivia.lambatriviaapp.All_Url.Base_Url.get_money_withdraw_request;

public class CashWithdrawHistory_Activity extends AppCompatActivity {
    ImageView iv_back;
    RecyclerView recycler_his;
    WithdrawHistoryAdapter withdrawHistoryAdapter;
    ArrayList<WithdrawHistoryModel> withdrawHistoryModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdraw_history_);

        iv_back=findViewById(R.id.iv_back);
        recycler_his=findViewById(R.id.recycler_his);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(CashWithdrawHistory_Activity.this)){
            getMy_History();

        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getMy_History() {

        final ProgressDialog progressDialog = new ProgressDialog(CashWithdrawHistory_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

      //  Log.e("get__url"," "+ Base_Url.BaseUrl+get_money_withdraw_request);
        AndroidNetworking.post(Base_Url.BaseUrl+get_money_withdraw_request)
                .addBodyParameter("user_id", AppPreference.getUser_Id(CashWithdrawHistory_Activity.this))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_card_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                         //   Toast.makeText(Card_request_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for(int i=0; i<jsonArray.length();i++){

                                    JSONObject jsonObject1= jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String user_id = jsonObject1.getString("user_id");
                                    String payment_type = jsonObject1.getString("payment_type");
                                    String payment_method_id = jsonObject1.getString("payment_method_id");
                                    String status = jsonObject1.getString("status");
                                    String amount = jsonObject1.getString("amount");
                                    String date = jsonObject1.getString("date");
                                    String phone_numer = jsonObject1.getString("phone_numer");

                                    withdrawHistoryModels.add(i, new WithdrawHistoryModel(id,user_id,payment_type,
                                            payment_method_id,status,amount,date,phone_numer));

                                }


                                withdrawHistoryAdapter= new WithdrawHistoryAdapter( CashWithdrawHistory_Activity.this,withdrawHistoryModels);
                                recycler_his.setLayoutManager(new LinearLayoutManager(CashWithdrawHistory_Activity.this, RecyclerView.VERTICAL, false));
                                recycler_his.setAdapter(withdrawHistoryAdapter);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_prof_show", String.valueOf(anError));

                    }
                });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
