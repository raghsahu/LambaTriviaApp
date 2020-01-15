package com.trivia.lambatriviaapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Predict_play_activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.ContactModel;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.League_Data_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Show_Rewards_Adapter extends RecyclerView.Adapter<Show_Rewards_Adapter.ViewHolder> {

    private static final String TAG = "Show_Rewards_Adapter";
    private ArrayList<LeagueReward> leagueRewards;
    public Context context;
    View viewlike;

    public Show_Rewards_Adapter(Context activity, ArrayList<LeagueReward> league_rewards) {
        context = activity;
        leagueRewards = league_rewards;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_rank_name,tv_rank_amount;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_rank_name=viewlike.findViewById(R.id.tv_rank_name);
            tv_rank_amount=viewlike.findViewById(R.id.tv_rank_amount);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reward_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (leagueRewards.size() > 0) {

            viewHolder.tv_rank_name.setText(leagueRewards.get(position).getName());
            viewHolder.tv_rank_amount.setText(leagueRewards.get(position).getPrice());

        } else
        {
            //Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }

        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return leagueRewards.size();
    }


}
