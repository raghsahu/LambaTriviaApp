package com.trivia.lambatriviaapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.AdMetadataListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.LiveBannerVideoShow;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_QuizStart_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.R;

public class FullScreenVideoClip_Activity extends AppCompatActivity implements RewardedVideoAdListener {

    ImageView iv_back;
    VideoView myVideo;
    RewardedAd rewardedAd;
    String TAG = "Reward_ads_videoclip";

     RewardedVideoAd rewardedVideoAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video_clip_);

        iv_back = findViewById(R.id.iv_back);
        myVideo = findViewById(R.id.myVideo);

        //****************mobile reward ads
        MobileAds.initialize(FullScreenVideoClip_Activity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        createAndLoadRewardedAd();//****reward ads

        rewardedVideoAd = new RewardedVideoAd() {
            @Override
            public void loadAd(String s, AdRequest adRequest) {

            }

            @Override
            public void loadAd(String s, PublisherAdRequest publisherAdRequest) {

            }

            @Override
            public boolean isLoaded() {
                return false;
            }

            @Override
            public void show() {

            }

            @Override
            public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {

            }

            @Override
            public void setAdMetadataListener(AdMetadataListener adMetadataListener) {

            }

            @Override
            public Bundle getAdMetadata() {
                return null;
            }

            @Override
            public void setUserId(String s) {

            }

            @Override
            public RewardedVideoAdListener getRewardedVideoAdListener() {
                return null;
            }

            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public void pause() {

            }

            @Override
            public void pause(Context context) {

            }

            @Override
            public void resume() {

            }

            @Override
            public void resume(Context context) {

            }

            @Override
            public void destroy() {

            }

            @Override
            public void destroy(Context context) {

            }

            @Override
            public String getMediationAdapterClassName() {
                return null;
            }

            @Override
            public void setImmersiveMode(boolean b) {

            }

            @Override
            public void setCustomData(String s) {

            }

            @Override
            public String getCustomData() {
                return null;
            }
        };

        //**********reward video ads
        RewardedVideoAd mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideo();
        //******************************
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getIntent() != null) {
            String video_url = getIntent().getStringExtra("video_url");

            if (Connectivity.isConnected(FullScreenVideoClip_Activity.this)) {
                play_Video_Clip(Base_Url.video_clip + video_url);
            } else {
                Toast.makeText(FullScreenVideoClip_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void loadRewardedVideo() {
        AdRequest request = new AdRequest.Builder().build();
        rewardedVideoAd.loadAd(getString(R.string.lamba_rewarded_ad), request);
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        }
    }

    public RewardedAd createAndLoadRewardedAd() {
        rewardedAd = new RewardedAd(this, getString(R.string.lamba_rewarded_ad));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
//    @Override
//    public void onRewardedAdClosed() {
//        this.rewardedAd = createAndLoadRewardedAd();
//    }


    private void play_Video_Clip(String video_url) {
        MediaController vidControl = new MediaController(FullScreenVideoClip_Activity.this);
        Uri vidUri = Uri.parse(video_url);
        myVideo.setVideoURI(vidUri);
        myVideo.start();
        vidControl.setAnchorView(myVideo);
        myVideo.setMediaController(vidControl);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            myVideo.stopPlayback();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            myVideo.stopPlayback();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            myVideo.stopPlayback();
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            myVideo.pause();
        } catch (Exception e) {

        }
    }

    //************************REWARD video ads listener***
    @Override
    public void onRewardedVideoAdLoaded() {
        Log.e(TAG, "Rewarded: onRewardedVideoAdLoaded");
        try {
            if (rewardedVideoAd.isLoaded()) {
                rewardedVideoAd.show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.i(TAG, "Rewarded: onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.i(TAG, "Rewarded: onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideo();
        Log.i(TAG, "Rewarded: onRewardedVideoAdClosed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.i(TAG, "Rewarded:  onRewarded! currency: " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount());

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.i(TAG, "Rewarded: onRewardedVideoAdLeftApplication ");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.i(TAG, "Rewarded: onRewardedVideoAdFailedToLoad: " + i);
    }

    @Override
    public void onRewardedVideoCompleted() {

    }


}
