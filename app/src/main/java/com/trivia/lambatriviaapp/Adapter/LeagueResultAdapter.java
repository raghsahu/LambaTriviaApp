package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.League_complete_Activity;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.League_seemore_Result;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Predict_play_activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.League_Data_Model;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Result;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlite_league_game;
import com.trivia.lambatriviaapp.Session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeagueResultAdapter extends RecyclerView.Adapter<LeagueResultAdapter.ViewHolder> {

        private static final String TAG = "LeagueResultAdapter";
        private List<Show_League_Result> show_league_results=new ArrayList<Show_League_Result>();
        Show_League_Result showLeagueResult;
        public Context context;
        View viewlike;
        SessionManager sessionManager;


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_total_naira,tv_team_vs_team,tv_coin,tv_league_status;
            LinearLayout ll_leag_comp_details;
            ImageView iv_leag_more_img;
            TextView btn_league_play;
            int pos;

            public ViewHolder(View view) {
                super(view);
                viewlike = view;
                tv_team_vs_team=viewlike.findViewById(R.id.tv_team_vs_team);
                tv_coin=viewlike.findViewById(R.id.tv_coin);
                iv_leag_more_img=viewlike.findViewById(R.id.iv_leag_more_img);
                tv_total_naira=viewlike.findViewById(R.id.tv_total_naira);
                ll_leag_comp_details=viewlike.findViewById(R.id.ll_leag_comp_details);
                btn_league_play=viewlike.findViewById(R.id.btn_league_play);
                tv_league_status=viewlike.findViewById(R.id.tv_league_status);


            }
        }

    public LeagueResultAdapter(Context mContext, List<Show_League_Result> show_league_results1) {
            context = mContext;
            show_league_results = show_league_results1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.league_result_adapter, parent, false);

            return new ViewHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

            if (show_league_results.size() > 0) {
                showLeagueResult = show_league_results.get(position);

                viewHolder.tv_team_vs_team.setText(showLeagueResult.getGame_title());
                viewHolder.tv_coin.setText(showLeagueResult.getTotal_coin());
                viewHolder.tv_total_naira.setText(showLeagueResult.getNaira_prize());
                viewHolder.tv_league_status.setText("Result");

//            Picasso.with(context).load(Base_Url.league_game_image_path+league_data_model.getImage())
//                    .placeholder(R.drawable.league_place_holder_img)
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                            viewHolder.ll_bg_image.setBackground(new BitmapDrawable(bitmap));
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });

                ///*******************************************

                Picasso.with(context)
                        .load(Base_Url.league_game_image_path+showLeagueResult.getImage())
                        .placeholder(R.drawable.league_place_holder_img)
                        .into(viewHolder.iv_leag_more_img);




            } else
            {
                //Toast.makeText(context, "No leage found", Toast.LENGTH_SHORT).show();
            }

            viewHolder.ll_leag_comp_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String quiz_comp_id=show_league_results.get(position).getId();
                    Intent intent =new Intent(context, League_seemore_Result.class);
                    intent.putExtra("quiz_comp_id", quiz_comp_id);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return show_league_results.size();
        }



}
