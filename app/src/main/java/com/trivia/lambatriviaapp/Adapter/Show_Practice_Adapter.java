package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity.Practice_quizActivity;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.QuizDataCount;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.SessionManager;
import com.trivia.lambatriviaapp.Session.SQLiteDB.Sqlitehelper;

import java.util.ArrayList;
import java.util.List;

public class Show_Practice_Adapter extends RecyclerView.Adapter<Show_Practice_Adapter.ViewHolder> {

        private static final String TAG = "Show_Practice_Adapter";
        private List<QuizDataCount> quizDataCountArrayList=new ArrayList<QuizDataCount>();
        QuizDataCount quizDataCount;
        public Context context;
        View viewlike;
        SessionManager sessionManager;
        Sqlitehelper sqlitehelper;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_pr_quiz,tv_quiz_total_coin;
            LinearLayout ll_pr_quiz;
            int pos;

            public ViewHolder(View view) {
                super(view);
                viewlike = view;
                tv_pr_quiz=viewlike.findViewById(R.id.tv_pr_quiz);
                tv_quiz_total_coin=viewlike.findViewById(R.id.tv_quiz_total_coin);
                ll_pr_quiz=viewlike.findViewById(R.id.ll_pr_quiz);

            }
        }

    public Show_Practice_Adapter(Context mContext, List<QuizDataCount> quizDataCountList) {
            context = mContext;
            quizDataCountArrayList = quizDataCountList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.show_practice_item, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

            if (quizDataCountArrayList.size() > 0) {
                quizDataCount = quizDataCountArrayList.get(position);

                viewHolder.tv_pr_quiz.setText(quizDataCount.getName());
                viewHolder.tv_quiz_total_coin.setText(quizDataCount.getTotalCoin());

            } else
            {
                Toast.makeText(context, "No practice quiz found", Toast.LENGTH_SHORT).show();
            }


            viewHolder.ll_pr_quiz.setTag(viewHolder);
            viewHolder.pos = position;

            viewHolder.ll_pr_quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager=new SessionManager(context);
                    sqlitehelper=new Sqlitehelper(context);
                    String quiz_id=quizDataCountArrayList.get(position).getId();
                    sessionManager.setQuizId(String.valueOf(position));
                    Log.e("quiz_position", ""+position);
                    try{
                        sqlitehelper.deleteQuestion_practice();
                        Log.e("quiz_id_adap", quiz_id);
                        Log.e("quizDataCount", ""+quizDataCountArrayList.get(position).getQuestions().size());
                        Log.e("sqliteCount", ""+sqlitehelper.getAllQuestion_practice().size());

                        if (sqlitehelper.getAllQuestion_practice().size()==0){

                            for (int i=0;i<quizDataCountArrayList.get(position).getQuestions().size();i++){
                                Log.e("quiz_question",""+ quizDataCountArrayList.get(position).getQuestions().get(i).getQuestion());
                                Log.e("quiz_ques_ans",""+ quizDataCountArrayList.get(position).getQuestions().get(i).getCorrectAns());

                                sqlitehelper.addPracticeQuistion(quizDataCountArrayList.get(position).getQuestions());

                            }
                        }else {
                            Log.e("sql_update", "update");
                            sqlitehelper.deleteQuestion_practice();
                            Log.e("sqlCount_delete", ""+sqlitehelper.getAllQuestion_practice().size());
                            for (int i=0;i<quizDataCountArrayList.get(position).getQuestions().size();i++){
                                Log.e("quiz_question",""+ quizDataCountArrayList.get(position).getQuestions().get(i).getQuestion());
                                Log.e("quiz_ques_ans",""+ quizDataCountArrayList.get(position).getQuestions().get(i).getCorrectAns());

                                sqlitehelper.addPracticeQuistion(quizDataCountArrayList.get(position).getQuestions());
                                // sqlitehelper.updateQuestion_practice(quizDataCountArrayList.get(position).getQuestions());
                            }
                            Log.e("sql_updateCount", ""+sqlitehelper.getAllQuestion_practice().size());
                        }

                        Log.e("AfterInser_sql_Count", ""+sqlitehelper.getAllQuestion_practice().size());

                    }catch (Exception e){
                        Log.e("add_sql_error",e.getMessage());
                    }

                    Intent intent=new Intent(context, Practice_quizActivity.class);
                    intent.putExtra("quiz_coin", quizDataCount.getTotalCoin());
                    context.startActivity(intent);
                  //  ((Activity)context).finish();
                   // Toast.makeText(context, "quiz_id "+quiz_id, Toast.LENGTH_SHORT).show();

                }
            });


        }



        @Override
        public int getItemCount() {
            return quizDataCountArrayList.size();
        }


    }
