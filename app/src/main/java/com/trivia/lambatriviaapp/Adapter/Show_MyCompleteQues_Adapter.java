package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Model_Class.League_game_model.LeagueReward;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.MyCompleteQuestion;
import com.trivia.lambatriviaapp.R;

import java.util.ArrayList;
import java.util.List;

public class Show_MyCompleteQues_Adapter extends RecyclerView.Adapter<Show_MyCompleteQues_Adapter.ViewHolder> {

    private static final String TAG = "Show_MyCompleteQues_Adapter";
    private List<MyCompleteQuestion> myCompleteQuestions;
    public Context context;
    View viewlike;

    public Show_MyCompleteQues_Adapter(Context activity, List<MyCompleteQuestion> myCompleteQuestions1) {
        context = activity;
        myCompleteQuestions = myCompleteQuestions1;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_set_question,tv_ques_no,tv_option_1,tv_opt2,tv_opt3,
                tv_per1,tv_per2,tv_per3;
        LinearLayout ll_opt_3,ll_opt2;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_set_question=viewlike.findViewById(R.id.tv_set_question);
            tv_ques_no=viewlike.findViewById(R.id.tv_ques_no);
            tv_option_1=viewlike.findViewById(R.id.tv_option_1);
            tv_opt2=viewlike.findViewById(R.id.tv_opt2);
            tv_opt3=viewlike.findViewById(R.id.tv_opt3);
            tv_per1=viewlike.findViewById(R.id.tv_per1);
            tv_per2=viewlike.findViewById(R.id.tv_per2);
            tv_per3=viewlike.findViewById(R.id.tv_per3);
            ll_opt_3=viewlike.findViewById(R.id.ll_opt_3);
            ll_opt2=viewlike.findViewById(R.id.ll_opt2);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.league_crowdscore_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (myCompleteQuestions.size() > 0) {

            //viewHolder.tv_ques_no.setText(myCompleteQuestions.get(position).getName());
            viewHolder.tv_set_question.setText(myCompleteQuestions.get(position).getQuestion());
            viewHolder.tv_per1.setText(myCompleteQuestions.get(position).getOptionAPer().toString()+"%");
           viewHolder.tv_per2.setText(myCompleteQuestions.get(position).getOptionBPer().toString()+"%");
            viewHolder.tv_per3.setText(myCompleteQuestions.get(position).getOptionCPer().toString()+"%");

            //**********if type 2***then option 3 gone**
            if (myCompleteQuestions.get(position).getType().equalsIgnoreCase("2")){
                viewHolder.ll_opt_3.setVisibility(View.GONE);
            }

            //******if type 1 no need to show otption a, b, c****
            if (myCompleteQuestions.get(position).getType().equalsIgnoreCase("1")){
                viewHolder.ll_opt_3.setVisibility(View.GONE);
                viewHolder.ll_opt2.setVisibility(View.GONE);
                viewHolder.tv_per1.setVisibility(View.INVISIBLE);
                viewHolder.tv_option_1.setBackgroundResource(R.drawable.bg_bg);
                viewHolder.tv_option_1.setText("your answer:"+" "+myCompleteQuestions.get(position).getMyAns());
            }else {
                viewHolder.tv_option_1.setText(myCompleteQuestions.get(position).getOptionA());
                viewHolder.tv_opt2.setText(myCompleteQuestions.get(position).getOptionB());
                viewHolder.tv_opt3.setText(myCompleteQuestions.get(position).getOptionC());
            }


            //***************** your ans colorbg blue****
            if (!myCompleteQuestions.get(position).getType().equalsIgnoreCase("1")){

                if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionA())){
                    //viewHolder.tv_option_1.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.tv_option_1.setBackgroundResource(R.drawable.color_border);
                }else{
                    viewHolder.tv_option_1.setBackgroundResource(R.drawable.bg_bg);
                }

                if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionB())){
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.color_border);
                }else {
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.bg_bg);
                }

                if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionC())){
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.color_border);
                }else {
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.bg_bg);
                }

            }


            int sum=position+1;
            viewHolder.tv_ques_no.setText("Q"+ " "+sum);

        } else
        {
            //Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }

        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return myCompleteQuestions.size();
    }



}
