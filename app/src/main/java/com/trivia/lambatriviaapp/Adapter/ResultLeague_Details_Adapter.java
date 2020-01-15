package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Model_Class.League_game_model.MyCompleteQuestion;
import com.trivia.lambatriviaapp.R;

import java.util.List;

public class ResultLeague_Details_Adapter extends RecyclerView.Adapter<ResultLeague_Details_Adapter.ViewHolder> {

    private static final String TAG = "ResultLeague_Details_Adapter";
    private List<MyCompleteQuestion> myCompleteQuestions;
    public Context context;
    View viewlike;

    public ResultLeague_Details_Adapter(Context activity, List<MyCompleteQuestion> myCompleteQuestions1) {
        context = activity;
        myCompleteQuestions = myCompleteQuestions1;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_set_question,tv_ques_no,tv_option_1,tv_opt2,tv_opt3,
                tv_per1,tv_per2,tv_per3,tv_win_per_ques_coin;
        LinearLayout ll_opt_3;
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
            tv_win_per_ques_coin=viewlike.findViewById(R.id.tv_win_per_ques_coin);
            ll_opt_3=viewlike.findViewById(R.id.ll_opt_3);

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

            if (myCompleteQuestions.get(position).getType().equalsIgnoreCase("2")){
                viewHolder.ll_opt_3.setVisibility(View.GONE);
            }
            //****************if ques type equal 1 option*****
            if (myCompleteQuestions.get(position).getType().equalsIgnoreCase("1")){
                viewHolder.ll_opt_3.setVisibility(View.GONE);
                viewHolder.tv_per1.setVisibility(View.INVISIBLE);
                viewHolder.tv_per2.setVisibility(View.INVISIBLE);
                viewHolder.tv_option_1.setText("correct answer:"+" "+myCompleteQuestions.get(position).getCorrectAns());
                viewHolder.tv_opt2.setText("your answer:"+" "+myCompleteQuestions.get(position).getMyAns());
            }else {
                viewHolder.tv_option_1.setText(myCompleteQuestions.get(position).getOptionA());
                viewHolder.tv_opt2.setText(myCompleteQuestions.get(position).getOptionB());
                viewHolder.tv_opt3.setText(myCompleteQuestions.get(position).getOptionC());
            }

           //***********my wrong ans colorbg red otherwise green**

            //****************if ques type not equal 1*****
            if (!myCompleteQuestions.get(position).getType().equalsIgnoreCase("1")){

            //**********option A*****
           if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionA()))
           {
               if (myCompleteQuestions.get(position).getOptionA().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())){
                   viewHolder.tv_option_1.setBackgroundResource(R.drawable.green_bg);
                   viewHolder.tv_win_per_ques_coin.setText(myCompleteQuestions.get(position).getCoin());
               }else {
                   viewHolder.tv_option_1.setBackgroundResource(R.drawable.red_bg);
               }

           }else {
               if (myCompleteQuestions.get(position).getOptionA().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())) {
                   viewHolder.tv_option_1.setBackgroundResource(R.drawable.green_bg);
               }else {
                   viewHolder.tv_option_1.setBackgroundResource(R.drawable.gray_bg);
               }

           }

           //**option B****
            if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionB()))
            {
                if (myCompleteQuestions.get(position).getOptionB().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())){
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.green_bg);
                    viewHolder.tv_win_per_ques_coin.setText(myCompleteQuestions.get(position).getCoin());
                }else {
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.red_bg);
                }

            }else {
                if (myCompleteQuestions.get(position).getOptionB().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())) {
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.green_bg);
                }else {
                    viewHolder.tv_opt2.setBackgroundResource(R.drawable.gray_bg);
                }

            }

            //*****option C*******************
            if (myCompleteQuestions.get(position).getMyAns().equalsIgnoreCase(myCompleteQuestions.get(position).getOptionC()))
            {
                if (myCompleteQuestions.get(position).getOptionC().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())){
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.green_bg);
                    viewHolder.tv_win_per_ques_coin.setText(myCompleteQuestions.get(position).getCoin());
                }else {
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.red_bg);
                }

            }else {
                if (myCompleteQuestions.get(position).getOptionC().equalsIgnoreCase(myCompleteQuestions.get(position).getCorrectAns())) {
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.green_bg);
                }else {
                    viewHolder.tv_opt3.setBackgroundResource(R.drawable.gray_bg);
                }
            }

        }


               //******ques serial no.****
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
