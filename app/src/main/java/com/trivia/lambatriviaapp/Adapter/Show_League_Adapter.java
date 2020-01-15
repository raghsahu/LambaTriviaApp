package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.League_seemore_Result;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.League_seemore_complete;
import com.trivia.lambatriviaapp.Activity.League_Play_Activity.Predict_play_activity;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.ThreeSecondSplashActivity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.League_Data_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlite_league_game;
import com.trivia.lambatriviaapp.Session.SessionManager;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlitehelper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Show_League_Adapter extends RecyclerView.Adapter<Show_League_Adapter.ViewHolder> {

    private static final String TAG = "Show_League_Adapter";
    private List<League_Data_Model> league_data_modelArrayList=new ArrayList<League_Data_Model>();
    League_Data_Model league_data_model;
    public Context context;
    View viewlike;
    SessionManager sessionManager;
    String League_rules;
    Sqlite_league_game sqliteLeagueGame;
    public String  event_id;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_team_vs_team,tv_time_left,tv_coin,tv_total_played;
        LinearLayout ll_league_open;
        RelativeLayout ll_bg_image;
        Button btn_league_play;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_team_vs_team=viewlike.findViewById(R.id.tv_team_vs_team);
            tv_time_left=viewlike.findViewById(R.id.tv_time_left);
            tv_coin=viewlike.findViewById(R.id.tv_coin);
            ll_bg_image=viewlike.findViewById(R.id.ll_bg_image);
            tv_total_played=viewlike.findViewById(R.id.tv_total_player);
           // ll_league_open=viewlike.findViewById(R.id.ll_league_open);
            btn_league_play=viewlike.findViewById(R.id.btn_league_play);


        }
    }

    public Show_League_Adapter(Context mContext, List<League_Data_Model> league_data_modelList, String rules) {
        context = mContext;
        league_data_modelArrayList = league_data_modelList;
        League_rules=rules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.league_game_item, parent, false);

        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);
        if (league_data_modelArrayList.size() > 0) {
            league_data_model = league_data_modelArrayList.get(position);

            viewHolder.tv_team_vs_team.setText(league_data_model.getGameTitle());
            viewHolder.tv_coin.setText(league_data_model.getTotalCoin());
            viewHolder.tv_total_played.setText(league_data_model.getPlayed().toString()+" "+"played");

            if (!league_data_model.getStatus().isEmpty() && !(league_data_model.getStatus() ==null)){
                viewHolder.btn_league_play.setText(league_data_model.getStatus());
            }

            Picasso.with(context).load(Base_Url.league_game_image_path+league_data_model.getImage())
                    .placeholder(R.drawable.league_place_holder_img)
                    .into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    viewHolder.ll_bg_image.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

            ///*******************************************

            String toyBornTime = league_data_modelArrayList.get(position).getStartDateTime();
            //String toyBornTime = "2019-09-04 17:09:00";
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");

            try {
                Date startDate = dateFormat.parse(toyBornTime);
                System.out.println(startDate);
                Log.e("old_date", startDate.toString());

                Date currentDate = new Date();

                long diff = startDate.getTime() - currentDate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                if (startDate.after(currentDate)) {

                    Log.e("oldDate", "is previous date");
                    Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                            + " hours: " + hours + " days: " + days);

                    if (hours>0){
                        viewHolder.tv_time_left.setText((int) hours + " "+ "hour left");
                    }else {
                        if (minutes>0){
                            viewHolder.tv_time_left.setText((int) minutes + " "+ "min left");
                            Log.e("min_left", ""+minutes);
                        }else {
                            viewHolder.tv_time_left.setText(" ");
                        }
                    }

                }else {
                    Log.e("min_left1", ""+minutes);

                    if (minutes>0){
                        viewHolder.tv_time_left.setText((int) minutes + " "+ "min left");
                        Log.e("min_left2", ""+minutes);
                    }else {
                        viewHolder.tv_time_left.setText(" ");
                    }

                }

            } catch (ParseException e) {

                e.printStackTrace();
            }



        } else
        {
            //Toast.makeText(context, "No leage found", Toast.LENGTH_SHORT).show();
        }


        viewHolder.btn_league_play.setTag(viewHolder);
        viewHolder.pos = position;

        viewHolder.btn_league_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager=new SessionManager(context);
                sqliteLeagueGame=new Sqlite_league_game(context);
                event_id=league_data_modelArrayList.get(position).getId();
                sessionManager.setQuizId(String.valueOf(position));
                sessionManager.setEventId(event_id);
                Log.e("quiz_position", ""+position);
                //**************************************
                if (viewHolder.btn_league_play.getText().toString().equalsIgnoreCase("CROWDSCORE")){
                    String quiz_comp_id=league_data_modelArrayList.get(position).getId();
                    Intent intent =new Intent(context, League_seemore_complete.class);
                    intent.putExtra("quiz_comp_id", quiz_comp_id);
                    context.startActivity(intent);
                }else if (viewHolder.btn_league_play.getText().toString().equalsIgnoreCase("RESULT")){
                    String quiz_comp_id=league_data_modelArrayList.get(position).getId();
                    Intent intent =new Intent(context, League_seemore_Result.class);
                    intent.putExtra("quiz_comp_id", quiz_comp_id);
                    context.startActivity(intent);
                }else {

                    //**********************
                    try{
                        sqliteLeagueGame.delete_league_quiz();
                        //Log.e("quiz_id_adap", quiz_id);
                        Log.e("quizDataCount", ""+league_data_modelArrayList.get(position).getLeague_questions().size());
                        Log.e("sqliteCount", ""+sqliteLeagueGame.getAllLeague_quiz().size());

                        if (sqliteLeagueGame.getAllLeague_quiz().size()==0){

                            for (int i=0;i<league_data_modelArrayList.get(position).getLeague_questions().size();i++){
                                Log.e("quiz_question",""+ league_data_modelArrayList.get(position).getLeague_questions().get(i).getQuestion());
                                // Log.e("quiz_ques_ans",""+ league_data_modelArrayList.get(position).getLeague_questions().get(i).getCorrectAns());

                                sqliteLeagueGame.addLeague_quiz(league_data_modelArrayList.get(position).getLeague_questions());

                            }
                        }else {
                            sqliteLeagueGame.delete_league_quiz();
                            Log.e("sqlCount_delete", ""+sqliteLeagueGame.getAllLeague_quiz().size());
                            for (int i=0;i<league_data_modelArrayList.get(position).getLeague_questions().size();i++){
                                Log.e("quiz_question",""+ league_data_modelArrayList.get(position).getLeague_questions().get(i).getQuestion());
                                //Log.e("quiz_ques_ans",""+ league_data_modelArrayList.get(position).getLeague_questions().get(i).getCorrectAns());

                                sqliteLeagueGame.addLeague_quiz(league_data_modelArrayList.get(position).getLeague_questions());

                            }
                            Log.e("sql_updateCount", ""+sqliteLeagueGame.getAllLeague_quiz().size());
                        }

                        Log.e("AfterInsert_sql_Count", ""+sqliteLeagueGame.getAllLeague_quiz().size());

                    }catch (Exception e){
                        Log.e("add_sql_error",e.getMessage());
                    }
//************************************after save db go to intent*************
                    if((sqliteLeagueGame.getAllLeague_quiz().size()>0)){

                        if (viewHolder.btn_league_play.getText().toString().equalsIgnoreCase("RESUME")){
                            Intent intent=new Intent(context, ThreeSecondSplashActivity.class);
                            intent.putExtra("startgame", "league");
                            context.startActivity(intent);

                        }else {
                            Intent intent=new Intent(context, Predict_play_activity.class);
                            intent.putExtra("league_coin", league_data_modelArrayList.get(position).getTotalCoin());
                            intent.putExtra("league_team", league_data_modelArrayList.get(position).getGameTitle());
                            intent.putExtra("league_image", league_data_modelArrayList.get(position).getImage());
                            intent.putExtra("league_rules", League_rules);
                            intent.putExtra("league_rewards", league_data_modelArrayList.get(position).getRewards());
                            intent.putExtra("league_left_time", viewHolder.tv_time_left.getText().toString());
                            intent.putExtra("start_date_time", league_data_modelArrayList.get(position).getStartDateTime());
                            intent.putExtra("naira_prize", league_data_modelArrayList.get(position).getNaira_prize());
                            context.startActivity(intent);
                        }
                    }else {
                        Toast.makeText(context, "No game available", Toast.LENGTH_SHORT).show();
                    }





                }






            }
        });

    }

    @Override
    public int getItemCount() {
        return league_data_modelArrayList.size();
    }


}
