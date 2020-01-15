package com.trivia.lambatriviaapp.Activity.League_Play_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trivia.lambatriviaapp.Adapter.Show_MyCompleteQues_Adapter;
import com.trivia.lambatriviaapp.Adapter.Show_Practice_Adapter;
import com.trivia.lambatriviaapp.Adapter.Show_Rewards_Adapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Complete_Details;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Show_Practice_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class League_seemore_complete extends AppCompatActivity {

    boolean visibility_Flag1 = false;
    boolean visibility_Flag2 = false;
    boolean visibility_Flag3 = false;
    LinearLayout ll_crowd_details,ll_answer_details,ll_reward_click,ll_reward_details,
            ll_rules,ll_rules_click,ll_bg_image;
    ImageView iv_up_rule,iv_up_rew,iv_up_cro,iv_back;
    TextView tv_league_rules,tv_game_discrp,tv_time_left,tv_total_naira,tv_wallet_coin,tv_team_vs;
    String quiz_comp_id;
    Show_Rewards_Adapter show_rewards_adapter;
    Show_MyCompleteQues_Adapter show_myCompleteQues_adapter;
    RecyclerView recycler_reward,recycler_crowd;
    ArrayList<LeagueReward> league_rewards=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_seemore_complete);

        ll_crowd_details=findViewById(R.id.ll_crowd_details);
        ll_answer_details=findViewById(R.id.ll_answer_details);
        ll_reward_click=findViewById(R.id.ll_reward_click);
        ll_reward_details=findViewById(R.id.ll_reward_details);
        ll_rules=findViewById(R.id.ll_rules);
        ll_rules_click=findViewById(R.id.ll_rules_click);
        iv_up_rule=findViewById(R.id.iv_up_rule);
        iv_up_rew=findViewById(R.id.iv_up_rew);
        iv_up_cro=findViewById(R.id.iv_up_cro);
        iv_back=findViewById(R.id.iv_back);
        recycler_reward=findViewById(R.id.recycler_reward);
        tv_league_rules=findViewById(R.id.tv_league_rules);
        recycler_crowd=findViewById(R.id.recycler_crowd);
        tv_game_discrp=findViewById(R.id.tv_game_discrp);
        tv_time_left=findViewById(R.id.tv_time_left);
        tv_total_naira=findViewById(R.id.tv_total_naira);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        tv_team_vs=findViewById(R.id.tv_team_vs);
        ll_bg_image=findViewById(R.id.ll_bg_image);

        try {
            if (getIntent()!=null){
                quiz_comp_id=getIntent().getStringExtra("quiz_comp_id");
            }
        }catch (Exception e){

        }
        try {
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){

        }

        
        if (Connectivity.isConnected(League_seemore_complete.this)){
            ShowMyCompleteLeagueDetails();
            
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        ll_answer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag1){
                    ll_crowd_details.setVisibility(View.GONE);
                    iv_up_cro.setImageResource(R.drawable.arrow_down);
                    visibility_Flag1 = false;
                } else {
                    ll_crowd_details.setVisibility(View.VISIBLE);
                    iv_up_cro.setImageResource(R.drawable.arrow_up);
                    visibility_Flag1 =true;
                }


            }
        });
        ll_reward_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag2){
                    ll_reward_details.setVisibility(View.GONE);
                    iv_up_rew.setImageResource(R.drawable.arrow_down);
                    visibility_Flag2 = false;
                } else {
                    ll_reward_details.setVisibility(View.VISIBLE);
                    iv_up_rew.setImageResource(R.drawable.arrow_up);
                    visibility_Flag2 =true;
                }


            }
        });
        ll_rules_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag3){
                    ll_rules.setVisibility(View.GONE);
                    iv_up_rule.setImageResource(R.drawable.arrow_down);
                    visibility_Flag3 = false;
                } else {
                    ll_rules.setVisibility(View.VISIBLE);
                    iv_up_rule.setImageResource(R.drawable.arrow_up);
                    visibility_Flag3 =true;
                }
            }
        });


    }

    private void ShowMyCompleteLeagueDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(League_seemore_complete.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(League_seemore_complete.this);
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

        Api_Call service = retrofit.create(Api_Call.class);

        Call<Show_League_Complete_Details> call = service.get_completeDetails(user_id,quiz_comp_id);

        call.enqueue(new Callback<Show_League_Complete_Details>() {
            @Override
            public void onResponse(Call<Show_League_Complete_Details> call, Response<Show_League_Complete_Details> response) {

                try{

                    if (response!=null){
                        Log.e("league_compDetals" , ""+response.toString());
                         Log.e("league_compDetals_msg",""+response.body().getResult());
                         Log.e("league_compDetals_msg",""+response.body().getMsg());

                        ///***************show recycler_rewards item***************
                        if (response.body().getData().getRewards()!=null){
                            for (int i=0; i<response.body().getData().getRewards().size();i++){
                                String id=response.body().getData().getRewards().get(i).getId();
                                String name=response.body().getData().getRewards().get(i).getName();
                                String price=response.body().getData().getRewards().get(i).getPrice();
                                league_rewards.add(i,new LeagueReward(id,name,price));
                            }

                            show_rewards_adapter= new Show_Rewards_Adapter( League_seemore_complete.this,league_rewards);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_seemore_complete.this);
                            recycler_reward.setLayoutManager(mLayoutManger);
                            recycler_reward.setLayoutManager(new LinearLayoutManager(League_seemore_complete.this, RecyclerView.VERTICAL, false));
                            recycler_reward.setItemAnimator(new DefaultItemAnimator());
                            recycler_reward.setAdapter(show_rewards_adapter);

                        }
                        //**************set textview rules**********
                        if(response.body().getRules()!=null){
                            tv_league_rules.setText(response.body().getRules());
                        }
                        //*******************set crowdScore question adapter****
                        if (response.body().getData().getQuestions()!=null){
                            show_myCompleteQues_adapter= new Show_MyCompleteQues_Adapter( League_seemore_complete.this,response.body().getData().getQuestions());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_seemore_complete.this);
                            recycler_crowd.setLayoutManager(mLayoutManger);
                            recycler_crowd.setLayoutManager(new LinearLayoutManager(League_seemore_complete.this, RecyclerView.VERTICAL, false));
                            recycler_crowd.setItemAnimator(new DefaultItemAnimator());
                            recycler_crowd.setAdapter(show_myCompleteQues_adapter);

                        }

                        //************set some textview***
                        tv_team_vs.setText(response.body().getData().getGameTitle());
                        tv_total_naira.setText(response.body().getData().getNairaPrize());
                        tv_game_discrp.setText(response.body().getData().getDescription());
                        String img=response.body().getData().getImage();

                        Picasso.with(League_seemore_complete.this).load(Base_Url.league_game_image_path+img)
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


                    }
                }catch (Exception e){
                    Log.e("exc_comp_more", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Show_League_Complete_Details> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_practice",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
