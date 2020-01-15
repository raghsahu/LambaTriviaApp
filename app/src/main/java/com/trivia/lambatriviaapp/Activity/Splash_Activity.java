package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.LiveBannerVideoShow;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash_Activity extends AppCompatActivity {

    SessionManager sessionManager;
    Animation animZoomIn;
    TextView splash_text;
    ImageView splash_logo_image,splash_image_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        sessionManager=new SessionManager(Splash_Activity.this);
       // splash_text=findViewById(R.id.splash_text);
        splash_logo_image=findViewById(R.id.splash_logo_image);
        splash_image_bg=findViewById(R.id.splash_image_bg);

        printHashKey();

        try {
            //splashPlayer();
        } catch(Exception ex) {
        }

//        Glide.with(Splash_Activity.this)
//                .load(R.drawable.new_gif_splash)
//                //.error(R.drawable.error_image) // show error drawable if the image is not a gif
//                .into(splash_image_bg);



//        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoom_in);
//        splash_text.setVisibility(View.VISIBLE);
//        splash_text.startAnimation(animZoomIn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        try {

            //********animation top to middle and zoom
//        ViewAnimator
//                .animate(splash_logo_image)
//                .translationY(-1000, 0)
//                .alpha(0, 1)
//                .thenAnimate(splash_logo_image)
//                .scale(1f, 0.5f, 1f)
//                .accelerate()
//                .duration(1000)
//                .start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;

                if (sessionManager.isLoggedIn()){

                    intent=new Intent(Splash_Activity.this, MainActivity.class);
                    Animatoo.animateFade(Splash_Activity.this);

                }else {

                   // intent=new Intent(Splash_Activity.this, GetStarted_Activity.class);
                   // Animatoo.animateFade(Splash_Activity.this);

                    intent=new Intent(Splash_Activity.this, SignIn_SignUp_Activity.class);
                    Animatoo.animateFade(Splash_Activity.this);

               }
                startActivity(intent);
                finish();

            }
        }, 7000);

    }catch (Exception e){

    }


    }

    private void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.trivia.lambatriviaapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

//    private void splashPlayer() {
//        simpleVideoView = new VideoView(this);
//        setContentView(simpleVideoView);
//        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.splash_video);
//        simpleVideoView.setVideoURI(video);
//        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                //jumpMain(); //jump to the next Activity
//            }
//        });
//        simpleVideoView.start();
//
//    }


}

