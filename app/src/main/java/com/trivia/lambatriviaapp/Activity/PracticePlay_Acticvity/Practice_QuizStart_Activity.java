package com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.Video_Clip_list_Activity;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.BuildConfig;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Show_Practice_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlitehelper;

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

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.trivia.lambatriviaapp.MainActivity.total_coin;
import static com.trivia.lambatriviaapp.MainActivity.tv_coinMain;

public class Practice_QuizStart_Activity extends AppCompatActivity  {

    int myProgress = 0;
    ProgressBar progressBarView;
    TextView tv_time,tv_setcoin,tv_ques_no;
    LinearLayout ll_quiz_card,ll_progress_check;
    ImageView iv_quiz_back;
    int progress;
    CountDownTimer countDownTimer;
    int endTime = 11;

    RelativeLayout relative_progress_bar,relative_timesup,relative_wrong,relative_correct;
    MediaPlayer mediaPlayer;
    boolean isPlaying= false;
    TextView tv_ans1,tv_ans2,tv_ans3,tv_set_question,tv_coin;
    SessionManager sessionManager;
    Sqlitehelper sqlitehelper;
    //ArrayList<Quiz_practiceModel>questionPractices=new ArrayList<>();
    //public static LinkedList <Quiz_practiceModel>link_level_question = new LinkedList<>();
    int intNext=0;
    int intNextLevelGame=0;
    public int seconds = 3;
    private Dialog dialog;
    private Dialog Quitdialog;

    final Handler handler = new Handler();
    private int TotalWinCoin=0;
    private int sum;

    //**********ad related
    private static final String AD_UNIT_ID = "ca-app-pub-8993976198123599/3482281955";
    private InterstitialAd interstitialAd;
     InterstitialAd adView;  // The ad
    private Handler mHandler;       // Handler to display the ad on the UI thread
    private Runnable displayAd;
     AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_practice__quiz_start_);
        interstitialAd = new InterstitialAd(this);
        sessionManager=new SessionManager(Practice_QuizStart_Activity.this);
        sqlitehelper=new Sqlitehelper(Practice_QuizStart_Activity.this);

        MobileAds.initialize(this, "ca-app-pub-8993976198123599~1693999084");
        interstitialAd.setAdUnitId(AD_UNIT_ID);
        adRequest = new AdRequest.Builder().build();

//        MobileAds.initialize(Practice_QuizStart_Activity.this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });

        //*************interstial ads
//        adView = new InterstitialAd(Practice_QuizStart_Activity.this);
//        adView.setAdUnitId(getString(R.string.lamba_interstitial_ad));
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//
//                mHandler = new Handler(Looper.getMainLooper());
//                displayAd = new Runnable() {
//                    public void run() {
////                        try {
////                           // wait(60*1000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                if (adView.isLoaded()) {
//                                    adView.show();
//                                }
//                            }
//                        });
//                    }
//                };
//                loadAd();
//            }
//
//            @Override
//            public void onAdClosed() {
//                loadAd(); // Need to reload the Ad when it is closed.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//        });



   //*************************
        try{
            Log.e("quiz_id_position", sessionManager.getQuizId());
            intNextLevelGame= Integer.parseInt(sessionManager.getQuizId());

        }catch (Exception e){

        }

        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        tv_time= (TextView)findViewById(R.id.tv_timer);
        relative_progress_bar= findViewById(R.id.relative_progress_bar);
        relative_timesup= findViewById(R.id.relative_timesup);
        relative_wrong= findViewById(R.id.relative_wrong);
        relative_correct= findViewById(R.id.relative_correct);
        tv_ans1= findViewById(R.id.tv_ans1);
        tv_ans2= findViewById(R.id.tv_ans2);
        tv_ans3= findViewById(R.id.tv_ans3);
        tv_setcoin= findViewById(R.id.tv_setcoin);
        tv_set_question= findViewById(R.id.tv_set_question);
        ll_quiz_card= findViewById(R.id.ll_quiz_card);
        tv_ques_no= findViewById(R.id.tv_ques_no);
        ll_progress_check= findViewById(R.id.ll_progress_check);
        iv_quiz_back= findViewById(R.id.iv_quiz_back);
        tv_coin= findViewById(R.id.tv_coin);

        try{
           // for (int i=0;i<sqlitehelper.getAllQuestion_practice().size();i++){
                getNextQuestion();
                gameMusicStart();
           // }

        }catch (Exception e){
            Log.e("p_ques_error", e.toString());
        }
        try {
            tv_coin.setText(total_coin);
        }catch (Exception e){

        }

        iv_quiz_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
  //***********************************************
        tv_ans1.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
//                if(stateChanged) {
//                    // reset background to default;
//                    tv_ans1.setBackgroundResource(R.drawable.quiz_btn_blue);
//                } else {
                  //  tv_ans1.setBackgroundResource(R.drawable.tv_clickchange_bg);
                    tv_ans1.setBackgroundResource(R.drawable.triang_gray);
                     String tv_click_ans=tv_ans1.getText().toString();
                     OpenProgressChecking(tv_click_ans,tv_ans1);
//                }
//                stateChanged = !stateChanged;
            }
        });

        tv_ans2.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
               // if(stateChanged) {
                    // reset background to default;
                   // tv_ans2.setBackgroundResource(R.drawable.quiz_btn_blue);
                //} else {
                    tv_ans2.setBackgroundResource(R.drawable.triang_gray);
                    String tv_click_ans=tv_ans2.getText().toString();
                    OpenProgressChecking(tv_click_ans,tv_ans2);

               // }
               // stateChanged = !stateChanged;
            }
        });

        tv_ans3.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged;
            public void onClick(View view) {
                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
//                if(stateChanged) {
//                    // reset background to default;
//                    tv_ans3.setBackgroundResource(R.drawable.quiz_btn_blue);
//                } else {
                    tv_ans3.setBackgroundResource(R.drawable.triang_gray);
                    String tv_click_ans=tv_ans3.getText().toString();
                    OpenProgressChecking(tv_click_ans,tv_ans3);

//                }
//                stateChanged = !stateChanged;
            }
        });
//*******************************************************
    }

    private void loadAd() {

        interstitialAd.loadAd(adRequest);
//        AdRequest adRequest = new AdRequest.Builder()
//                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        // Load the adView object witht he request
//        adView.loadAd(adRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();


                }

            }

            @Override
            public void onAdOpened() {
                AddCoinWallet();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });



    }

    private void AddCoinWallet() {

        String user_id= AppPreference.getUser_Id(Practice_QuizStart_Activity.this);
        String url=Base_Url.BaseUrl+Base_Url.coin_add_on_view_ads;
        Log.e("get_ads_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("type","1")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //  progressDialog.dismiss();
                        Log.e("get_ads_response = ", jsonObject.toString());

                        try {
                            // progressDialog.dismiss();
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        //progressDialog.dismiss();
                        Log.e("error_ads_show", String.valueOf(anError));

                    }
                });

    }

    //Call displayInterstitial() once you are ready to display the ad.
    public void displayInterstitial() {
        mHandler.postDelayed(displayAd, 1);
    }
//************************************

    private void OpenProgressChecking(final String tv_click_ans, final TextView tv_ans) {
        try {
            //mediaPlayer.stop();
            countDownTimer.cancel();
        } catch (Exception e) {
        }
        open_dialog_checking();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.dismiss();
                CheckRight_ans(tv_click_ans,tv_ans);

            }
        }, 2000);

    }

    private void open_dialog_checking() {
            dialog = new Dialog(Practice_QuizStart_Activity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.progress_check_dialog);
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);

        try {
            if (!Practice_QuizStart_Activity.this.isFinishing()){
                dialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    private void CheckRight_ans(String tv_click_ans, TextView textview_ans) {

        Log.e("int_next_count", ""+intNext);
        try {
            //mediaPlayer.stop();
            //countDownTimer.cancel();

            if (tv_click_ans.equalsIgnoreCase(sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns()))
            {
                try {
                    // Toast.makeText(Practice_QuizStart_Activity.this, "right_ans", Toast.LENGTH_SHORT).show();
                    relative_progress_bar.setVisibility(View.GONE);
                    relative_correct.setVisibility(View.VISIBLE);
                    Correct_Ans_MusicStart();
                    //  textview_ans.setBackgroundResource(R.drawable.button_selector);
                    textview_ans.setBackgroundResource(R.drawable.triang_right);

                }catch (Exception e){

                }


//                try {
//                    if (!sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns().
//                            equalsIgnoreCase(tv_ans1.getText().toString()))
//                    {
//                        tv_ans1.setBackgroundResource(R.drawable.tv_clickchange_bg);
//                    }else if (!sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns().
//                            equalsIgnoreCase(tv_ans2.getText().toString())){
//                        tv_ans2.setBackgroundResource(R.drawable.tv_clickchange_bg);
//                    }else if (!sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns().
//                            equalsIgnoreCase(tv_ans3.getText().toString())){
//                        tv_ans3.setBackgroundResource(R.drawable.tv_clickchange_bg);
//                    }else {
//                        textview_ans.setBackgroundResource(R.drawable.button_selector);
//                    }
//                }catch (Exception e){
//                    Log.e("SearchAns_error", e.getMessage());
//

                intNext++;
                if (Connectivity.isConnected(Practice_QuizStart_Activity.this)){
                    addCoinMyWallet(tv_setcoin.getText().toString(),sum);
                }else {
                    Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
                OpenNextQuestionHandler();


                try
                {
                    TotalWinCoin= TotalWinCoin+Integer.parseInt(tv_setcoin.getText().toString()) ;
                   Log.e("TotalWincoin", ""+TotalWinCoin);
                }
                catch (NumberFormatException exception)
                {
                    Log.e("TotalWincoin_error", exception.toString());
                }


            }else {
               // Toast.makeText(Practice_QuizStart_Activity.this, "wrong_ans", Toast.LENGTH_SHORT).show();
                relative_progress_bar.setVisibility(View.GONE);
                relative_wrong.setVisibility(View.VISIBLE);
                Timeout_Wrong_MusicStart();
               // textview_ans.setBackgroundResource(R.drawable.quiz_wrong_ans_bg);
                textview_ans.setBackgroundResource(R.drawable.triang_wrong);

                SearchExactAns();
                intNext++;
                OpenNextQuestionHandler();
            }
        }catch (Exception e){
            Log.e("next_ques_error", e.getMessage());
        }

    }

    //****************addcoin to my wallet**************
    private void addCoinMyWallet(String myquizcoin, int sum) {
        String url=Base_Url.BaseUrl+Base_Url.get_coin;
        String user_id=AppPreference.getUser_Id(Practice_QuizStart_Activity.this);
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("coin",myquizcoin)
                .addBodyParameter("ques_no", String.valueOf(sum))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //progressDialog.dismiss();
                        Log.e("get_coin_response = ", jsonObject.toString());

                        try {
                            //progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                if (jsonObject1.length()!=0){

                                        String id = jsonObject1.getString("id");
                                        String total_coin = jsonObject1.getString("total_coin");
                                        String total_naira = jsonObject1.getString("total_naira");

                                        tv_coin.setText(total_coin);
                                         tv_coinMain.setText(total_coin);
                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        //progressDialog.dismiss();
                        Log.e("error_prof_show", String.valueOf(anError));

                    }
                });

    }

    private void SearchExactAns() {
        try {
            if (sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns().
                    equalsIgnoreCase(tv_ans1.getText().toString()))
            {
                tv_ans1.setBackgroundResource(R.drawable.triang_right);
            }else if (sqlitehelper.getAllQuestion_practice().get(intNext).getCorrectAns().
                    equalsIgnoreCase(tv_ans2.getText().toString())){
                tv_ans2.setBackgroundResource(R.drawable.triang_right);
            }else {
                tv_ans3.setBackgroundResource(R.drawable.triang_right);
            }
        }catch (Exception e){
            Log.e("SearchAns_error", e.getMessage());
        }

    }

    private void OpenNextQuestionHandler() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              OpenNextQuestionProgress();

            }
        }, 3000);
    }

    private void OpenNextQuestionProgress() {
        open_dialog_checking();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                getNextQuestion();

            }
        }, 3000);
    }


    private void progressAnimationMaker() {
        Log.e("prog_anim", "anim_count");
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);
    }

    private void getNextQuestion() {
        try {

            //Log.e("first_qnext", "first_next");
            Log.e("getAllQuestion_size", " "+sqlitehelper.getAllQuestion_practice().size());
//           for (int i=0; i<sqlitehelper.getAllQuestion_practice().size();i++){
//               Log.e("practice_start_Q", ""+sqlitehelper.getAllQuestion_practice().get(i).getQuestion());
//           }

            if(sqlitehelper.getAllQuestion_practice().size()>0){
                  //  if (sqlitehelper.getAllQuestion_practice().get(intNext+1).getQuestion().length()!=0){
                        tv_ans1.setClickable(true);
                        tv_ans2.setClickable(true);
                        tv_ans3.setClickable(true);
                        tv_ans1.setBackgroundResource(R.drawable.triang_light_blue);
                        tv_ans2.setBackgroundResource(R.drawable.triang_light_blue);
                        tv_ans3.setBackgroundResource(R.drawable.triang_light_blue);
                       // tv_ans3.setBackgroundResource(R.drawable.quiz_btn_blue);

                        relative_progress_bar.setVisibility(View.VISIBLE);
                        relative_wrong.setVisibility(View.GONE);
                        relative_correct.setVisibility(View.GONE);
                        relative_timesup.setVisibility(View.GONE);

                        tv_setcoin.setText(sqlitehelper.getAllQuestion_practice().get(intNext).getCoin());
                        tv_set_question.setText(" "+sqlitehelper.getAllQuestion_practice().get(intNext).getQuestion());
                        tv_ans1.setText(sqlitehelper.getAllQuestion_practice().get(intNext).getOptionA());
                        tv_ans2.setText(sqlitehelper.getAllQuestion_practice().get(intNext).getOptionB());
                        tv_ans3.setText(sqlitehelper.getAllQuestion_practice().get(intNext).getOptionC());

                        try
                        {
                            sum=intNext+1;
                            tv_ques_no.setText("Q"+sum+".");
                        }
                        catch (NumberFormatException exception)
                        {
                            Log.e("tv_ques_no_error", exception.toString());
                        }

                        progressAnimationMaker();
                        fn_countdown();

                  //  }

            }else {
                ll_quiz_card.setVisibility(View.GONE);
               // Toast.makeText(this, "no quiz found", Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            Log.e("complete_error", ""+e.toString());
            ll_quiz_card.setVisibility(View.GONE);
            NextQuizLevel_Dialog(TotalWinCoin);
            mediaPlayer.stop();
           // Toast.makeText(this, "sorry no quiz found", Toast.LENGTH_SHORT).show();

        }
    }

    //**********************next quiz level dialog*****************
    private void NextQuizLevel_Dialog(int totalWinCoin) {

        loadAd();

        final Dialog NextQuizLevel_dialog;

        NextQuizLevel_dialog = new Dialog(Practice_QuizStart_Activity.this);
        NextQuizLevel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        NextQuizLevel_dialog.setCancelable(true);
        NextQuizLevel_dialog.setContentView(R.layout.complete_quiz_dialog_open);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(NextQuizLevel_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        NextQuizLevel_dialog.getWindow().setAttributes(lp);


        ImageView play_next=NextQuizLevel_dialog.findViewById(R.id.play_next);
        Button iv_share=NextQuizLevel_dialog.findViewById(R.id.iv_share);
        ImageView iv_profile=NextQuizLevel_dialog.findViewById(R.id.iv_profile);
        TextView user_name=NextQuizLevel_dialog.findViewById(R.id.user_name);
        TextView tv_wincoin=NextQuizLevel_dialog.findViewById(R.id.text_win);

        try{
           // if (NextQuizLevel_dialog.isShowing()){
            Log.e("TotalWin_end", ""+totalWinCoin);
                user_name.setText(AppPreference.getName(this));
                tv_wincoin.setText(String.valueOf(totalWinCoin));
           // }

        }catch (Exception e){
        Log.e("win_end_error", e.toString());
        }


        String json_array = AppPreference.getJsondata(this);
        if (json_array != null) {
            try {
                JSONArray jsoArray=new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    JSONObject job = jsoArray.getJSONObject(i);
                    String image = job.getString("image");

                    Picasso.with(this)
                            .load(Base_Url.MenuImageUrl+image)
                            //.placeholder(R.drawable.person)
                            .into(iv_profile);
                }
            } catch (JSONException e) {
                Log.e("complete_dialog_error", e.toString());
            }
        }

        play_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intNext=0;
                intNextLevelGame++;
                TotalWinCoin=0;
                Log.e("intNextLevelGame", ""+intNextLevelGame);
                getNextLevelGame();
                NextQuizLevel_dialog.dismiss();

            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            share_my_friend();
            }
        });

        try {
            if (!Practice_QuizStart_Activity.this.isFinishing()){
                NextQuizLevel_dialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    //***********change game level using api****
    private void getNextLevelGame() {

        final ProgressDialog progressDialog = new ProgressDialog(Practice_QuizStart_Activity.this,R.style.MyGravity);
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

        Api_Call service = retrofit.create(Api_Call.class);

        Call<Show_Practice_Model> call = service.get_practice();

        call.enqueue(new Callback<Show_Practice_Model>() {
            @Override
            public void onResponse(Call<Show_Practice_Model> call, Response<Show_Practice_Model> response) {

                try{

                    if (response!=null){
                        Log.e("get_practice_resp" , ""+response.toString());
                        //Log.e("practice_msg",""+response.body().getMsg());

                            if (response.body().getData().get(intNextLevelGame).getQuestions()!=null){

                                try{
                                    sqlitehelper.deleteQuestion_practice();
                                    Log.e("quizDataCount", ""+response.body().getData().get(intNextLevelGame).getQuestions().size());

                                    if (sqlitehelper.getAllQuestion_practice().size()==0){

                                        for (int i=0;i<response.body().getData().get(intNextLevelGame).getQuestions().size();i++){
                                            Log.e("quiz_question",""+ response.body().getData().get(intNextLevelGame).getQuestions().get(i).getQuestion());
                                            Log.e("quiz_ques_ans",""+ response.body().getData().get(intNextLevelGame).getQuestions().get(i).getCorrectAns());

                                            sqlitehelper.addPracticeQuistion(response.body().getData().get(intNextLevelGame).getQuestions());

                                        }
                                    }else {
                                        Log.e("sql_update", "update");
                                        sqlitehelper.deleteQuestion_practice();
                                        Log.e("sqlCount_delete", ""+sqlitehelper.getAllQuestion_practice().size());
                                        for (int i=0;i<response.body().getData().get(intNextLevelGame).getQuestions().size();i++){
                                            Log.e("quiz_question",""+ response.body().getData().get(intNextLevelGame).getQuestions().get(i).getQuestion());
                                            Log.e("quiz_ques_ans",""+ response.body().getData().get(intNextLevelGame).getQuestions().get(i).getCorrectAns());

                                            sqlitehelper.addPracticeQuistion(response.body().getData().get(intNextLevelGame).getQuestions());

                                        }
                                        Log.e("sql_updateCount", ""+sqlitehelper.getAllQuestion_practice().size());
                                    }

                                }catch (Exception e){
                                    Log.e("add_sql_error",e.getMessage());
                                }

                                ll_quiz_card.setVisibility(View.VISIBLE);
                                getNextQuestion();
                                gameMusicStart();
                            }


                    }
                }catch (Exception e){
                    Log.e("excep_get_pract", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Show_Practice_Model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_practice",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
      //*********************getnextquiz api call end************************

    }

    private void share_my_friend() {
      //  Toast.makeText(this, "Share success", Toast.LENGTH_SHORT).show();


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

    //********************************count down timer run-*****************
    private void fn_countdown() {
        Log.e("counttime", "Timer_fn");

       // if (et_timer.getText().toString().length()>0) {
           try {
                countDownTimer.cancel();

            } catch (Exception e) {

            }

            String timeInterval = "11";//10seconds
            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                   // gameMusicStart();
                    isPlaying= true;
                    setProgress(progress, endTime);
                    progress = progress + 1;

                    int seconds = (int) (millisUntilFinished / 1000) % 60;

                    String new_time = "" + seconds;
                    tv_time.setText(new_time);

                }

                @Override
                public void onFinish() {
                    Log.e("countFinish", "Timer_finish");
                    //MusicStop();
                    setProgress(progress, endTime);

                    relative_progress_bar.setVisibility(View.GONE);
                    relative_timesup.setVisibility(View.VISIBLE);
                    tv_ans1.setClickable(false);
                    tv_ans2.setClickable(false);
                    tv_ans3.setClickable(false);
                    Timeout_Wrong_MusicStart();
                   // progressTimerCheck();
                    intNext++;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AfterStopNextQuestion();
                        }
                    }, 3000);

                }
            };
            countDownTimer.start();
       // }else {
            //Toast.makeText(getApplicationContext(),"Please enter the value",Toast.LENGTH_LONG).show();
        //}

    }

    private void AfterStopNextQuestion() {
       // SearchExactAns();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OpenNextQuestionProgress();
            }
        }, 1000);
    }

    private void Timeout_Wrong_MusicStart() {
       MediaPlayer mediaPlayer= MediaPlayer.create(Practice_QuizStart_Activity.this,R.raw.wrong_ans_music);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
    }

    private void Correct_Ans_MusicStart() {
        MediaPlayer mediaPlayer= MediaPlayer.create(Practice_QuizStart_Activity.this,R.raw.correct_answer_music);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
    }

    private void MusicStop() {

//        AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
//        if(manager.isMusicActive())
//        {
//            mediaPlayer.stop();
//        }
        try {
            if(mediaPlayer.isPlaying())
            {
                Log.e("music_isPlaying", ""+isPlaying);
                if (isPlaying==true){
                    isPlaying= false;
                    mediaPlayer.stop();
                }
                Log.e("music_stop", ""+isPlaying);
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop();
               // mediaPlayer.release();
                mediaPlayer = null;
            }

        }catch (Exception e){
            Log.e("music_stop_error", e.toString());
        }


    }

    private void gameMusicStart() {
        mediaPlayer= MediaPlayer.create(Practice_QuizStart_Activity.this,R.raw.theme_song);
        mediaPlayer.setLooping(true);
        //mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
    }

    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);

    }

    @Override
    protected void onStop() {
        super.onStop();
        intNext=0;
        intNextLevelGame=0;
        isPlaying=false;
        try {
            if (mediaPlayer != null) {
                if (isPlaying==true){
                    isPlaying=false;
                    mediaPlayer.stop();
                   // mediaPlayer.release();
                    mediaPlayer = null;
                }

            }
        }catch (Exception e){
            Log.e("onback_error", e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPlaying=false;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
                //mediaPlayer.release();

            }
        }


//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//
//        boolean screenOn;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
//            screenOn = pm.isInteractive();
//        } else {
//            screenOn = pm.isScreenOn();
//        }
//
//        if (screenOn) {
//            // Screen is still on, so do your thing here
//        }else {
//            if (mediaPlayer != null) {
//                mediaPlayer.pause();
//            }
//        }




    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        OpenQuitDialog();

    }

    private void OpenQuitDialog() {
        Quitdialog = new Dialog(Practice_QuizStart_Activity.this);
        Quitdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Quitdialog.setCancelable(true);
        Quitdialog.setContentView(R.layout.quit_dialog_open);
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btn_quit_yes=Quitdialog.findViewById(R.id.btn_quit_yes);
        Button btn_quit_no=Quitdialog.findViewById(R.id.btn_quit_no);


        btn_quit_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intNext=0;
                    intNextLevelGame=0;

                    try{
                        countDownTimer.cancel();
                    }catch (Exception e){
                    }
                    try{
                        if(handler!=null){
                            handler.removeCallbacksAndMessages(null);
                        }

                        if (dialog!=null){
                            dialog.dismiss();
                        }
                    }catch (Exception e){
                    Log.e("onback_dialog_error", e.getMessage());
                    }

                    try {
                        if (mediaPlayer != null) {
                            if (isPlaying==true){
                                isPlaying=false;
                                mediaPlayer.stop();
                                //mediaPlayer.release();
                                mediaPlayer = null;
                            }

                        }
                    }catch (Exception e){
                        Log.e("onback_error", e.getMessage());
                    }
                    Quitdialog.dismiss();
                    Intent intent=new Intent(Practice_QuizStart_Activity.this,MainActivity.class);
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
            if (!Practice_QuizStart_Activity.this.isFinishing()){
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
        intNext=0;
        intNextLevelGame=0;

        try {
            mediaPlayer.stop();
        }catch (Exception e){}
    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            displayInterstitial();
        }catch (Exception e){

        }

    }




}
