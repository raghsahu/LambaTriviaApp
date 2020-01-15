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
import com.trivia.lambatriviaapp.Adapter.ResultLeague_Details_Adapter;
import com.trivia.lambatriviaapp.Adapter.ResultLeague_Winner_Adapter;
import com.trivia.lambatriviaapp.Adapter.Show_MyCompleteQues_Adapter;
import com.trivia.lambatriviaapp.Adapter.Show_Rewards_Adapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Complete_Details;
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

public class League_seemore_Result extends AppCompatActivity {

    boolean visibility_Flag1 = false;
    boolean visibility_Flag2 = false;
    boolean visibility_Flag3 = false;
    boolean visibility_Flag4 = false;

    LinearLayout ll_win_click,ll_win_details,ll_reward_click,ll_reward_details,
            ll_rules,ll_rules_click,ll_answer_click,ll_ans_details,ll_bg_image;
    ImageView iv_up_rule,iv_up_rew,iv_up_ans,iv_back,iv_up_win,iv_profile;
    TextView tv_wallet_coin,tv_total_naira,tv_team_vs,tv_time_left,tv_game_discrp,tv_league_rules,
    tv_my_win_coin,tv_my_win_cash,tv_user_id,tv_my_rank;
    String quiz_comp_id;
    RecyclerView recycler_ans,recycler_reward,recycler_winners;
    ArrayList<LeagueReward> league_rewards=new ArrayList<>();
    Show_Rewards_Adapter show_rewards_adapter;
    ResultLeague_Details_Adapter show_myResultLeague_adapter;
    ResultLeague_Winner_Adapter resultLeagueWinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_seemore__result);

        ll_win_click=findViewById(R.id.ll_win_click);
        ll_win_details=findViewById(R.id.ll_win_details);
        ll_reward_click=findViewById(R.id.ll_reward_click);
        ll_reward_details=findViewById(R.id.ll_reward_details);
        ll_rules=findViewById(R.id.ll_rules);
        ll_rules_click=findViewById(R.id.ll_rules_click);
        ll_answer_click=findViewById(R.id.ll_answer_click);
        ll_ans_details=findViewById(R.id.ll_ans_details);
        iv_up_rule=findViewById(R.id.iv_up_rule);
        iv_up_rew=findViewById(R.id.iv_up_rew);
        iv_up_ans=findViewById(R.id.iv_up_ans);
        iv_up_win=findViewById(R.id.iv_up_win);
        iv_back=findViewById(R.id.iv_back);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        tv_total_naira=findViewById(R.id.tv_total_naira);
        tv_team_vs=findViewById(R.id.tv_team_vs);
        tv_time_left=findViewById(R.id.tv_time_left);
        tv_game_discrp=findViewById(R.id.tv_game_discrp);
        recycler_ans=findViewById(R.id.recycler_ans);
        recycler_reward=findViewById(R.id.recycler_reward);
        tv_league_rules=findViewById(R.id.tv_league_rules);
        ll_bg_image=findViewById(R.id.ll_bg_image);
        tv_my_win_coin=findViewById(R.id.tv_my_win_coin);
        tv_my_win_cash=findViewById(R.id.tv_my_win_cash);
        recycler_winners=findViewById(R.id.recycler_winners);
        tv_user_id=findViewById(R.id.tv_user_id);
        tv_my_rank=findViewById(R.id.tv_my_rank);
        iv_profile=findViewById(R.id.iv_profile);


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

        //**********************api call****************
        if (Connectivity.isConnected(League_seemore_Result.this)){
            ShowMyResultLeagueDetails();

        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        //*******************onclick*****************
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        ll_win_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag1){
                    ll_win_details.setVisibility(View.GONE);
                    iv_up_win.setImageResource(R.drawable.arrow_down);
                    visibility_Flag1 = false;
                } else {
                    ll_win_details.setVisibility(View.VISIBLE);
                    iv_up_win.setImageResource(R.drawable.arrow_up);
                    visibility_Flag1 =true;
                }


            }
        });
        ll_reward_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag3){
                    ll_reward_details.setVisibility(View.GONE);
                    iv_up_rew.setImageResource(R.drawable.arrow_down);
                    visibility_Flag3 = false;
                } else {
                    ll_reward_details.setVisibility(View.VISIBLE);
                    iv_up_rew.setImageResource(R.drawable.arrow_up);
                    visibility_Flag3 =true;
                }


            }
        });
        ll_rules_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag4){
                    ll_rules.setVisibility(View.GONE);
                    iv_up_rule.setImageResource(R.drawable.arrow_down);
                    visibility_Flag4 = false;
                } else {
                    ll_rules.setVisibility(View.VISIBLE);
                    iv_up_rule.setImageResource(R.drawable.arrow_up);
                    visibility_Flag4 =true;
                }
            }
        });

        ll_answer_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visibility_Flag2){
                    ll_ans_details.setVisibility(View.GONE);
                    iv_up_ans.setImageResource(R.drawable.arrow_down);
                    visibility_Flag2 = false;
                } else {
                    ll_ans_details.setVisibility(View.VISIBLE);
                    iv_up_ans.setImageResource(R.drawable.arrow_up);
                    visibility_Flag2 =true;
                }
            }
        });


    }

    private void ShowMyResultLeagueDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(League_seemore_Result.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(League_seemore_Result.this);
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

        Call<Show_League_Complete_Details> call = service.get_ResultDetails(user_id,quiz_comp_id);

        call.enqueue(new Callback<Show_League_Complete_Details>() {
            @Override
            public void onResponse(Call<Show_League_Complete_Details> call, Response<Show_League_Complete_Details> response) {

                try{

                    if (response!=null){
                        progressDialog.dismiss();

                        Log.e("league_resDetals" , ""+response.toString());
                        Log.e("league_ResDetals_msg",""+response.body().getResult());
                        Log.e("league_ResDetals_msg",""+response.body().getMsg());

                        ///***************show recycler_rewards item***************
                        if (response.body().getData().getRewards()!=null){
                            for (int i=0; i<response.body().getData().getRewards().size();i++){
                                String id=response.body().getData().getRewards().get(i).getId();
                                String name=response.body().getData().getRewards().get(i).getName();
                                String price=response.body().getData().getRewards().get(i).getPrice();
                                league_rewards.add(i,new LeagueReward(id,name,price));
                            }

                            show_rewards_adapter= new Show_Rewards_Adapter( League_seemore_Result.this,league_rewards);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_seemore_Result.this);
                            recycler_reward.setLayoutManager(mLayoutManger);
                            recycler_reward.setLayoutManager(new LinearLayoutManager(League_seemore_Result.this, RecyclerView.VERTICAL, false));
                            recycler_reward.setItemAnimator(new DefaultItemAnimator());
                            recycler_reward.setAdapter(show_rewards_adapter);

                        }
                        //**************set textview rules**********
                        if(response.body().getRules()!=null){
                            tv_league_rules.setText(response.body().getRules());
                        }
                        //*******************set Resultcore question adapter****
                        if (response.body().getData().getQuestions()!=null){
                            show_myResultLeague_adapter= new ResultLeague_Details_Adapter( League_seemore_Result.this,response.body().getData().getQuestions());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_seemore_Result.this);
                            recycler_ans.setLayoutManager(mLayoutManger);
                            recycler_ans.setLayoutManager(new LinearLayoutManager(League_seemore_Result.this, RecyclerView.VERTICAL, false));
                            recycler_ans.setItemAnimator(new DefaultItemAnimator());
                            recycler_ans.setAdapter(show_myResultLeague_adapter);

                        }
                        if (response.body().getData().getWinners()!=null){
                            resultLeagueWinnerAdapter= new ResultLeague_Winner_Adapter( League_seemore_Result.this,response.body().getData().getWinners());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_seemore_Result.this);
                            recycler_winners.setLayoutManager(mLayoutManger);
                            recycler_winners.setLayoutManager(new LinearLayoutManager(League_seemore_Result.this, RecyclerView.VERTICAL, false));
                            recycler_winners.setItemAnimator(new DefaultItemAnimator());
                            recycler_winners.setAdapter(resultLeagueWinnerAdapter);

                        }

                        //************set some textview***
                        tv_team_vs.setText(response.body().getData().getGameTitle());
                        tv_total_naira.setText(response.body().getData().getNairaPrize());
                        tv_game_discrp.setText(response.body().getData().getDescription());
                        String img=response.body().getData().getImage();

                        //************load background image***
                        Picasso.with(League_seemore_Result.this).load(Base_Url.league_game_image_path+img)
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

                        //**************set my rank coin and cash***
                        if (response.body().getData().getMyRanks()!=null){
                            tv_my_rank.setText(response.body().getData().getMyRanks().getMyRank());
                            tv_user_id.setText(response.body().getData().getMyRanks().getUsername());
                            tv_my_win_cash.setText(response.body().getData().getMyRanks().getMyAmount());
                            tv_my_win_coin.setText(response.body().getData().getMyRanks().getMyTotalCoin());

                            //*****set my profile image***
                            if (!response.body().getData().getMyRanks().getImage().isEmpty()){
                                Picasso.with(League_seemore_Result.this)
                                        .load(Base_Url.MenuImageUrl+response.body().getData().getMyRanks().getImage())
                                        .placeholder(R.drawable.bear)
                                        .into(iv_profile);
                            }
                        }



                    }
                }catch (Exception e){
                    Log.e("exc_result_more", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Show_League_Complete_Details> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_league_result",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
