package com.trivia.lambatriviaapp.Activity.League_Play_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.ThreeSecondSplashActivity;
import com.trivia.lambatriviaapp.Adapter.Show_Rewards_Adapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;
import static com.trivia.lambatriviaapp.MainActivity.tv_coinMain;

public class Predict_play_activity extends AppCompatActivity {

    TextView tv_rewards,tv_total_naira,tv_coin,tv_total_pay_coin,tv_team_vs,
            tv_league_rules,tv_time_left,tv_wallet_coin,tv_game_discrp,tv_total_pay_btn;
    //Button btn_play_now;
    LinearLayout btn_play_now;
    ImageView iv_back;
    LinearLayout ll_bg_image;
    RecyclerView recycler_rewards;
    ArrayList<LeagueReward>league_rewards=new ArrayList<>();
    Show_Rewards_Adapter show_rewards_adapter;
    public static String coin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_play_activity);
        sessionManager=new SessionManager(Predict_play_activity.this);

        tv_rewards=findViewById(R.id.tv_rewards);
        btn_play_now=findViewById(R.id.btn_play_now);
        iv_back=findViewById(R.id.iv_back);
        tv_total_naira=findViewById(R.id.tv_total_naira);
        tv_coin=findViewById(R.id.tv_coin);
        tv_total_pay_coin=findViewById(R.id.tv_total_pay_coin);
        tv_team_vs=findViewById(R.id.tv_team_vs);
        ll_bg_image=findViewById(R.id.ll_bg_image);
        tv_time_left=findViewById(R.id.tv_time_left);
        recycler_rewards=findViewById(R.id.recycler_rewards);
        tv_league_rules=findViewById(R.id.tv_league_rules);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        tv_game_discrp=findViewById(R.id.tv_game_discrp);
        tv_total_pay_btn=findViewById(R.id.tv_total_pay_btn);

        try{
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){
            Log.e("total_coin_error", e.toString());
        }

        if (getIntent()!=null){
            try {
            tv_coin.setText(getIntent().getStringExtra("league_coin"));
            tv_total_pay_coin.setText(getIntent().getStringExtra("league_coin"));
            tv_total_pay_btn.setText(getIntent().getStringExtra("league_coin"));
            tv_team_vs.setText(getIntent().getStringExtra("league_team"));
            tv_time_left.setText(getIntent().getStringExtra("league_left_time"));
            tv_league_rules.setText(getIntent().getStringExtra("league_rules"));
            tv_total_naira.setText(getIntent().getStringExtra("naira_prize"));
            league_rewards= (ArrayList<LeagueReward>) getIntent().getSerializableExtra("league_rewards");

            tv_game_discrp.setText(getIntent().getStringExtra("league_team")+" "+"start "+getIntent().getStringExtra("start_date_time"));

            String bg_image=getIntent().getStringExtra("league_image");

            Picasso.with(Predict_play_activity.this).load(Base_Url.league_game_image_path+getIntent().getStringExtra("league_image"))
                    //  .placeholder(R.drawable.league_place_holder_img)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            ll_bg_image.setBackground(new BitmapDrawable(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            }catch (Exception e){

            }
        }

        btn_play_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pay_coin_to_play();

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


        //****************set reward adapter****************

        try {
            if (league_rewards!=null){
                show_rewards_adapter= new Show_Rewards_Adapter( Predict_play_activity.this,league_rewards);
                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Predict_play_activity.this);
                recycler_rewards.setLayoutManager(mLayoutManger);
                recycler_rewards.setLayoutManager(new LinearLayoutManager(Predict_play_activity.this, RecyclerView.VERTICAL, false));
                recycler_rewards.setItemAnimator(new DefaultItemAnimator());
                recycler_rewards.setAdapter(show_rewards_adapter);

            }

        }catch (Exception e){
            Log.e("reward_error", e.toString());
        }

    }

    private void pay_coin_to_play() {

        final ProgressDialog progressDialog = new ProgressDialog(Predict_play_activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String url=Base_Url.BaseUrl+Base_Url.League_WalletCoin_Deduction;
        String user_id=AppPreference.getUser_Id(Predict_play_activity.this);
        Log.e("UserId", user_id);

        Log.e("pay_coin_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("league_quiz_id",sessionManager.getEventId())

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("pay_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");


                            if (result.equalsIgnoreCase("true")){
                                coin = jsonObject.getString("coin");

                                tv_wallet_coin.setText(coin);
                                tv_coinMain.setText(coin);
                                Intent intent=new Intent(Predict_play_activity.this, ThreeSecondSplashActivity.class);
                                intent.putExtra("startgame", "league");
                                startActivity(intent);
                                finish();

                            }else {
                                String message = jsonObject.getString("msg");
                                Toast.makeText(Predict_play_activity.this, "get more coins to join", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_pay_coin", String.valueOf(anError));
                        Log.e("error_pay_coin", String.valueOf(anError.getErrorCode()));

                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
