package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.trivia.lambatriviaapp.Adapter.NewsBlogAdapter;
import com.trivia.lambatriviaapp.Adapter.VideoClipAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.Model_Class.Video_Clip_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class Video_Clip_list_Activity extends AppCompatActivity implements RewardedVideoAdListener {

    RecyclerView recycler_video_list;
    ImageView iv_back;
    TextView tv_wallet_coin;
    ArrayList<Video_Clip_model> video_clip_list=new ArrayList<>();
    private VideoClipAdapter videoClipAdapter;

    private RewardedVideoAd mRewardedVideoAd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__clip_list_);

        recycler_video_list=findViewById(R.id.recycler_video_list);
        iv_back=findViewById(R.id.iv_back);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);

        rewardAds();
        if (Connectivity.isConnected(Video_Clip_list_Activity.this)){
           // get_Video_Clip();
        }else {
            Toast.makeText(Video_Clip_list_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
        }
        try{
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){ }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }



    private void get_Video_Clip() {
        final ProgressDialog progressDialog = new ProgressDialog(Video_Clip_list_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(Video_Clip_list_Activity.this);
        String url= Base_Url.BaseUrl+Base_Url.get_video_clips;
        Log.e("get_video_url"," "+url);
        AndroidNetworking.get(url)
                //.addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_video_response = ", jsonObject.toString());

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
                                    String coin = job.getString("coin");
                                    String video = job.getString("video");


                                    video_clip_list.add(i,new Video_Clip_model(id,title,coin,video) );
                                }

                                videoClipAdapter= new VideoClipAdapter( Video_Clip_list_Activity.this,video_clip_list);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Video_Clip_list_Activity.this);
                                recycler_video_list.setLayoutManager(mLayoutManger);
                                recycler_video_list.setLayoutManager(new LinearLayoutManager(Video_Clip_list_Activity.this, RecyclerView.VERTICAL, false));
                                recycler_video_list.setAdapter(videoClipAdapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_video_show", String.valueOf(anError));

                    }
                });

    }


//*********************************************


    private void rewardAds() {
        progressDialog = new ProgressDialog(Video_Clip_list_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.lamba_rewarded_ad),//use this id for testing
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

    }

    @Override
    public void onRewardedVideoAdOpened() {
        progressDialog.dismiss();
      //  Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    onBackPressed();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
//        Toast.makeText(this, "Download to Earn" + rewardItem.getType() + "  amount: " +
//                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        progressDialog.dismiss();
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        progressDialog.dismiss();
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        progressDialog.dismiss();
       // Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();

        //rewardAds();

        AddCoinWallet();
    }

    private void AddCoinWallet() {
//        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        String user_id= AppPreference.getUser_Id(Video_Clip_list_Activity.this);
        String url=Base_Url.BaseUrl+Base_Url.coin_add_on_view_ads;
        Log.e("get_ads_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("type","0")
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
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
