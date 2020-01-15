package com.trivia.lambatriviaapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.Wallet_History.WithdrawAmountActivity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.BankDetailsModel;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.WithdrawHistoryModel;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raghvendra Sahu on 26/11/2019.
 */
public class WithdrawHistoryAdapter extends RecyclerView.Adapter<WithdrawHistoryAdapter.ViewHolder> {

    private static final String TAG = "NewsBlogAdapter";
    private ArrayList<WithdrawHistoryModel> news_blog_models;
    public Context context;
    View viewlike;

    public WithdrawHistoryAdapter(Context activity, ArrayList<WithdrawHistoryModel> news_blog_models1) {
        context = activity;
        news_blog_models = news_blog_models1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_type,tv_status,tv_date,tv_amount,tv_phone_nmbr;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_type=viewlike.findViewById(R.id.tv_type);
            tv_status=viewlike.findViewById(R.id.tv_status);
            tv_date=viewlike.findViewById(R.id.tv_date);
            tv_amount=viewlike.findViewById(R.id.tv_amount);
            tv_phone_nmbr=viewlike.findViewById(R.id.tv_phone_nmbr);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.withdraw_history_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final WithdrawHistoryModel newsBlogModel = news_blog_models.get(position);

        viewHolder.tv_type.setText(newsBlogModel.getPayment_type());
        viewHolder.tv_status.setText(newsBlogModel.getStatus());
        viewHolder.tv_date.setText(newsBlogModel.getDate());
        viewHolder.tv_amount.setText(newsBlogModel.getAmount());
        viewHolder.tv_phone_nmbr.setText(newsBlogModel.getPhone_numer());

    }

    @Override
    public int getItemCount() {
        return news_blog_models.size();
    }
}
