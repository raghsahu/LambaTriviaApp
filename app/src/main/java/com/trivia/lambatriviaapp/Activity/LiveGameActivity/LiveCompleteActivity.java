package com.trivia.lambatriviaapp.Activity.LiveGameActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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
import com.trivia.lambatriviaapp.Activity.Video_Clip_list_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Custom_Class.CustomLayoutManager;
import com.trivia.lambatriviaapp.Adapter.LiveCompleteWinnerAdapter;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.Live_Game_Model.LiveWinner_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiveCompleteActivity extends AppCompatActivity implements RewardedVideoAdListener {
    LiveCompleteWinnerAdapter liveCompleteWinnerAdapter;
    ArrayList<LiveWinner_model>liveWinner_models=new ArrayList<>();
    RecyclerView recycler_winner;
    final Handler handler = new Handler();
    private CountDownTimer newtimer;
    SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_complete);
        sessionManager =new SessionManager(LiveCompleteActivity.this);

        recycler_winner=findViewById(R.id.recycler_winner);

        if (Connectivity.isConnected(LiveCompleteActivity.this)){
            getLiveWinner();
        }else {
            //Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        rewardAds();

//        liveWinner_models.add(0,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(1,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(2,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(3,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(4,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(5,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(6,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(7,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(8,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(9,new LiveWinner_model("ramesh","mukesh","pavan"));
//        liveWinner_models.add(10,new LiveWinner_model("ramesh","mukesh","pavan"));
//
//        Log.e("live_win_size", ""+liveWinner_models.size());

//        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(LiveCompleteActivity.this);
//        recycler_winner.setLayoutManager(mLayoutManger);
//        recycler_winner.setLayoutManager(new LinearLayoutManager(LiveCompleteActivity.this, RecyclerView.VERTICAL, false));
//
//
//        recycler_winner.getLayoutManager().smoothScrollToPosition(recycler_winner,new RecyclerView.State(), recycler_winner.getAdapter().getItemCount());
//        recycler_winner.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
//        recycler_winner.smoothScrollToPosition(liveWinner_models.size()-1);

//
    }


    private void getLiveWinner() {

        String eventId= sessionManager.getEventId();
        String user_id= AppPreference.getUser_Id(LiveCompleteActivity.this);
        String url= Base_Url.BaseUrl+Base_Url.get_live_game_winners;
        Log.e("winner_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("event_id",eventId)
                //.addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                       // progressDialog.dismiss();
                        Log.e("winner_responce = ", jsonObject.toString());

                        try {
                          //  progressDialog.dismiss();

                            String result = jsonObject.getString("result");
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                                for (int i=0; i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String username=jsonObject1.getString("username");
                                    String image=jsonObject1.getString("image");
                                    String price=jsonObject1.getString("price");

                                    liveWinner_models.add(i, new LiveWinner_model(username,image,price));
                                }
                                liveCompleteWinnerAdapter= new LiveCompleteWinnerAdapter( LiveCompleteActivity.this,liveWinner_models);
                                recycler_winner.setAdapter(liveCompleteWinnerAdapter);
                                recycler_winner.setLayoutManager(new CustomLayoutManager(LiveCompleteActivity.this));
                                recycler_winner.smoothScrollToPosition(liveWinner_models.size()-1);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onBackPressed();
                                    }
                                }, 30000);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                       // progressDialog.dismiss();
                        Log.e("error_winner_show", String.valueOf(anError));

                    }
                });
    }

    private void rewardAds() {
//        progressDialog = new ProgressDialog(LiveCompleteActivity.this,R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

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
      //  progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

    }

    @Override
    public void onRewardedVideoAdOpened() {
       // progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
       // progressDialog.dismiss();
        // Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
      //  progressDialog.dismiss();
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
       // progressDialog.dismiss();
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
       // progressDialog.dismiss();
       // Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        //progressDialog.dismiss();
       // Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();

        //rewardAds();
    }



    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        try {
            if(handler!=null){
                handler.removeCallbacksAndMessages(null);
            }
        }catch (Exception e){ }

        Intent intent=new Intent(LiveCompleteActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
}
