package com.trivia.lambatriviaapp.Activity.League_Play_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_QuizStart_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlite_league_game;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trivia.lambatriviaapp.Activity.League_Play_Activity.Predict_play_activity.coin;

public class Play_league_Activity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_ques_no,tv_game_coin,tv_question,tv_ans1,tv_ans2,tv_ans3,
            tv_wallet_coin,tv_prediction;
    Button btn_play_next;
    LinearLayout ll_prediction,ll_card_quiz,ll_three_option;
    int intNextLevelGame=0;
    int intNext=0;
    SessionManager sessionManager;
    Sqlite_league_game sqliteLeagueGame;
    private Dialog dialog;
    String quiz_ques_id;
    LinearLayout ll_type1_et;
    EditText et_type1;
    Button btn_sub_type1;
    private Dialog Quitdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_play_);

        sessionManager=new SessionManager(Play_league_Activity.this);
        sqliteLeagueGame=new Sqlite_league_game(Play_league_Activity.this);

        try{
            Log.e("quiz_id_position", sessionManager.getQuizId());
            intNextLevelGame= Integer.parseInt(sessionManager.getQuizId());

        }catch (Exception e){

        }

        iv_back=findViewById(R.id.iv_back);
        tv_ques_no=findViewById(R.id.tv_ques_no);
        tv_game_coin=findViewById(R.id.tv_setcoin);
        tv_question=findViewById(R.id.tv_question);
        tv_ans1=findViewById(R.id.tv_ans1);
        tv_ans2=findViewById(R.id.tv_ans2);
        tv_ans3=findViewById(R.id.tv_ans3);
        tv_prediction=findViewById(R.id.tv_prediction);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        btn_play_next=findViewById(R.id.btn_play_next);
        ll_prediction=findViewById(R.id.ll_prediction);
        ll_card_quiz=findViewById(R.id.ll_card_quiz);
        ll_type1_et=findViewById(R.id.ll_type1_et);
        et_type1=findViewById(R.id.et_type1);
        btn_sub_type1=findViewById(R.id.btn_sub_type1);
        ll_three_option=findViewById(R.id.ll_three_option);

        try{
            LeagueNextQuestion();
            tv_wallet_coin.setText(coin);

        }catch (Exception e){
            Log.e("p_ques_error", e.toString());
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //*****************three option text******************************
        tv_ans1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                tv_ans1.setBackgroundResource(R.drawable.triang_gray);
                String tv_click_ans=tv_ans1.getText().toString();
                if (Connectivity.isConnected(Play_league_Activity.this)){
                    Submitting_Ans(tv_click_ans);
                }else {
                    Toast.makeText(Play_league_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }

                tv_prediction.setVisibility(View.INVISIBLE);
                ll_prediction.setVisibility(View.GONE);

                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);

            }
        });

        tv_ans2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                tv_ans2.setBackgroundResource(R.drawable.triang_gray);
                String tv_click_ans=tv_ans2.getText().toString();
                if (Connectivity.isConnected(Play_league_Activity.this)){
                    Submitting_Ans(tv_click_ans);
                }else {
                    Toast.makeText(Play_league_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
                tv_prediction.setVisibility(View.INVISIBLE);
                ll_prediction.setVisibility(View.GONE);

                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
            }
        });
        tv_ans3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                tv_ans3.setBackgroundResource(R.drawable.triang_gray);
                String tv_click_ans=tv_ans3.getText().toString();

                if (Connectivity.isConnected(Play_league_Activity.this)){
                    Submitting_Ans(tv_click_ans);
                }else {
                    Toast.makeText(Play_league_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
                tv_prediction.setVisibility(View.INVISIBLE);
                ll_prediction.setVisibility(View.GONE);

                tv_ans1.setClickable(false);
                tv_ans2.setClickable(false);
                tv_ans3.setClickable(false);
            }
        });

        btn_sub_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sub_type1.setBackgroundResource(R.drawable.tv_clickchange_bg);
                String ET_type1_ans=et_type1.getText().toString();

                if (ET_type1_ans.isEmpty()){
                    et_type1.setError("Please enter");
                }else {
                    if (Connectivity.isConnected(Play_league_Activity.this)){
                        Submitting_Ans(ET_type1_ans);
                    }else {
                        Toast.makeText(Play_league_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        btn_play_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                intNext++;
                LeagueNextQuestion();
            }
        });
      //***************************************************
    }

    private void Submitting_Ans(final String tv_click_ans) {
        Open_Submit_Dialog();

        String url=Base_Url.BaseUrl+Base_Url.League_Game_Play;
        String user_id=AppPreference.getUser_Id(Play_league_Activity.this);
        Log.e("submit_ans_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("event_id",sessionManager.getEventId())
                .addBodyParameter("question_id",quiz_ques_id)
                .addBodyParameter("answer",tv_click_ans)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        dialog.dismiss();
                        Log.e("submit_ans_response = ", jsonObject.toString());

                        try {
                            dialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){
                                dialog.dismiss();

                                btn_play_next.setVisibility(View.VISIBLE);
                                tv_ans1.setClickable(false);
                                tv_ans2.setClickable(false);
                                tv_ans3.setClickable(false);
                                btn_sub_type1.setClickable(false);

                            }else {
                               // tv_answer.setClickable(true);
                                dialog.dismiss();
                                btn_play_next.setVisibility(View.VISIBLE);//******remove karna hai
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        btn_play_next.setVisibility(View.VISIBLE);//*****remove karna hai
                        //tv_answer.setClickable(true);
                        Log.e("error_submit_show", anError.getMessage());


                    }
                });

    }

    private void Open_Submit_Dialog() {
        dialog = new Dialog(Play_league_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.league_submit_ans);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        dialog.getWindow().setAttributes(lp);

        try {
            if (!Play_league_Activity.this.isFinishing()){
                dialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }
    //**************************method next question *************
    private void LeagueNextQuestion() {
        try {
            Log.e("getAllQuestion_size", " "+sqliteLeagueGame.getAllLeague_quiz().size());
            if(sqliteLeagueGame.getAllLeague_quiz().size()>0){
                //  if (sqlitehelper.getAllQuestion_practice().get(intNext+1).getQuestion().length()!=0){
                if (sqliteLeagueGame.getAllLeague_quiz().get(intNext).getType().equalsIgnoreCase("2")){
                    ll_three_option.setVisibility(View.VISIBLE);
                    ll_type1_et.setVisibility(View.GONE);
                    tv_ans3.setVisibility(View.GONE);
                }else if (sqliteLeagueGame.getAllLeague_quiz().get(intNext).getType().equalsIgnoreCase("3")){
                    tv_ans3.setVisibility(View.VISIBLE);
                    ll_three_option.setVisibility(View.VISIBLE);
                    ll_type1_et.setVisibility(View.GONE);
                }else{
                    et_type1.setText(null);
                    ll_three_option.setVisibility(View.GONE);
                    ll_type1_et.setVisibility(View.VISIBLE);
                }

                btn_play_next.setVisibility(View.GONE);
                tv_prediction.setVisibility(View.VISIBLE);
                ll_prediction.setVisibility(View.VISIBLE);
                tv_ans1.setClickable(true);
                tv_ans2.setClickable(true);
                tv_ans3.setClickable(true);
                btn_sub_type1.setClickable(true);
                tv_ans1.setBackgroundResource(R.drawable.triang_light_blue);
                tv_ans2.setBackgroundResource(R.drawable.triang_light_blue);
                tv_ans3.setBackgroundResource(R.drawable.triang_light_blue);
                btn_sub_type1.setBackgroundResource(R.drawable.quiz_btn_blue);

                tv_game_coin.setText(sqliteLeagueGame.getAllLeague_quiz().get(intNext).getCoin());
                tv_question.setText(" "+sqliteLeagueGame.getAllLeague_quiz().get(intNext).getQuestion());
                tv_ans1.setText(sqliteLeagueGame.getAllLeague_quiz().get(intNext).getOptionA());
                tv_ans2.setText(sqliteLeagueGame.getAllLeague_quiz().get(intNext).getOptionB());
                tv_ans3.setText(sqliteLeagueGame.getAllLeague_quiz().get(intNext).getOptionC());
                tv_prediction.setText(sqliteLeagueGame.getAllLeague_quiz().get(intNext).getPredictionDescription());

                quiz_ques_id=sqliteLeagueGame.getAllLeague_quiz().get(intNext).getId();
                Log.e("quiz_ques_id", quiz_ques_id);
                try
                {
                    int sum=intNext+1;
                   // tv_ques_no.setText("Q"+ " "+sum);
                    tv_ques_no.setText("Q"+ " "+sqliteLeagueGame.getAllLeague_quiz().get(intNext).getQues_num().toString());
                }
                catch (NumberFormatException exception)
                {
                    Log.e("tv_ques_no_error", exception.toString());
                }

            }else {
                ll_card_quiz.setVisibility(View.INVISIBLE);
               // Toast.makeText(this, "no quiz found", Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            Log.e("complete_error", ""+e.toString());
            //ll_quiz_card.setVisibility(View.GONE);
            //NextQuizLevel_Dialog();
            Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(Play_league_Activity.this, League_complete_Activity.class);
            startActivity(intent);
            finish();

        }

    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        OpenQuitDialog();
    }

    private void OpenQuitDialog() {
        Quitdialog = new Dialog(Play_league_Activity.this);
        Quitdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Quitdialog.setCancelable(true);
        Quitdialog.setContentView(R.layout.quit_dialog_open);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button btn_quit_yes=Quitdialog.findViewById(R.id.btn_quit_yes);
        Button btn_quit_no=Quitdialog.findViewById(R.id.btn_quit_no);


        btn_quit_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intNext=0;
                intNextLevelGame=0;

                Quitdialog.dismiss();
                Intent intent=new Intent(Play_league_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        btn_quit_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quitdialog.dismiss();
            }
        });
        try {
            if (!Play_league_Activity.this.isFinishing()){
                Quitdialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }


    }
}
