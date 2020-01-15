package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.Live_Game_Model.LiveWinner_model;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.R;

import java.util.ArrayList;

public class LiveCompleteWinnerAdapter extends RecyclerView.Adapter<LiveCompleteWinnerAdapter.ViewHolder> {

    private static final String TAG = "LiveCompleteWinnerAdapter";
    private ArrayList<LiveWinner_model> liveWinner_models;
    public Context context;
    View viewlike;

    public LiveCompleteWinnerAdapter(Context activity, ArrayList<LiveWinner_model> liveWinner_models1) {
        context = activity;
        liveWinner_models = liveWinner_models1;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_user_id,tv_my_win_cash;
        ImageView iv_profile;
        LinearLayout ll_winner_list;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_user_id=viewlike.findViewById(R.id.tv_user_id);
            tv_my_win_cash=viewlike.findViewById(R.id.tv_my_win_cash);
            iv_profile=viewlike.findViewById(R.id.iv_profile);
            ll_winner_list=viewlike.findViewById(R.id.ll_winner_list);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_winner_recycle_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final LiveWinner_model liveWinner_model = liveWinner_models.get(position);

        viewHolder.tv_my_win_cash.setText(liveWinner_model.getPrice());
        viewHolder.tv_user_id.setText(liveWinner_model.getUsername());

        Log.e("livewinnersize", ""+liveWinner_models.size());
        Picasso.with(context)
                .load(Base_Url.MenuImageUrl+liveWinner_model.getImage())
                .placeholder(R.drawable.bear)
                .into(viewHolder.iv_profile);

//        Glide.with(context) // replace with 'this' if it's in activity
//                .load(R.drawable.progress_bar)
//                //.error(R.drawable.error_image) // show error drawable if the image is not a gif
//                .into(viewHolder.iv_news_image);

        if (position%2 == 0){
            viewHolder.ll_winner_list.setGravity(Gravity.LEFT);
        } else {
            viewHolder.ll_winner_list.setGravity(Gravity.RIGHT);
        }

    }


    @Override
    public int getItemCount() {
        return liveWinner_models.size();
    }
}
