package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Predict_play_activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.LeaderBoard_Model;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.R;

import java.util.ArrayList;

public class ShowLeaderAdapter extends RecyclerView.Adapter<ShowLeaderAdapter.ViewHolder> {

    private static final String TAG = "ShowLeaderAdapter";
    private ArrayList<LeaderBoard_Model> leaderBoard_models;
    public Context context;
    View viewlike;

    public ShowLeaderAdapter(FragmentActivity activity, ArrayList<LeaderBoard_Model> leaderBoardModelArrayList) {
        context = activity;
        leaderBoard_models = leaderBoardModelArrayList;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_username,tv_serial,tv_naira;
        ImageView iv_profile;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            iv_profile=viewlike.findViewById(R.id.iv_profile);
            tv_username=viewlike.findViewById(R.id.tv_username);
            tv_serial=viewlike.findViewById(R.id.tv_serial);
            tv_naira=viewlike.findViewById(R.id.tv_naira);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);
//
//        if (leaderBoard_models.size() > 0) {
            LeaderBoard_Model leaderBoard_model = leaderBoard_models.get(position);

            viewHolder.tv_username.setText(leaderBoard_model.getUsername());
            viewHolder.tv_naira.setText(leaderBoard_model.getTotal_naira());

        Picasso.with(context)
        .load(Base_Url.MenuImageUrl+leaderBoard_model.getImage())
        .into(viewHolder.iv_profile);


        try
        {
            int sum=position+1;
            viewHolder.tv_serial.setText(""+sum);
        }
        catch (NumberFormatException exception)
        {
            Log.e("tv_ques_no_error", exception.toString());
        }

//        } else
//        {
//            //Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
//        }

        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return leaderBoard_models.size();
    }


}
