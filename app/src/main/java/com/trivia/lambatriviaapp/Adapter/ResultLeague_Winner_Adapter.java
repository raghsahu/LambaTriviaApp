package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.MyCompleteQuestion;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.WinnerListLeague;
import com.trivia.lambatriviaapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultLeague_Winner_Adapter extends RecyclerView.Adapter<ResultLeague_Winner_Adapter.ViewHolder> {

    private static final String TAG = "ResultLeague_Winner_Adapter";
    private List<WinnerListLeague> winnerListLeagueList;
    public Context context;
    View viewlike;

    public ResultLeague_Winner_Adapter(Context activity, List<WinnerListLeague> winnerListLeagues) {
        context = activity;
        winnerListLeagueList = winnerListLeagues;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_serial,tv_username,tv_naira;
        CircleImageView iv_profile;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_serial=viewlike.findViewById(R.id.tv_serial);
            tv_username=viewlike.findViewById(R.id.tv_username);
            tv_naira=viewlike.findViewById(R.id.tv_naira);
            iv_profile=viewlike.findViewById(R.id.iv_profile);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_winner_league_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (winnerListLeagueList.size() > 0) {

            viewHolder.tv_serial.setText(winnerListLeagueList.get(position).getRank().toString());
            viewHolder.tv_username.setText(winnerListLeagueList.get(position).getUsername());
            viewHolder.tv_naira.setText(winnerListLeagueList.get(position).getPrice());

            Picasso.with(context)
                    .load(Base_Url.MenuImageUrl+winnerListLeagueList.get(position).getImage())
                    .placeholder(R.drawable.bear)
                    .into(viewHolder.iv_profile);

        } else
        {
            //Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }

        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return winnerListLeagueList.size();
    }
}
