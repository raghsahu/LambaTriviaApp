package com.trivia.lambatriviaapp.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.value.ScaleXY;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.LeaguePlaySeeMore;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Play_league_Activity;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.Play_Live_Activity;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.Start_RulesActivity;
import com.trivia.lambatriviaapp.Activity.News_ReadMore_Activity;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_QuizStart_Activity;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_quizActivity;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.ThreeSecondSplashActivity;
import com.trivia.lambatriviaapp.Activity.Video_Clip_list_Activity;
import com.trivia.lambatriviaapp.Adapter.NewsBlogAdapter;
import com.trivia.lambatriviaapp.Adapter.ShowLeaderAdapter;
import com.trivia.lambatriviaapp.Adapter.Show_League_Adapter;
import com.trivia.lambatriviaapp.Adapter.Show_Practice_Adapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.BuildConfig;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.LeaderBoard_Model;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Model;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Question_practice;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.QuizDataCount;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Show_Practice_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Service.ImprovedSSLSocketFactory;
import com.trivia.lambatriviaapp.Service.NoSSLv3SocketFactory;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlitehelper;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Looper.getMainLooper;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.trivia.lambatriviaapp.MainActivity.user_name;

public class Home_Frament extends Fragment {

    Button btn_practice_home_quiz,btn_invite,btn_video_play;
    RecyclerView recycler_practice,recycler_league_game,recycler_news_blog;
    private Show_Practice_Adapter show_practice_adapter;
    private Show_League_Adapter show_league_adapter;
    private NewsBlogAdapter showNewsBlogAdapter;
    Sqlitehelper sqlitehelper;
    SessionManager sessionManager;
    TextView tv_invite_msg,tv_view_more,tv_live_title,tv_live_win,tv_live_nexttime,tv_news_more;
    LinearLayout ll_live_play;
    ArrayList<News_blog_model>news_blog_models=new ArrayList<>();
    private String rules,date,time,live_game_id,background_banner,need_coin;
    private TextClock tClock;
    private CountDownTimer newtimer;
    private Dialog Joindialog;
    private CountDownTimer newtimer1;
    private CountDownTimer newtimer2;
    private CountDownTimer newtimer3;
    // AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        sqlitehelper=new Sqlitehelper(getActivity());

        //MobileAds.initialize(getActivity(), "ca-app-pub-8993976198123599~1693999084");
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });


      //  mAdView = (AdView)root.findViewById(R.id.adView);

//        AdRequest.Builder adRequest = new AdRequest.Builder();
//
//        String ANDROID_ID = Settings.Secure.getString(getContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        String deviceId = md5(ANDROID_ID).toUpperCase();
//        Log.e("ANDROID_ID", ""+ANDROID_ID);
//        Log.e("deviceId", ""+deviceId);
//
//        adRequest.addTestDevice(deviceId);
//
//        AdRequest request = adRequest.build();
//        mAdView.loadAd(request);
//
//        mAdView = new AdView(getActivity());
//        mAdView.setAdSize(AdSize.SMART_BANNER);
//        mAdView.setAdUnitId("ca-app-pub-8993976198123599/2907008404");
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                Log.e("ban_adsload", "Banner");
//                Toast.makeText(getActivity(), "Banner ads", Toast.LENGTH_SHORT).show();
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Log.e("ban_ads", ""+errorCode);
//                Toast.makeText(getActivity(), "Banner fail", Toast.LENGTH_SHORT).show();
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                Log.e("ban_adsopen", "Banner");
//                Toast.makeText(getActivity(), "Banner open", Toast.LENGTH_SHORT).show();
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//            @Override
//            public void onAdClicked() {
//                Log.e("ban_adsclick", "Banner");
//                Toast.makeText(getActivity(), "Banner click", Toast.LENGTH_SHORT).show();
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                Log.e("ban_adsleft", "Banner");
//                Toast.makeText(getActivity(), "Banner left", Toast.LENGTH_SHORT).show();
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                Log.e("ban_adsclose", "Banner");
//                Toast.makeText(getActivity(), "Banner close", Toast.LENGTH_SHORT).show();
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//        });

// TODO: Add adView to your view hierarchy.

        btn_invite=root.findViewById(R.id.btn_invite);
        btn_practice_home_quiz=root.findViewById(R.id.btn_practice_home_quiz);
        recycler_practice  =root.findViewById(R.id.recycler_practice);
        recycler_league_game  =root.findViewById(R.id.recycler_league_game);
        recycler_news_blog  =root.findViewById(R.id.recycler_news_blog);
        tv_invite_msg  =root.findViewById(R.id.tv_invite_msg);
        tv_view_more  =root.findViewById(R.id.tv_view_more);
        ll_live_play  =root.findViewById(R.id.ll_live_play);
        tv_live_title  =root.findViewById(R.id.tv_live_title);
        tv_live_win  =root.findViewById(R.id.tv_live_win);
        tv_live_nexttime  =root.findViewById(R.id.tv_live_nexttime);
        tv_news_more  =root.findViewById(R.id.tv_news_more);
        btn_video_play  =root.findViewById(R.id.btn_video_play);

        if (Connectivity.isConnected(getActivity())){
            get_practice_quiz("1");
           // get_League_game();
           // get_News_blog();
            get_live_game();

            //***********not required only testing
           // getLeaguegame();//************aneroid_networking method
          //  new GetLeagueAsync().execute();

        }else {
            Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
        }


//**********************************************************************************
        String json_array = AppPreference.getJsondata(getActivity());
        if (json_array != null) {
            try {
                JSONArray jsoArray = new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    JSONObject job = jsoArray.getJSONObject(i);
                    String username = job.getString("username");

                    tv_invite_msg.setText("Earn coin use"+" "+username+" "+"as referral code");
                }
            } catch (JSONException e) {
                Log.e("set_homefrg_error", e.toString());
            }
        }else {
            //tv_invite_msg.setText("Earn coin use"+" "+user_name+" "+"as referral code");

            if (!AppPreference.getName(getActivity()).equals("")){
            tv_invite_msg.setText("Earn coin use"+" "+AppPreference.getName(getActivity())+" "+"as referral code");
                }
        }




        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Fragment view_creat=new Invite_Fragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_content,view_creat);
//                fragmentTransaction.commit();
                shareFriends();

            }
        });
        btn_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Video_Clip_list_Activity.class);
                startActivity(intent);

            }
        });

        btn_practice_home_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_practice_quiz("2");
            }
        });
        tv_news_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), News_ReadMore_Activity.class);
                startActivity(intent);
            }
        });

        tv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LeaguePlaySeeMore.class);
                startActivity(intent);
            }
        });
        ll_live_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.e("rules_live", rules);

                try{
                    if (!tv_live_nexttime.getText().toString().equalsIgnoreCase("comming soon")){
                        JoinLiveGame();
                    }


                }catch (Exception e){

                }

            }
        });

        return root;
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private void getLeaguegame() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.get_League_Game;
        Log.e("get_league_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_league_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {


//                                    Picasso.with(MainActivity.this)
//                                            .load(Base_Url.MenuImageUrl+image)
//                                            .into(iv_profile);
                                   // leaderBoardModelArrayList.add(i,new LeaderBoard_Model(username,total_naira,image) );
                                }



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_league_show", String.valueOf(anError));
                        Log.e("error_league_show1",""+ anError.getErrorCode());
                        Log.e("error_league_show2",""+ anError.getMessage());

                    }
                });
    }

    private void showtlivetime() {

        try {
            //***********set banner****
            Picasso.with(getActivity()).load(Base_Url.live_quiz_banner+background_banner)
                    .placeholder(R.drawable.league_place_holder_img)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            ll_live_play.setBackground(new BitmapDrawable(bitmap));
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

      //  Log.e("showtlivetime", "is showtlivetime date");
        try {

            newtimer2 = new CountDownTimer(1000000000, 1000) {
                public void onTick(long millisUntilFinished) {


                    //**********************************
                    String delegate = "hh:mm:ss";
                    String AmPmTime= (String) DateFormat.format(delegate, Calendar.getInstance().getTime());

                    //**********if time equal then finish
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startDate = null;
                    try {
                        startDate = sdf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String Api_ampm= (String) DateFormat.format(delegate,startDate);
                    Log.e("Api_ampm", ""+Api_ampm);

                    if (AmPmTime.equalsIgnoreCase(Api_ampm)){
                        Log.e("Api_am==", "Api_am");
                        onFinish();
                    }else {

                        DisplayLiveTime();

                    }



                }
                public void onFinish() {
                    AutomaticJoinDialog();
                    newtimer2.cancel();

                }
            };
            newtimer2.start();

        }catch (Exception e){

        }


    }

    private void DisplayLiveTime() {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        if (date!=null && !date.isEmpty()){

            try {
                Date startDate = sdf.parse(date);
                System.out.println(startDate);
                // Log.e("start_date", startDate.toString());

                Date currentDate = new Date();

                long diff = startDate.getTime() - currentDate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                if (minutes<5 && (seconds >0)){
                    showdigitalClock();

                    Log.e("min_left", ""+minutes);
                }else{
                    Log.e("min_left+5", ""+minutes);
                    tv_live_nexttime.setText("Next Game "+time);

                    Log.e("api_time", ""+time);

                }

                if (minutes==5){
                    AutomaticJoinDialog();
                }

            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
    }

    private void showdigitalClock() {
//        newtimer1 = new CountDownTimer(1000000000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
               // //Calendar c = Calendar.getInstance();
               // // tv_live_nexttime.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                String delegate = "hh:mm:ss";
                String AmPmTime = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());

                tv_live_nexttime.setText("Next Game " + AmPmTime);
                Log.e("AmPmTime1", "" + AmPmTime);

               // //**********if time equal then finish
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = null;
                try {
                    startDate = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String Api_ampm = (String) DateFormat.format(delegate, startDate);
                Log.e("Api_ampm1", "" + Api_ampm);

                if (AmPmTime.equalsIgnoreCase(Api_ampm)) {
                    Log.e("Api_am==1", "Api_am");
                   // onFinish();
                    AutomaticJoinDialog();
                }

//            }
//
//            public void onFinish() {
//
//                try {
//                    AutomaticJoinDialog();
//                    newtimer1.cancel();
//                    tv_live_nexttime.setText("Comming soon ");
//                } catch (Exception e) {
//
//                }
//
//            }
//        };
//        newtimer1.start();
    }


    //********************************************join live game*****
    private void JoinLiveGame() {
        String delegate = "hh:mm:ss aaa";
       String AmPmTime= (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        Log.e("currentAmPm", AmPmTime);

       // tv_live_nexttime

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.e("currentDateandTime", currentDateandTime);

        if (date!=null && !date.isEmpty()){

        try {
            Date startDate = sdf.parse(date);
            System.out.println(startDate);
            Log.e("start_date", startDate.toString());

            Date currentDate = new Date();

            long diff = startDate.getTime() - currentDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

           /// if (startDate.after(currentDate)) {

                //Log.e("oldDate", "is previous date");
                Log.e("Difference1: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);

                if (seconds<61 && seconds>-1){
                    AutomaticJoinDialog();
//                    if (Connectivity.isConnected(getActivity())){
//                            JoinLiveGameApi();
//                    }else {
//                        Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
//                        }

                        Log.e("sec_left", ""+minutes);
                }else {
                        Log.e("live_min_left+5", ""+minutes);

                       // AutomaticJoinDialog();

                    }

        } catch (ParseException e) {

            e.printStackTrace();
        }
    }


    }

    private void AutomaticJoinDialog() {

        Joindialog = new Dialog(getActivity());
        Joindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Joindialog.setCancelable(true);
        Joindialog.setContentView(R.layout.live_dialog_open);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btn_join_yes=Joindialog.findViewById(R.id.btn_quit_yes);
        Button btn_join_no=Joindialog.findViewById(R.id.btn_quit_no);
        TextView tv_need_coin=Joindialog.findViewById(R.id.tv_need_coin);
        tv_need_coin.setText(need_coin);

        btn_join_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Joindialog.dismiss();

                if (Connectivity.isConnected(getActivity())){
                    JoinLiveGameApi();
                }else {
                    Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_join_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Joindialog.dismiss();
            }
        });
        try {
            if (!getActivity().isFinishing()){
                Joindialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }


    }

    //*******************join new live game---------
    private void JoinLiveGameApi() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.join_live_game;
        Log.e("get_join_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("event_id",live_game_id)
                .addBodyParameter("status","1")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("join_live_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();

                            String result = jsonObject.getString("result");
//                            JSONObject jsonObject1=jsonObject.getJSONObject("rule");
//                            rules = jsonObject1.getString("rules");


                            if (result.equalsIgnoreCase("true")){

                                Intent intent = new Intent(getActivity(), Start_RulesActivity.class);
                                intent.putExtra("live_rules", rules);
                                startActivity(intent);

                                Toast.makeText(getActivity(), "Successfull join", Toast.LENGTH_SHORT).show();
//
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject job = jsonArray.getJSONObject(i);
//                                    String id = job.getString("id");
//                                    String name = job.getString("name");
//                                    //String type = job.getString("type");
//                                    String total_coin = job.getString("total_coin");
//                                    String price_money = job.getString("price_money");
//                                    String background_banner = job.getString("background_banner");
//
//
                                }else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                            }


                           // }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_joinlive", String.valueOf(anError));

                    }
                });

    }

    //************get live game**************

    private void get_live_game() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.Get_Live_Quiz;
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.get(url)
                //.addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_live_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();

                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")){
                                JSONObject jsonObject1=jsonObject.getJSONObject("rule");
                                rules = jsonObject1.getString("rules");
                               // ll_live_play.setClickable(true);

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray!=null){
                                    AppPreference.setLivegamejsondata(getActivity(), jsonArray);
                                }

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    live_game_id = job.getString("id");
                                    String name = job.getString("name");
                                    String type = job.getString("type");
                                     need_coin = job.getString("need_coin");
                                    String price_money = job.getString("price_money");
                                    background_banner = job.getString("background_banner");
                                    date = job.getString("date");
                                    time = job.getString("time");
                                    String sub_title = job.getString("sub_title");
                                    String content_type = job.getString("content_type");
                                    String game_start_banner = job.getString("game_start_banner");

                                    //*****need live game status= playing or closed****

                                    Log.e("background_banner", background_banner);
                                    tv_live_title.setText(name);
                                    tv_live_win.setText(price_money);
                                    //tv_live_nexttime.setText("Next Game "+time);
                                    showtlivetime();

                                    try {
                                        Picasso.with(getActivity()).load(Base_Url.live_quiz_banner+background_banner)
                                                .placeholder(R.drawable.league_place_holder_img)
                                                .into(new Target() {
                                                    @Override
                                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                        ll_live_play.setBackground(new BitmapDrawable(bitmap));
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

                            }else {
                                tv_live_nexttime.setText("Comming soon");
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

    ///********************news_blog_api***
    private void get_News_blog() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.news_blog;
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.get(url)
                //.addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_news_response = ", jsonObject.toString());

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
                                    String title = job.getString("title");
                                    String image = job.getString("image");
                                    String news_link = job.getString("news_link");
                                    String coin = job.getString("coin");

                                    news_blog_models.add(i,new News_blog_model(id,title,image,news_link,coin) );
                                }

                                showNewsBlogAdapter= new NewsBlogAdapter( getActivity(),news_blog_models);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_news_blog.setLayoutManager(mLayoutManger);
                                recycler_news_blog.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                                recycler_news_blog.setItemAnimator(new DefaultItemAnimator());
                                recycler_news_blog.setAdapter(showNewsBlogAdapter);

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

    private void OpenPracticeQuiz(List<QuizDataCount> quizDataCountArrayList) {
        sessionManager=new SessionManager(getActivity());
        sqlitehelper=new Sqlitehelper(getActivity());
        //String quiz_id=quizDataCountArrayList.get(0).getId();
        sessionManager.setQuizId(String.valueOf(0));
        //Log.e("quiz_position", ""+0);
        try{
            sqlitehelper.deleteQuestion_practice();
           // Log.e("quiz_id_adap", quiz_id);
            Log.e("quizDataCount", ""+quizDataCountArrayList.get(0).getQuestions().size());
            Log.e("sqliteCount", ""+sqlitehelper.getAllQuestion_practice().size());

            if (sqlitehelper.getAllQuestion_practice().size()==0){

                for (int i=0;i<quizDataCountArrayList.get(0).getQuestions().size();i++){
                    Log.e("quiz_question",""+ quizDataCountArrayList.get(0).getQuestions().get(i).getQuestion());
                    Log.e("quiz_ques_ans",""+ quizDataCountArrayList.get(0).getQuestions().get(i).getCorrectAns());

                    sqlitehelper.addPracticeQuistion(quizDataCountArrayList.get(0).getQuestions());

                }
            }else {
                Log.e("sql_update", "update");
                sqlitehelper.deleteQuestion_practice();
                Log.e("sqlCount_delete", ""+sqlitehelper.getAllQuestion_practice().size());
                for (int i=0;i<quizDataCountArrayList.get(0).getQuestions().size();i++){
                    Log.e("quiz_question",""+ quizDataCountArrayList.get(0).getQuestions().get(i).getQuestion());
                    Log.e("quiz_ques_ans",""+ quizDataCountArrayList.get(0).getQuestions().get(i).getCorrectAns());

                    sqlitehelper.addPracticeQuistion(quizDataCountArrayList.get(0).getQuestions());
                    // sqlitehelper.updateQuestion_practice(quizDataCountArrayList.get(position).getQuestions());
                }
                Log.e("sql_updateCount", ""+sqlitehelper.getAllQuestion_practice().size());
            }

            Log.e("AfterInser_sql_Count", ""+sqlitehelper.getAllQuestion_practice().size());

        }catch (Exception e){
            Log.e("add_sql_error",e.getMessage());
        }

        Intent intent=new Intent(getActivity(), Practice_quizActivity.class);
        intent.putExtra("quiz_coin", quizDataCountArrayList.get(0).getTotalCoin());
        startActivity(intent);

    }

    private void shareFriends() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Lamba");
            String shareMessage= "\nLet me recommend you this app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    //*******************league game api call**************************************
    private void get_League_game() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        List tlsSpecs = Arrays.asList(ConnectionSpec.MODERN_TLS);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            tlsSpecs = Arrays.asList(ConnectionSpec.COMPATIBLE_TLS);
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                //.connectionSpecs(tlsSpecs)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url.BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        String user_id=AppPreference.getUser_Id(getActivity());

        Api_Call service = retrofit.create(Api_Call.class);

        Call<Show_League_Model> call = service.get_league(user_id);

        call.enqueue(new Callback<Show_League_Model>() {
            @Override
            public void onResponse(Call<Show_League_Model> call, Response<Show_League_Model> response) {

                try{

                    if (response!=null){
                        Log.e("league_msg",""+response.body().getResult());

//                        for (int i=0;i<response.body().getData().size();i++)
//                        {
//                           // Log.e("get_league_resp" , ""+response.body().getData().get(i).getStartDateTime());
//
//                            if (response.body().getData().get(i).getLeague_questions()!=null){
//
//                                //sqlitehelper.addPracticeQuistion((QuizDataCount) response.body().getData().get(i).getQuestions());
//                            }
//
//                        }

                        show_league_adapter= new Show_League_Adapter( getActivity(),response.body().getData(),response.body().getRules());
                        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                        recycler_league_game.setLayoutManager(mLayoutManger);
                        recycler_league_game.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                        recycler_league_game.setItemAnimator(new DefaultItemAnimator());
                        recycler_league_game.setAdapter(show_league_adapter);

                    }
                }catch (Exception e){
                    Log.e("excep_leage", e.getMessage());
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


//*****************get practice_game api call**********************
    private void get_practice_quiz(final String s) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
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
                   // Log.e("practice_msg",""+response.body().getMsg());

                    for (int i=0;i<response.body().getData().size();i++)
                    {
                       // Log.e("quiz_name" , ""+response.body().getData().get(i).getName());
                       // Log.e("quiz_coin" , ""+response.body().getData().get(i).getTotalCoin());
                       // Log.e("quiz_Q_data" , ""+response.body().getData().get(i).getQuestions());

                        if (response.body().getData().get(i).getQuestions()!=null){

                            //sqlitehelper.addPracticeQuistion((QuizDataCount) response.body().getData().get(i).getQuestions());
                        }

                    }

                    if (s.equalsIgnoreCase("2")){
                        OpenPracticeQuiz(response.body().getData());
                    }



                show_practice_adapter= new Show_Practice_Adapter( getActivity(),response.body().getData());
                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                recycler_practice.setLayoutManager(mLayoutManger);
                recycler_practice.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                recycler_practice.setItemAnimator(new DefaultItemAnimator());
                recycler_practice.setAdapter(show_practice_adapter);

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
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
       // setHasOptionsMenu(true);
       // getActivity().setTitle("My Activity");





    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            newtimer3 = new CountDownTimer(1000000000, 1000*10) {
                public void onTick(long millisUntilFinished) {

                    //showtlivetime();


                }
                public void onFinish() {


                }
            };
            newtimer3.start();

        }catch (Exception e){

        }


    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            //newtimer.onFinish();
            newtimer.cancel();
            newtimer1.cancel();
            newtimer3.cancel();
        }catch (Exception e){

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            newtimer.cancel();
            newtimer2.cancel();
            newtimer3.cancel();
        }catch (Exception e){

        }
    }


    private class GetLeagueAsync extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            try {
                String url1=Base_Url.BaseUrl+Base_Url.get_League_Game;
                String user_id=AppPreference.getUser_Id(getActivity());
                Log.e("leag__url"," "+url1);

                URL url = new URL(url1);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id",user_id);


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
            Log.e("leag_result", ""+result.toString());

            if (result != null) {

                Log.e("leag_res_", result.toString());

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String result1 = jsonObject.getString("result");



                    // Toast.makeText(Play_Live_Activity.this, ""+answer, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
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
