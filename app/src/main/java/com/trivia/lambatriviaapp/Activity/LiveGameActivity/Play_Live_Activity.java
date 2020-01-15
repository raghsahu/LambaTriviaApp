package com.trivia.lambatriviaapp.Activity.LiveGameActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_QuizStart_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Service.ImprovedSSLSocketFactory;
import com.trivia.lambatriviaapp.Service.NoSSLv3SocketFactory;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class Play_Live_Activity extends AppCompatActivity {

    final Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    SessionManager sessionManager;
    ImageView iv_back;
    TextView tv_set_question,tv_ans1,tv_ans2,tv_ans3,tv_setcoin,
            tv_live_player,tv_wallet_coin,tv_ques_no,tv_timer;
    RelativeLayout relative_progress_bar,relative_timesup,relative_wrong,relative_correct;
    private String quiz_ques_id;
    ProgressBar progressBarView;
    int endTime;
    CountDownTimer countDownTimer,countDownTimer1;
    int progress;
    private int progress1;
    private Dialog Sub_Get_dialog;
    private Dialog Wrongdialog;
    int rejoin_coint=0;
    int ques_new_id=0;
    int tv_ques_btn=0;//****if 1=means tv_ans1, 2=tv_ans2, 3=tv_ans3. no select=0;
    private String tv_click_ans="";
    private Dialog Quitdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__live_);

        sessionManager=new SessionManager(Play_Live_Activity.this);
        tv_set_question=findViewById(R.id.tv_set_question);
        tv_ans1=findViewById(R.id.tv_ans1);
        tv_ans2=findViewById(R.id.tv_ans2);
        tv_ans3=findViewById(R.id.tv_ans3);
        tv_setcoin=findViewById(R.id.tv_setcoin);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        tv_ques_no=findViewById(R.id.tv_ques_no);
        tv_live_player=findViewById(R.id.tv_live_player);
        progressBarView=findViewById(R.id.view_progress_bar);
        tv_timer=findViewById(R.id.tv_timer);
        relative_progress_bar= findViewById(R.id.relative_progress_bar);
        relative_timesup= findViewById(R.id.relative_timesup);
        relative_wrong= findViewById(R.id.relative_wrong);
        relative_correct= findViewById(R.id.relative_correct);
        iv_back= findViewById(R.id.iv_back);

//*************************song theme.music start******

        try {
            gameMusicStart();
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){}

        if (Connectivity.isConnected(Play_Live_Activity.this)){
            getLiveQuestion();
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

    });
        //*****************ans submit button*****
        tv_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ans1.setBackgroundResource(R.drawable.triang_gray);
                tv_click_ans=tv_ans1.getText().toString();
                tv_ques_btn=1;

            }
        });

        tv_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ans2.setBackgroundResource(R.drawable.triang_gray);
                tv_click_ans=tv_ans2.getText().toString();
                tv_ques_btn=2;

//                if (Connectivity.isConnected(Play_Live_Activity.this)){
//                    Submitting_Ans(tv_click_ans,tv_ans2);
//                }else {
//                    Toast.makeText(Play_Live_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
//                }
            }
        });

         tv_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ans3.setBackgroundResource(R.drawable.triang_gray);
               tv_click_ans=tv_ans3.getText().toString();
                tv_ques_btn=3;

//                if (Connectivity.isConnected(Play_Live_Activity.this)){
//                    Submitting_Ans(tv_click_ans,tv_ans2);
//                }else {
//                    Toast.makeText(Play_Live_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    //************************submitting answer android-networking method api***below
    // already make async task*
//    private void Submitting_Ans(final String tv_sub_ans, final int textView_ans) {
//
//        Submit_And_Getting_Dialog();
//
//        String url=Base_Url.BaseUrl+Base_Url.submit_live_question;
//        String user_id=AppPreference.getUser_Id(Play_Live_Activity.this);
//        Log.e("submit_ans_url"," "+url);
//        AndroidNetworking.post(url)
//                .addBodyParameter("user_id",user_id)
//                .addBodyParameter("event_id",sessionManager.getEventId())
//                .addBodyParameter("question_id",quiz_ques_id)
//                .addBodyParameter("answer",tv_sub_ans)
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//
//                        Sub_Get_dialog.dismiss();
//                        Log.e("submit_ans_live", jsonObject.toString());
//                       tv_click_ans="";
//                        tv_ques_btn=0;
//
//                        try {
//                            Sub_Get_dialog.dismiss();
//                            String message = jsonObject.getString("msg");
//                            String result = jsonObject.getString("result");
//                            String live_users = jsonObject.getString("live_users");
//                            String answer = jsonObject.getString("answer");
//
//                            JSONObject jsonObject1=jsonObject.getJSONObject("question");
//                            String correct_ans = jsonObject1.getString("correct_ans");
//                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
//                            if (result.equalsIgnoreCase("true")){
//                                tv_ans1.setClickable(false);
//                                tv_ans2.setClickable(false);
//                                tv_ans3.setClickable(false);
//
//                            }else {
//                                // tv_answer.setClickable(true);
//                            }
//                           // Toast.makeText(Play_Live_Activity.this, ""+answer, Toast.LENGTH_SHORT).show();
//                            if (answer.equalsIgnoreCase("Right")){
//                                if (textView_ans==1){
//                                    tv_ans1.setBackgroundResource(R.drawable.triang_right);
//                                }else if (textView_ans==2){
//                                    tv_ans2.setBackgroundResource(R.drawable.triang_right);
//                                }else if (textView_ans==3){
//                                    tv_ans3.setBackgroundResource(R.drawable.triang_right);
//                                }
//                               // NextQues_handler();
//                            }else if (answer.equalsIgnoreCase("wrong")){
//                                if (textView_ans==1){
//                                    tv_ans1.setBackgroundResource(R.drawable.triang_wrong);
//                                }else if (textView_ans==2){
//                                    tv_ans2.setBackgroundResource(R.drawable.triang_wrong);
//                                }else if (textView_ans==3){
//                                    tv_ans3.setBackgroundResource(R.drawable.triang_wrong);
//                                }
//
//                                //************check correct ans and change bgcolor**
//                                if (correct_ans.equalsIgnoreCase(tv_ans1.getText().toString())){
//                                    tv_ans1.setBackgroundResource(R.drawable.triang_right);
//                                }else  if (correct_ans.equalsIgnoreCase(tv_ans2.getText().toString())){
//                                    tv_ans2.setBackgroundResource(R.drawable.triang_right);
//                                }else  if (correct_ans.equalsIgnoreCase(tv_ans3.getText().toString())){
//                                    tv_ans3.setBackgroundResource(R.drawable.triang_right);
//                                }
//
//                                //wrong method call
//                                WrongAnsDialog();
//                            }else {
////                                if (correct_ans.equalsIgnoreCase(tv_ans1.getText().toString())){
////                                    tv_ans1.setBackgroundResource(R.drawable.triang_right);
////                                }else  if (correct_ans.equalsIgnoreCase(tv_ans2.getText().toString())){
////                                    tv_ans2.setBackgroundResource(R.drawable.triang_right);
////                                }else  if (correct_ans.equalsIgnoreCase(tv_ans3.getText().toString())){
////                                    tv_ans3.setBackgroundResource(R.drawable.triang_right);
////                                }
//                            }
//                            NextQues_handler();
//                            //*******************set text*****
//                            tv_live_player.setText(live_users);
//
//                        } catch (JSONException e) {
//                            Sub_Get_dialog.dismiss();
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Sub_Get_dialog.dismiss();
//                        //tv_answer.setClickable(true);
//                        Log.e("error_submit_show", anError.toString());
//                        Log.e("error_submit_show", ""+anError.getErrorCode());
//                        try {
//                           // Submitting_Ans(tv_click_ans,tv_ques_btn);
//                        }catch (Exception e){
//
//                        }
//                    }
//                });
//
//    }

    private void WrongAnsDialog(final Integer re_join_coin, final int q_no) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (re_join_coin!=0){

                    if (rejoin_coint<3 || re_join_coin==3){

                        if (q_no<9 || q_no==9){
                            OpenWrongDialog(re_join_coin);
                        }

                    }
                }

            }
        }, 3000);

    }

    private void OpenWrongDialog(Integer re_join_coin) {
        Wrongdialog = new Dialog(Play_Live_Activity.this);
        Wrongdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Wrongdialog.setCancelable(true);
        Wrongdialog.setContentView(R.layout.rejoin_live_game_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Wrongdialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        Wrongdialog.getWindow().setAttributes(lp);

        Button btn_rejoin_yes=Wrongdialog.findViewById(R.id.btn_rejoin_yes);
        Button btn_rejoin_no=Wrongdialog.findViewById(R.id.btn_rejoin_no);
        ProgressBar rejoin_progress_bar=Wrongdialog.findViewById(R.id.rejoin_progress_bar);
        TextView tv_rejoin_timer=Wrongdialog.findViewById(R.id.tv_rejoin_timer);
        TextView tv_rejoin_coin=Wrongdialog.findViewById(R.id.tv_rejoin_coin);

        tv_rejoin_coin.setText(re_join_coin.toString());

        wrongProgressAnim(rejoin_progress_bar);
        wrongCountdown(tv_rejoin_timer,rejoin_progress_bar);

        btn_rejoin_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Wrongdialog.dismiss();
                //***call rejoin api******************
                RejoinLiveGame();


            }
        });
        btn_rejoin_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wrongdialog.dismiss();
            }
        });
        try {
            if (!Play_Live_Activity.this.isFinishing()){
                Wrongdialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }


    }

    private void wrongCountdown(final TextView tv_rejoin_timer, final ProgressBar rejoin_progress_bar) {
        Log.e("wrong_counttime", "wrong_Timer");

        // if (et_timer.getText().toString().length()>0) {
        try {
            countDownTimer1.cancel();

        } catch (Exception e) {

        }

        String timeInterval = "6";//10seconds
        progress1 = 1;
        final int endTime1 = Integer.parseInt(timeInterval); // up to finish time

       countDownTimer1 = new CountDownTimer(endTime1 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // gameMusicStart();
               // setProgress_wrong(progress, endTime);
                setProgress_wrong(progress1, endTime1,rejoin_progress_bar);
                progress1 = progress1 + 1;

                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String new_time = "" + seconds;
                tv_rejoin_timer.setText(new_time);

            }


            @Override
            public void onFinish() {
                Log.e("countFinish", "Timer_finish");
                //MusicStop();
                setProgress_wrong(progress1, endTime1,rejoin_progress_bar);

                try {
                    countDownTimer1.cancel();
                    Wrongdialog.dismiss();
                } catch (Exception e) { }

            }
        };
        countDownTimer1.start();
    }

    private void setProgress_wrong(int progress1, int endTime1, ProgressBar rejoin_progress_bar) {
        rejoin_progress_bar.setMax(endTime1);
        rejoin_progress_bar.setSecondaryProgress(endTime1);
        rejoin_progress_bar.setProgress(progress1);
    }

    private void wrongProgressAnim(ProgressBar rejoin_progress_bar) {
        Log.e("wrong_anim", "wrong_count");
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        rejoin_progress_bar.startAnimation(makeVertical);
        rejoin_progress_bar.setSecondaryProgress(endTime);
        rejoin_progress_bar.setProgress(0);
    }

    //************************rejoin api live game***
    private void RejoinLiveGame() {
//        final ProgressDialog progressDialog = new ProgressDialog(Play_Live_Activity.this);
//        //progressDialog.setMessage("Rejoining...");
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.show();


        String eventId= sessionManager.getEventId();
        String user_id= AppPreference.getUser_Id(Play_Live_Activity.this);
        String url= Base_Url.BaseUrl+Base_Url.re_join_live_game;
        Log.e("rejoin_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("event_id",eventId)
                .addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                       // progressDialog.dismiss();
                        Log.e("rejoin_responce = ", jsonObject.toString());

                        try {
                            Wrongdialog.dismiss();
                            //progressDialog.dismiss();

                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")){
                                rejoin_coint++;

                                Log.e("rejoin_coint", ""+String.valueOf(rejoin_coint));

                                String live_users = jsonObject.getString("live_users");
                                String coin = jsonObject.getString("coin");

                                tv_wallet_coin.setText(coin);
                                tv_live_player.setText(live_users);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                       // progressDialog.dismiss();
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });

    }

    private void NextQues_handler() {
            handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Connectivity.isConnected(Play_Live_Activity.this)){
                    getLiveQuestion();
                }else {
                    Toast.makeText(Play_Live_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);
    }

    //********************getLive Question api call*************
    private void getLiveQuestion() {
        //progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Submit_And_Getting_Dialog();

        tv_click_ans="";
        tv_ques_btn=0;
        tv_ans1.setClickable(true);
        tv_ans2.setClickable(true);
        tv_ans3.setClickable(true);
        tv_ans1.setBackgroundResource(R.drawable.triang_light_blue);
        tv_ans2.setBackgroundResource(R.drawable.triang_light_blue);
        tv_ans3.setBackgroundResource(R.drawable.triang_light_blue);

        //******************visible or invisible views*****
        relative_progress_bar.setVisibility(View.VISIBLE);
        relative_wrong.setVisibility(View.GONE);
        relative_correct.setVisibility(View.GONE);
        relative_timesup.setVisibility(View.GONE);

        String eventId= sessionManager.getEventId();
        String url= Base_Url.BaseUrl+Base_Url.Get_Live_Question;
        Log.e("get_live_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("event_id",eventId)
                .addBodyParameter("question_id", String.valueOf(ques_new_id))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Sub_Get_dialog.dismiss();
                        Log.e("get_live_quis = ", jsonObject.toString());

                        try {
                            Sub_Get_dialog.dismiss();

                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")){
                                String live_users = jsonObject.getString("live_users");

                                JSONObject jsonObject1=jsonObject.getJSONObject("question");

                                    quiz_ques_id = jsonObject1.getString("id");
                                    //String live_quiz_event_id = jsonObject1.getString("live_quiz_event_id");
                                    String q_no = jsonObject1.getString("q_no");
                                    String question = jsonObject1.getString("question");
                                    String option_a = jsonObject1.getString("option_a");
                                    String option_b = jsonObject1.getString("option_b");
                                    String option_c = jsonObject1.getString("option_c");
                                   // String correct_ans = jsonObject1.getString("correct_ans");
                                    String coin = jsonObject1.getString("coin");

                                    ques_new_id= Integer.parseInt(quiz_ques_id);

                                    tv_ques_no.setText("Q"+q_no+".");
                                    tv_set_question.setText(question);
                                    tv_ans1.setText(option_a);
                                    tv_ans2.setText(option_b);
                                    tv_ans3.setText(option_c);
                                    tv_setcoin.setText(coin);
                                    tv_live_player.setText(live_users);

                                    //***********set progress animation and 10sec countdown timer**
                                progressAnimationMaker();
                                fn_countdown("11");

                            }else {
                                Intent intent=new Intent(Play_Live_Activity.this, LiveCompleteActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Sub_Get_dialog.dismiss();
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });


    }

    private void Submit_And_Getting_Dialog() {

        Sub_Get_dialog = new Dialog(Play_Live_Activity.this);
        Sub_Get_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Sub_Get_dialog.setCancelable(false);
        Sub_Get_dialog.setContentView(R.layout.progress_check_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Sub_Get_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        Sub_Get_dialog.getWindow().setAttributes(lp);

        try {
            if (!Play_Live_Activity.this.isFinishing()){
                Sub_Get_dialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    private void fn_countdown(String s) {
        Log.e("counttime", "Timer_fn");

        // if (et_timer.getText().toString().length()>0) {
        try {
            countDownTimer.cancel();

        } catch (Exception e) {

        }

        String timeInterval = s;//10seconds
        progress = 1;
        endTime = Integer.parseInt(timeInterval); // up to finish time

        countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // gameMusicStart();
                setProgress(progress, endTime);
                progress = progress + 1;

                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String new_time = "" + seconds;
                tv_timer.setText(new_time);

            }

            @Override
            public void onFinish() {
                Log.e("countFinish", "Timer_finish");
                //MusicStop();
                setProgress(progress, endTime);

                relative_progress_bar.setVisibility(View.GONE);
                if(tv_click_ans.equalsIgnoreCase("")){
                     relative_timesup.setVisibility(View.VISIBLE);
                }

                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
                //Timeout_Wrong_MusicStart();
                try {
                    countDownTimer.cancel();
                } catch (Exception e) { }
                // progressTimerCheck();
               // intNext++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Connectivity.isConnected(Play_Live_Activity.this)){
                           // Submitting_Ans(tv_click_ans,tv_ques_btn);
                            new Submitting_Ans(tv_click_ans,tv_ques_btn).execute();

                        }else {
                            Toast.makeText(Play_Live_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                        }
                       // NextQues_handler();
                    }
                }, 500);

            }
        };
        countDownTimer.start();
        // }else {
        //Toast.makeText(getApplicationContext(),"Please enter the value",Toast.LENGTH_LONG).show();
        //}
    }

    private void Timeout_Wrong_MusicStart() {
        MediaPlayer mediaPlayer= MediaPlayer.create(Play_Live_Activity.this,R.raw.wrong_ans_music);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
    }

    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);

    }

    private void progressAnimationMaker() {
        Log.e("prog_anim", "anim_count");
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);
    }

    private void gameMusicStart() {
            mediaPlayer= MediaPlayer.create(Play_Live_Activity.this,R.raw.theme_song);
            mediaPlayer.setLooping(true);
            //mediaPlayer.setVolume(100,100);
            mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mediaPlayer.stop();
        }catch (Exception e){}

        try{
            rejoin_coint=0;
        }catch (Exception e){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mediaPlayer.pause();
        }catch (Exception e){}
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        OpenQuitDialog();
        try{
            rejoin_coint=0;
        }catch (Exception e){

        }

    }

    private void OpenQuitDialog() {
        Quitdialog = new Dialog(Play_Live_Activity.this);
        Quitdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Quitdialog.setCancelable(true);
        Quitdialog.setContentView(R.layout.quit_dialog_open);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btn_quit_yes=Quitdialog.findViewById(R.id.btn_quit_yes);
        Button btn_quit_no=Quitdialog.findViewById(R.id.btn_quit_no);


        btn_quit_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    countDownTimer.cancel();
                    mediaPlayer.stop();
                }catch (Exception e){}

                try{
                    if(handler!=null){
                        handler.removeCallbacksAndMessages(null);
                    }
                }catch (Exception e){
                    Log.e("onback_dialog_error", e.getMessage());
                }

                try {
                    if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer = null;
                    }
                }catch (Exception e){
                    Log.e("onback_media", e.getMessage());
                }
                try {
                    if (Sub_Get_dialog != null) {
                        Sub_Get_dialog.dismiss();
                    }
                    if (Wrongdialog != null) {
                        Wrongdialog.dismiss();
                    }
                }catch (Exception e){
                }

                Quitdialog.dismiss();
                Intent intent=new Intent(Play_Live_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        btn_quit_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quitdialog.dismiss();
            }
        });
        try {
            if (!Play_Live_Activity.this.isFinishing()){
                Quitdialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mediaPlayer.stop();
        }catch (Exception e){}

        try{
            rejoin_coint=0;
        }catch (Exception e){

        }
    }

    //************using async task method**************
    private class Submitting_Ans extends AsyncTask<String, Integer, String> {

        protected void onPreExecute() {
            Submit_And_Getting_Dialog();

        }

        String TV_CLICK_ANS;
        int TV_QUES_BTN;
        public Submitting_Ans(String tv_click_ans, int tv_ques_btn) {
            this.TV_CLICK_ANS=tv_click_ans;
            this.TV_QUES_BTN=tv_ques_btn;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String url1=Base_Url.BaseUrl+Base_Url.submit_live_question;
                String user_id=AppPreference.getUser_Id(Play_Live_Activity.this);
                Log.e("submit_ans_url"," "+url1);

                URL url = new URL(url1);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id",user_id);
                postDataParams.put("event_id",sessionManager.getEventId());
                postDataParams.put("question_id",quiz_ques_id);
                postDataParams.put("answer",TV_CLICK_ANS);

                Log.e("postDataParams", postDataParams.toString());

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                SSLContext sslcontext = SSLContext.getInstance("TLSv1");
                // SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                sslcontext.init(null, null, null);
                SSLEngine engine = sslcontext.createSSLEngine();

                sslcontext.init(null,null,null);
                SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setDefaultSSLSocketFactory(new ImprovedSSLSocketFactory());
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        result.append(line);
                    }
                    r.close();
                    return result.toString();

                } else {
                    return new String("false : " + responseCode);
                }

            }
            else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds*/);
                conn.setConnectTimeout(15000  /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        result.append(line);
                    }
                    r.close();
                    return result.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            }



        }
        catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Sub_Get_dialog.dismiss();

                Log.e("submit_ans_live", result.toString());
                tv_click_ans="";
                tv_ques_btn=0;

                try {
                    Sub_Get_dialog.dismiss();
                    relative_timesup.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(result);

                    String message = jsonObject.getString("msg");
                    String result1 = jsonObject.getString("result");
                    String live_users = jsonObject.getString("live_users");
                    String answer = jsonObject.getString("answer");
                    Integer re_join_coin = jsonObject.getInt("re_join_coin");
                    String coin = jsonObject.getString("coin");

                    JSONObject jsonObject1=jsonObject.getJSONObject("question");
                    String correct_ans = jsonObject1.getString("correct_ans");
                    String q_no = jsonObject1.getString("q_no");
                    //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    if (result1.equalsIgnoreCase("true")){
                        tv_ans1.setClickable(false);
                        tv_ans2.setClickable(false);
                        tv_ans3.setClickable(false);

                    }else {
                        // tv_answer.setClickable(true);
                    }
                    // Toast.makeText(Play_Live_Activity.this, ""+answer, Toast.LENGTH_SHORT).show();
                    if (answer.equalsIgnoreCase("Right")){
                        relative_correct.setVisibility(View.VISIBLE);

                        if (TV_QUES_BTN==1){
                            tv_ans1.setBackgroundResource(R.drawable.triang_right);
                        }else if (TV_QUES_BTN==2){
                            tv_ans2.setBackgroundResource(R.drawable.triang_right);
                        }else if (TV_QUES_BTN==3){
                            tv_ans3.setBackgroundResource(R.drawable.triang_right);
                        }
                         NextQues_handler();
                    }else if (answer.equalsIgnoreCase("wrong")){
                        relative_wrong.setVisibility(View.VISIBLE);
                        if (TV_QUES_BTN==1){
                            tv_ans1.setBackgroundResource(R.drawable.triang_wrong);
                        }else if (TV_QUES_BTN==2){
                            tv_ans2.setBackgroundResource(R.drawable.triang_wrong);
                        }else if (TV_QUES_BTN==3){
                            tv_ans3.setBackgroundResource(R.drawable.triang_wrong);
                        }

                        //************check correct ans and change bgcolor**
                        if (correct_ans.equalsIgnoreCase(tv_ans1.getText().toString())){
                            tv_ans1.setBackgroundResource(R.drawable.triang_right);
                        }else  if (correct_ans.equalsIgnoreCase(tv_ans2.getText().toString())){
                            tv_ans2.setBackgroundResource(R.drawable.triang_right);
                        }else  if (correct_ans.equalsIgnoreCase(tv_ans3.getText().toString())){
                            tv_ans3.setBackgroundResource(R.drawable.triang_right);
                        }

                        //wrong method call
                        WrongAnsDialog(re_join_coin,Integer.parseInt(q_no));
                        NextQues_handler();

                    }else {
                       // if(tv_click_ans.equalsIgnoreCase("")){
                            relative_timesup.setVisibility(View.VISIBLE);
                       // }
//                        if (Connectivity.isConnected(Play_Live_Activity.this)){
//                            getLiveQuestion();
//                        }else {
//                            Toast.makeText(Play_Live_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
//                        }
                                if (correct_ans.equalsIgnoreCase(tv_ans1.getText().toString())){
                                    tv_ans1.setBackgroundResource(R.drawable.triang_right);
                                }else  if (correct_ans.equalsIgnoreCase(tv_ans2.getText().toString())){
                                    tv_ans2.setBackgroundResource(R.drawable.triang_right);
                                }else  if (correct_ans.equalsIgnoreCase(tv_ans3.getText().toString())){
                                    tv_ans3.setBackgroundResource(R.drawable.triang_right);
                                }

                        NextQues_handler();
                    }

                    //*******************set text*****
                    tv_live_player.setText(live_users);
                    tv_wallet_coin.setText(coin);

                } catch (JSONException e) {
                    Sub_Get_dialog.dismiss();
                    e.printStackTrace();
                }

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    }




}
