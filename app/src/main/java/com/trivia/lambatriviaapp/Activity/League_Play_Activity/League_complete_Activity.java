package com.trivia.lambatriviaapp.Activity.League_Play_Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.lambatriviaapp.Adapter.Show_League_Adapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.BuildConfig;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class League_complete_Activity extends AppCompatActivity {

    ImageView iv_back;
    Button btn_share;
    RecyclerView recycler_league_next;
    Show_League_Adapter show_league_adapter;
    TextView tv_view_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league_complete_thank);


        iv_back=findViewById(R.id.iv_back);
        btn_share=findViewById(R.id.btn_share);
        recycler_league_next=findViewById(R.id.recycler_league_next);
        tv_view_more=findViewById(R.id.tv_view_more);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share_game();
            }
        });

        if (Connectivity.isConnected(League_complete_Activity.this)){
            get_League_game();

        }else {
            Toast.makeText(League_complete_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
        }


        tv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(League_complete_Activity.this, LeaguePlaySeeMore.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void get_League_game() {

        final ProgressDialog progressDialog = new ProgressDialog(League_complete_Activity.this,R.style.MyGravity);
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
        String user_id= AppPreference.getUser_Id(League_complete_Activity.this);
        Api_Call service = retrofit.create(Api_Call.class);

        Call<Show_League_Model> call = service.get_league(user_id);

        call.enqueue(new Callback<Show_League_Model>() {
            @Override
            public void onResponse(Call<Show_League_Model> call, Response<Show_League_Model> response) {

                try{

                    if (response!=null){
                        Log.e("league_msg",""+response.body().getResult());

                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            // Log.e("get_league_resp" , ""+response.body().getData().get(i).getStartDateTime());

                            if (response.body().getData().get(i).getLeague_questions()!=null){

                                //sqlitehelper.addPracticeQuistion((QuizDataCount) response.body().getData().get(i).getQuestions());
                            }

                        }
                        show_league_adapter= new Show_League_Adapter( League_complete_Activity.this,response.body().getData(),response.body().getRules());
                        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(League_complete_Activity.this);
                        recycler_league_next.setLayoutManager(mLayoutManger);
                        recycler_league_next.setLayoutManager(new LinearLayoutManager(League_complete_Activity.this, RecyclerView.HORIZONTAL, false));
                        recycler_league_next.setItemAnimator(new DefaultItemAnimator());
                        recycler_league_next.setAdapter(show_league_adapter);

                    }
                }catch (Exception e){
                    Log.e("excep_league", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Show_League_Model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_league",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void share_game() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Lamba");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
