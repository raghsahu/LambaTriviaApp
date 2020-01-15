package com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Play_league_Activity;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.LiveCompleteActivity;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.Play_Live_Activity;
import com.trivia.lambatriviaapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class ThreeSecondSplashActivity extends AppCompatActivity {

    TextView timer_count;
    Animation animZoomIn;
    public int seconds = 3;
    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_second_splash);

        timer_count = findViewById(R.id.text);

        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);

//        timer_count.setVisibility(View.VISIBLE);
//        timer_count.startAnimation(animZoomIn);



        //*******************************************

         t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            boolean running = false;
            @Override
            public void run() {
                running = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer_count.setText(""+(seconds));
                        timer_count.setVisibility(View.VISIBLE);
                        timer_count.startAnimation(animZoomIn);

                        seconds -= 1;

                        if(seconds == -1)
                        {
                            stopRunning();

                        }

                    }

                    private void stopRunning() {
                            running = false;
                            t.cancel();
                            Thread.currentThread().interrupt();

                            if (getIntent()!=null){
                                String gameintent=getIntent().getStringExtra("startgame");
                                if (gameintent.equalsIgnoreCase("practice")){
                                    Intent intent=new Intent(ThreeSecondSplashActivity.this, Practice_QuizStart_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (gameintent.equalsIgnoreCase("league")){
                                    Intent intent1=new Intent(ThreeSecondSplashActivity.this, Play_league_Activity.class);
                                    startActivity(intent1);
                                    finish();
                                }

                                else if (gameintent.equalsIgnoreCase("liveGame")){
                                    Intent intent1=new Intent(ThreeSecondSplashActivity.this, Play_Live_Activity.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }



                    }


                });

            }


        }, 0, 1000);
//*****************************************************

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            t.cancel();
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            t.cancel();
        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            t.cancel();
        }catch (Exception e){

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            t.cancel();
        }catch (Exception e){

        }
    }
}
