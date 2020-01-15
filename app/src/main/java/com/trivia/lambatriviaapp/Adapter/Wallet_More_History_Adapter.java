package com.trivia.lambatriviaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Model_Class.Wallet_History.Wallet_Record;
import com.trivia.lambatriviaapp.R;

import java.util.List;

public class Wallet_More_History_Adapter extends RecyclerView.Adapter<Wallet_More_History_Adapter.ViewHolder> {

    private static final String TAG = "Wallet_More_History_Adapter";
    private List<Wallet_Record> wallet_records;
    public Context context;
    String Cash_or_Coin;
    View viewlike;

    public Wallet_More_History_Adapter(Context activity, List<Wallet_Record> wallet_records1, String cash_or_Coin) {
        context = activity;
        wallet_records = wallet_records1;
        this.Cash_or_Coin=cash_or_Coin;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_time,tv_details,tv_naira_sign;
        ImageView iv_coin,iv_plus_minus;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
             tv_time=viewlike.findViewById(R.id.tv_time);
             tv_details=viewlike.findViewById(R.id.tv_details);
            tv_naira_sign=viewlike.findViewById(R.id.tv_naira_sign);
            iv_coin=viewlike.findViewById(R.id.iv_coin);
            iv_plus_minus=viewlike.findViewById(R.id.iv_plus_minus);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallet_more_history_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Wallet_Record wallet_record = wallet_records.get(position);

        if (Cash_or_Coin.equalsIgnoreCase("Cash")){
            viewHolder.tv_naira_sign.setVisibility(View.VISIBLE);
            viewHolder.iv_coin.setVisibility(View.GONE);
        }else if (Cash_or_Coin.equalsIgnoreCase("Coin")){
            viewHolder.tv_naira_sign.setVisibility(View.GONE);
            viewHolder.iv_coin.setVisibility(View.VISIBLE);
        }

        if (wallet_record.getStatus().equalsIgnoreCase("debit")){
            viewHolder.iv_plus_minus.setImageResource(R.drawable.minus_sign);
        }else {
            viewHolder.iv_plus_minus.setImageResource(R.drawable.add_icon);
        }

        viewHolder.tv_details.setText(wallet_record.getDetail());
        viewHolder.tv_time.setText(wallet_record.getTime());

    }

    @Override
    public int getItemCount() {
        return wallet_records.size();
    }

}
