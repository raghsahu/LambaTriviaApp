package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Adapter.MoneyToCoinRateAdapter;
import com.trivia.lambatriviaapp.Adapter.NewsBlogAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MoneyToCoinRate;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import static com.trivia.lambatriviaapp.Adapter.MoneyToCoinRateAdapter.radio_id;
import static com.trivia.lambatriviaapp.MainActivity.tv_coinMain;
import static com.trivia.lambatriviaapp.MainActivity.tv_naira;

public class WalletGetCoinsActivity extends AppCompatActivity implements MoneyToCoinRateAdapter.AdapterCallback {

    ImageView iv_back;
    TextView tv_avail_naira,tv_same_in_coin,tv_get_coins,tv_wallet_add;
    RecyclerView cash_to_coin_recycler;
    MoneyToCoinRateAdapter moneyToCoinRateAdapter;
    ArrayList<MoneyToCoinRate>moneyToCoinRatesModel=new ArrayList<>();
    String Radio_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_get_coins);

        iv_back = findViewById(R.id.iv_back);
        tv_avail_naira = findViewById(R.id.tv_avail_naira);
        tv_same_in_coin = findViewById(R.id.tv_same_in_coin);
        tv_get_coins = findViewById(R.id.tv_get_coins);
        tv_wallet_add = findViewById(R.id.tv_wallet_add);
        cash_to_coin_recycler = findViewById(R.id.cash_to_coin_recycler);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_wallet_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(WalletGetCoinsActivity.this, AddWalletActivity.class);
                startActivity(intent);

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


        if (Connectivity.isConnected(WalletGetCoinsActivity.this)){

            getMoneytoCoinConvertList();
        }




        tv_get_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Radio_id!=null){

                  //  Log.e("radio_id", ""+radio_id.toString());
                    if (Connectivity.isConnected(WalletGetCoinsActivity.this)){
                        Money_to_coin_Api(Radio_id);
                    }else {
                        Toast.makeText(WalletGetCoinsActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(WalletGetCoinsActivity.this, "Please select one", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void Money_to_coin_Api(String radio_id) {
        final ProgressDialog progressDialog = new ProgressDialog(WalletGetCoinsActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(WalletGetCoinsActivity.this);
        String url= Base_Url.BaseUrl+Base_Url.naira_to_coin;
        Log.e("money_to_coin_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("id",radio_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("money_to_convert_url", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")){

                                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                                String total_coin = jsonObject1.getString("total_coin");
                                String total_naira = jsonObject1.getString("total_naira");

                                tv_avail_naira.setText(total_naira);
                                tv_coinMain.setText(total_coin);
                                tv_naira.setText(total_naira);

                            Toast.makeText(WalletGetCoinsActivity.this, "Successfully added coins", Toast.LENGTH_SHORT).show();



                        }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });



    }

    private void getMoneytoCoinConvertList() {

        final ProgressDialog progressDialog = new ProgressDialog(WalletGetCoinsActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(WalletGetCoinsActivity.this);
        String url= Base_Url.BaseUrl+Base_Url.get_coin_rate;
        Log.e("get_money_to_coin_url"," "+url);
        AndroidNetworking.get(url)
                //.addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("money_to_coin_url", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String id = job.getString("id");
                                    String coin = job.getString("coin");
                                    String naira = job.getString("naira");

                                    moneyToCoinRatesModel.add(i,new MoneyToCoinRate(id,coin,naira) );
                                }

                                moneyToCoinRateAdapter= new MoneyToCoinRateAdapter(WalletGetCoinsActivity.this,moneyToCoinRatesModel);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(WalletGetCoinsActivity.this);
                                cash_to_coin_recycler.setLayoutManager(mLayoutManger);
                                cash_to_coin_recycler.setLayoutManager(new LinearLayoutManager(WalletGetCoinsActivity.this, RecyclerView.VERTICAL, false));
                                cash_to_coin_recycler.setItemAnimator(new DefaultItemAnimator());
                                cash_to_coin_recycler.setAdapter(moneyToCoinRateAdapter);

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });



    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        //radio_id=null;
        Intent intent=new Intent(WalletGetCoinsActivity.this,Wallet_transaction_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMethodCallback(String radio_id) {

        Radio_id=radio_id;
    }
}
