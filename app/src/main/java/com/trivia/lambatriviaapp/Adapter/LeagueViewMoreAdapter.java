package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.trivia.lambatriviaapp.Activity.League_Play_Activity.LeaguePlaySeeMore;
import com.trivia.lambatriviaapp.Fragments.CompletedFragment;
import com.trivia.lambatriviaapp.Fragments.PlayFragment;
import com.trivia.lambatriviaapp.Fragments.ReasultFragment;

public class LeagueViewMoreAdapter extends FragmentPagerAdapter {

    Context myContext;
    int totalTabs;

    public LeagueViewMoreAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
       // myContext = leaguePlaySeeMore;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PlayFragment playFragment = new PlayFragment();
                return playFragment;
            case 1:
                CompletedFragment completedFragment = new CompletedFragment();
                return completedFragment;
            case 2:
                ReasultFragment reasultFragment = new ReasultFragment();
                return reasultFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}
