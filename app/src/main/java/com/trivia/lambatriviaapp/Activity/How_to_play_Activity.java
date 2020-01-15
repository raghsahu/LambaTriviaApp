package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trivia.lambatriviaapp.Adapter.HowToPlayPagerAdapter;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;

public class How_to_play_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    //MyViewPager mViewPager;
    private HowToPlayPagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    LinearLayout llSkip;
    Button tv_next;
    TextView tv_skip;
    private Context mContext;
    int pos=0;
    int[] mResources = {R.drawable.white_ract_bg, R.drawable.white_ract_bg, R.drawable.white_ract_bg,R.drawable.white_ract_bg,R.drawable.white_ract_bg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_how_to_play_);

        mContext = How_to_play_Activity.this;

        mViewPager = findViewById(R.id.viewpager);
        tv_next = findViewById(R.id.tv_next);
        tv_skip = findViewById(R.id.tv_skip);

        viewPagerCountDots = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mAdapter = new HowToPlayPagerAdapter(How_to_play_Activity.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(How_to_play_Activity.this, Invite_friend_Activity.class);
//                startActivity(intent);
//                finish();
               // mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                //mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                mViewPager.setCurrentItem(getItem(+1), true);

            }
        });
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(How_to_play_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //***********************************************************************
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    15,
                    15
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {

        } else {

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // clickDone();

    }

//    public void clickDone() {
//        new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle(getString(R.string.title_dialog))
//                .setMessage(getString(R.string.msg_dialog))
//                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        Intent i = new Intent();
//                        i.setAction(Intent.ACTION_MAIN);
//                        i.addCategory(Intent.CATEGORY_HOME);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
//                        finish();
//                    }
//                })
//                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }




}
