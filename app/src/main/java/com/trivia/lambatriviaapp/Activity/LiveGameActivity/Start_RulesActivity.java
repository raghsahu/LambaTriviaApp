package com.trivia.lambatriviaapp.Activity.LiveGameActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.ThreeSecondSplashActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Service.BackgroundSoundService;
import com.trivia.lambatriviaapp.Session.SessionManager;
import static com.trivia.lambatriviaapp.MainActivity.total_coin;

import java.util.Timer;
import java.util.TimerTask;

public class Start_RulesActivity extends AppCompatActivity {

    TextView tv_timer,tv_rule,tv_wallet_coin;
    public int seconds = 60;
    public int minutes = 0;
    BackgroundSoundService backgroundSoundService;
     MediaPlayer mediaPlayer;
     SessionManager sessionManager;
     ImageView iv_back;
    private Timer t;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__rules);
        sessionManager=new SessionManager(Start_RulesActivity.this);

        tv_timer=findViewById(R.id.tv_timer);
        tv_rule=findViewById(R.id.tv_league_rules);
        iv_back=findViewById(R.id.iv_back);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        backgroundSoundService=new BackgroundSoundService();

        mediaPlayer= MediaPlayer.create(Start_RulesActivity.this,R.raw.theme_song);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        try{
            tv_wallet_coin.setText(total_coin);

            if (getIntent()!=null){
                tv_rule.setText(getIntent().getStringExtra("live_rules"));
            }

        }catch (Exception e){

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //*******************************************
        StartRuleTimer();

//**********************************************************
    }

    private void StartRuleTimer() {

        try {

        t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                        seconds -= 1;

                        if(seconds == 0)
                        {
                            t.cancel();
                            mediaPlayer.stop();

//                            Intent intent=new Intent(Start_RulesActivity.this, LiveCompleteActivity.class);
//                            intent.putExtra("startgame", "liveGame");
//                            startActivity(intent);
//                            finish();

                            Intent intent=new Intent(Start_RulesActivity.this, LiveBannerVideoShow.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                });
            }

        }, 0, 1000);


        }catch (Exception e){

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            t.cancel();
            mediaPlayer.pause();
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            t.cancel();
            mediaPlayer.stop();
        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            t.cancel();
            mediaPlayer.stop();
        }catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            t.cancel();
            mediaPlayer.stop();
        }catch (Exception e){

        }

    }
}
