package com.trivia.lambatriviaapp.Activity.LiveGameActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.ThreeSecondSplashActivity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class LiveBannerVideoShow extends AppCompatActivity {

    TextView txt_price,txt_name_title,txt_subtitle,txt_timer,txt_live_player,tv_wallet_coin;
    private Timer t;
    public int seconds = 30;
    public int minutes = 0;
    ImageView iv_game_banner;
    VideoView myVideo;
    RelativeLayout rl_banner_img,rl_video_view;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_banner_video_show);
        sessionManager=new SessionManager(LiveBannerVideoShow.this);

        txt_price=findViewById(R.id.txt_price);
        txt_name_title=findViewById(R.id.txt_name_title);
        txt_subtitle=findViewById(R.id.txt_subtitle);
        txt_timer=findViewById(R.id.txt_timer);
        txt_live_player=findViewById(R.id.txt_live_player);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        iv_game_banner=findViewById(R.id.game_start_banner);
        myVideo=findViewById(R.id.myVideo);
        rl_banner_img=findViewById(R.id.rl_banner_img);
        rl_video_view=findViewById(R.id.rl_video_view);

        StartShowTimer();
        try{
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){

        }

        if (Connectivity.isConnected(LiveBannerVideoShow.this)){
           // getLive_banner();//************save live data responce in prefrence
            get_live_game();
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getLive_banner() {
        try{
            String liveArrary=AppPreference.getLivegamejsondata(LiveBannerVideoShow.this);
            if (liveArrary != null) {
                try {
                    Log.e("Offline_res", "Offline_res");
                    JSONArray jsonArray = new JSONArray(liveArrary);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);

                        String live_game_id = job.getString("id");
                        String name = job.getString("name");
                        String type = job.getString("type");
                        String total_coin = job.getString("total_coin");
                        String price_money = job.getString("price_money");
                        String background_banner = job.getString("background_banner");
                        String date = job.getString("date");
                        String time = job.getString("time");
                        String sub_title = job.getString("sub_title");
                        String content_type = job.getString("content_type");
                        String game_start_banner = job.getString("game_start_banner");

                        sessionManager.setEventId(live_game_id);
                        Log.e("game_start_banner", game_start_banner);
                        txt_name_title.setText(name);
                        txt_subtitle.setText(sub_title);
                        txt_price.setText(price_money);

                        try {
                            if (content_type.equalsIgnoreCase("Image")){
                                rl_video_view.setVisibility(View.GONE);
                                rl_banner_img.setVisibility(View.VISIBLE);
                                Glide.with(LiveBannerVideoShow.this)
                                        .load(Base_Url.live_quiz_banner+game_start_banner)
                                        //.error(R.drawable.error_image) // show error drawable if the image is not a gif
                                        .into(iv_game_banner);
                            }
                            else {
                                try{
                                    rl_banner_img.setVisibility(View.GONE);
                                    rl_video_view.setVisibility(View.VISIBLE);

                                    MediaController vidControl = new MediaController(LiveBannerVideoShow.this);
                                    String vidAddress = Base_Url.live_quiz_banner+game_start_banner;
                                    Uri vidUri = Uri.parse(vidAddress);
                                    myVideo.setVideoURI(vidUri);
                                    myVideo.start();
                                    vidControl.setAnchorView(myVideo);
                                    myVideo.setMediaController(vidControl);

                                }catch (Exception e){

                                }
                            }

                        }catch (Exception e){

                        }

                    }

                } catch (JSONException e) {
                    Log.e("set_live_error", e.toString());
                }
            }else {
                get_live_game();
            }

        }catch (Exception e){

        }


    }

    //********************live game api again call*******
    private void get_live_game() {
        final ProgressDialog progressDialog = new ProgressDialog(LiveBannerVideoShow.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        String url= Base_Url.BaseUrl+Base_Url.Get_Live_Quiz;
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
                            JSONObject jsonObject1=jsonObject.getJSONObject("rule");
                           // rules = jsonObject1.getString("rules");

                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String live_game_id = job.getString("id");
                                    String name = job.getString("name");
                                    String type = job.getString("type");
                                   // String total_coin = job.getString("total_coin");
                                    String price_money = job.getString("price_money");
                                    String background_banner = job.getString("background_banner");
                                    String date = job.getString("date");
                                    String time = job.getString("time");
                                    String sub_title = job.getString("sub_title");
                                    String content_type = job.getString("content_type");
                                    String game_start_banner = job.getString("game_start_banner");

                                    //*****need live game status= playing or closed****
                                    sessionManager.setEventId(live_game_id);
                                    Log.e("game_start_banner", game_start_banner);
                                    txt_name_title.setText(name);
                                    txt_subtitle.setText(sub_title);
                                    txt_price.setText(price_money);

                                    try {
                                        if (content_type.equalsIgnoreCase("Image")){
                                            rl_video_view.setVisibility(View.GONE);
                                            rl_banner_img.setVisibility(View.VISIBLE);
                                            Glide.with(LiveBannerVideoShow.this)
                                                    .load(Base_Url.live_quiz_banner+game_start_banner)
                                                    //.error(R.drawable.error_image) // show error drawable if the image is not a gif
                                                    .into(iv_game_banner);
                                        }
                                        else {
                                            try{
                                                rl_banner_img.setVisibility(View.GONE);
                                                rl_video_view.setVisibility(View.VISIBLE);

                                                MediaController vidControl = new MediaController(LiveBannerVideoShow.this);
                                                String vidAddress = Base_Url.live_quiz_banner+game_start_banner;
                                                Uri vidUri = Uri.parse(vidAddress);
                                                myVideo.setVideoURI(vidUri);
                                                myVideo.start();
                                                vidControl.setAnchorView(myVideo);
                                                myVideo.setMediaController(vidControl);

                                            }catch (Exception e){

                                            }
                                        }

                                    }catch (Exception e){

                                    }

                                }


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

    private void StartShowTimer() {

        try {

            t = new Timer();
            //Set the schedule function and rate
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt_timer.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                            seconds -= 1;

                            if(seconds == 0)
                            {
                                t.cancel();

                            Intent intent=new Intent(LiveBannerVideoShow.this, ThreeSecondSplashActivity.class);
                            intent.putExtra("startgame", "liveGame");
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
            myVideo.stopPlayback();
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            t.cancel();
            myVideo.stopPlayback();
        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            t.cancel();
            myVideo.stopPlayback();
        }catch (Exception e){

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            t.cancel();
            myVideo.stopPlayback();
        }catch (Exception e){

        }
    }

}
