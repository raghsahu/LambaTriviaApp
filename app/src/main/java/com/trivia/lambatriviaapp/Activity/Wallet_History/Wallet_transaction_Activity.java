package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.lambatriviaapp.Adapter.Wallet_History_Adapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.Wallet_history_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Wallet_transaction_Activity extends AppCompatActivity {

    ImageView iv_back;
    LinearLayout ll_cash_history,ll_coin_history;
    RelativeLayout rel_coin,rel_cash;
    TextView tv_get_coins,tv_redeem,tv_avail_naira,tv_total_naira,tv_avail_coin,tv_total_coin;
    RecyclerView recycler_wallet;
    Wallet_History_Adapter wallet_history_adapter;
    //ArrayList<Wallet_history_Model>wallet_history_models=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transaction_);

        iv_back=findViewById(R.id.iv_back);
        ll_coin_history=findViewById(R.id.ll_coin_history);
        ll_cash_history=findViewById(R.id.ll_cash_history);
        rel_coin=findViewById(R.id.rel_coin);
        rel_cash=findViewById(R.id.rel_cash);
        tv_redeem=findViewById(R.id.tv_redeem);
        tv_get_coins=findViewById(R.id.tv_get_coins);
        tv_avail_naira=findViewById(R.id.tv_avail_naira);
        tv_total_naira=findViewById(R.id.tv_total_naira);
        tv_avail_coin=findViewById(R.id.tv_avail_coin);
        tv_total_coin=findViewById(R.id.tv_total_coin);
        recycler_wallet=findViewById(R.id.recycler_wallet);
       // recycler_wallet.setNestedScrollingEnabled(false);
       // ViewCompat.setNestedScrollingEnabled(recycler_wallet, false);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(Wallet_transaction_Activity.this)){
            getWalletCash_Retro("Coin");
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        ll_cash_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_coin.setVisibility(View.GONE);
                rel_cash.setVisibility(View.VISIBLE);
                ll_cash_history.setBackgroundResource(R.drawable.color_border);
                ll_coin_history.setBackgroundResource(R.color.colorPrimarybg);

                if (Connectivity.isConnected(Wallet_transaction_Activity.this)){
                     getWalletCash_Retro("Cash");
                }else {
                    Toast.makeText(Wallet_transaction_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ll_coin_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_coin.setVisibility(View.VISIBLE);
                rel_cash.setVisibility(View.GONE);
                ll_coin_history.setBackgroundResource(R.drawable.color_border);
                ll_cash_history.setBackgroundResource(R.color.colorPrimarybg);

                if (Connectivity.isConnected(Wallet_transaction_Activity.this)){
                    getWalletCash_Retro("Coin");
                }else {
                    Toast.makeText(Wallet_transaction_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_get_coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Wallet_transaction_Activity.this,WalletGetCoinsActivity.class);
                startActivity(intent);

            }
        });

        tv_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Wallet_transaction_Activity.this,WalletRedeemActivity.class);
                startActivity(intent);

            }
        });

    }

    //************get coin transaction history*****
    //************get cash transaction history*****
    private void getWalletCash_Retro(final String Cash_or_Coin) {
        final ProgressDialog progressDialog = new ProgressDialog(Wallet_transaction_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url.BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        String user_id= AppPreference.getUser_Id(Wallet_transaction_Activity.this);
        Api_Call service = retrofit.create(Api_Call.class);

        Call<Wallet_history_Model> call;
        if (Cash_or_Coin.equalsIgnoreCase("Cash")){
             call = service.get_wallet_history(user_id);
        }else {
            call = service.get_wallet_history_coin(user_id);
        }

        call.enqueue(new Callback<Wallet_history_Model>() {
            @Override
            public void onResponse(Call<Wallet_history_Model> call, Response<Wallet_history_Model> response) {
                try{

                    if (response!=null){
                        Log.e("wallet_msg",""+response.body().getResult());

                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            if (response.body().getData().get(i).getDateFormatted()!=null){

                            }
                        }
                    if (response.body().getResult().equalsIgnoreCase("true")){

                            wallet_history_adapter= new Wallet_History_Adapter( Wallet_transaction_Activity.this,response.body().getData(),Cash_or_Coin);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Wallet_transaction_Activity.this);
                            recycler_wallet.setLayoutManager(mLayoutManger);
                            recycler_wallet.setLayoutManager(new LinearLayoutManager(Wallet_transaction_Activity.this, RecyclerView.VERTICAL, false));
                            recycler_wallet.setFocusable(false);
                            recycler_wallet.setAdapter(wallet_history_adapter);

                        //*************set text data***
                        if (Cash_or_Coin.equalsIgnoreCase("Cash")){
                            tv_avail_naira.setText(response.body().getCurrently());
                            tv_total_naira.setText(response.body().getTotalWinning());
                        }else {
                          tv_avail_coin.setText(response.body().getCurrently());
                          tv_total_coin.setText(response.body().getTotalWinning().toString());
                        }

                    }

                }

                }catch (Exception e){
                    Log.e("excep_wallet", e.getMessage());


                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Wallet_history_Model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_league",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(Wallet_transaction_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
