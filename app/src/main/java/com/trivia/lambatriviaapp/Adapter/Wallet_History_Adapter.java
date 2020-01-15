package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Model_Class.Wallet_History.Wallet_History_Data;
import com.trivia.lambatriviaapp.R;

import java.util.List;

public class Wallet_History_Adapter extends RecyclerView.Adapter<Wallet_History_Adapter.ViewHolder> {

    private static final String TAG = "Wallet_History_Adapter";
    private List<Wallet_History_Data> wallet_history_models;
    public Context context;
    Wallet_More_History_Adapter wallet_more_history_adapter;
    String Cash_or_Coin;
    View viewlike;

    public Wallet_History_Adapter(Context activity, List<Wallet_History_Data> wallet_history_models1, String cash_or_Coin) {
        context = activity;
        wallet_history_models = wallet_history_models1;
        this.Cash_or_Coin=cash_or_Coin;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date;
        RecyclerView recycler_more_history;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_date=viewlike.findViewById(R.id.tv_date);
            recycler_more_history=viewlike.findViewById(R.id.recycler_more_history);
           // tv_time=viewlike.findViewById(R.id.tv_time);
           // tv_details=viewlike.findViewById(R.id.tv_details);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_transaction_history_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
       final Wallet_History_Data wallet_history_model = wallet_history_models.get(position);

        viewHolder.tv_date.setText(wallet_history_model.getDateFormatted());
       // viewHolder.tv_details.setText(wallet_history_model.getDetail());
        //viewHolder.tv_time.setText(wallet_history_model.getTime());

        wallet_more_history_adapter= new Wallet_More_History_Adapter( context,wallet_history_models.get(position).getRecord(),Cash_or_Coin);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(context);
        viewHolder.recycler_more_history.setLayoutManager(mLayoutManger);
        viewHolder.recycler_more_history.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        viewHolder.recycler_more_history.setAdapter(wallet_more_history_adapter);


    }

    @Override
    public int getItemCount() {
        return wallet_history_models.size();
    }


}
