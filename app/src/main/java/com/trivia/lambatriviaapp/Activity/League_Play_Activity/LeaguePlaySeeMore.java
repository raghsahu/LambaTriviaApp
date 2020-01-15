package com.trivia.lambatriviaapp.Activity.League_Play_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.trivia.lambatriviaapp.Adapter.LeagueViewMoreAdapter;
import com.trivia.lambatriviaapp.Fragments.CompletedFragment;
import com.trivia.lambatriviaapp.Fragments.PlayFragment;
import com.trivia.lambatriviaapp.Fragments.ReasultFragment;
import com.trivia.lambatriviaapp.R;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class LeaguePlaySeeMore extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    LeagueViewMoreAdapter leagueViewMoreAdapter;
    FrameLayout simpleFrameLayout;
    TabItem tabplay,tabcompleted,tabresults;
    ImageView iv_back;
    TextView tv_wallet_coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_play_see_more);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);

        tabplay = findViewById(R.id.tabplay);
        tabcompleted = findViewById(R.id.tabcompleted);
        tabresults = findViewById(R.id.tabresults);
        iv_back = findViewById(R.id.iv_back);
        tv_wallet_coin = findViewById(R.id.tv_wallet_coin);

        tv_wallet_coin.setText(total_coin);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        tabLayout.addTab(tabLayout.newTab().setText("play"));
//        tabLayout.addTab(tabLayout.newTab().setText("completed"));
//        tabLayout.addTab(tabLayout.newTab().setText("results"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        leagueViewMoreAdapter = new LeagueViewMoreAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(leagueViewMoreAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                        if (tab.getPosition() == 1) {

//                    tabLayout.setBackgroundColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                            R.color.colorAccent));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                                R.color.colorAccent));
//                    }
                        } else if (tab.getPosition() == 2) {

//                    tabLayout.setBackgroundColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                            android.R.color.darker_gray));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                                android.R.color.darker_gray));
//                    }
                        } else {
//                    tabLayout.setBackgroundColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                            R.color.colorPrimary));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(LeaguePlaySeeMore.this,
//                                R.color.colorPrimaryDark));
//                    }
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });



//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//// get the current selected tab's position and replace the fragment accordingly
//                Fragment fragment = null;
//                switch (tab.getPosition()) {
//                    case 0:
//                        fragment = new PlayFragment();
//                        break;
//                    case 1:
//                        fragment = new CompletedFragment();
//                        break;
//                    case 2:
//                        fragment = new ReasultFragment();
//                        break;
//                }
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.simpleFrameLayout, fragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
