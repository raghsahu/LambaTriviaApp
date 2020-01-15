package com.trivia.lambatriviaapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MoneyToCoinRate;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoneyToCoinRateAdapter extends RecyclerView.Adapter<MoneyToCoinRateAdapter.ViewHolder> {

    private static final String TAG = "MoneyToCoinRateAdapter";
    private ArrayList<MoneyToCoinRate> moneyToCoinRates;
    public Context context;
    View viewlike;
    private int lastSelectedPosition = -1;
    String radio_id;
     AdapterCallback mAdapterCallback;

    public MoneyToCoinRateAdapter(Context activity, ArrayList<MoneyToCoinRate> moneyToCoinRates1) {
        context = activity;
        moneyToCoinRates = moneyToCoinRates1;

    }

//    public MoneyToCoinRateAdapter(Context context) {
//        try {
//            this.mAdapterCallback = ((AdapterCallback) context);
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Activity must implement AdapterCallback.");
//        }
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_naira_rate,tv_coin_rate;
        RadioButton radio_btn;
        LinearLayout ll_news_blog;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_naira_rate=viewlike.findViewById(R.id.tv_naira_rate);
            tv_coin_rate=viewlike.findViewById(R.id.tv_coin_rate);
            radio_btn=viewlike.findViewById(R.id.radio_btn);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.money_to_coin_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final MoneyToCoinRate moneyToCoinRate = moneyToCoinRates.get(position);

        viewHolder.tv_naira_rate.setText(moneyToCoinRate.getNaira());
        viewHolder.tv_coin_rate.setText(moneyToCoinRate.getCoin());

        viewHolder.radio_btn.setChecked(lastSelectedPosition == position);

        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

        viewHolder.radio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               radio_id=moneyToCoinRate.getId();
                lastSelectedPosition = viewHolder.getAdapterPosition();
                notifyDataSetChanged();

                mAdapterCallback.onMethodCallback(radio_id);

              //  Toast.makeText(context,"selected " + radio_id,Toast.LENGTH_SHORT).show();
            }
        });



    }

    public interface AdapterCallback {
        void onMethodCallback(String radio_id);
    }


    private void getNews_Coin(String news_id) {

        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(context);
        String url=Base_Url.BaseUrl+Base_Url.news_read;
        Log.e("get_news_coin_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("news_id",news_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_news_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_leader_show", String.valueOf(anError));

                    }
                });
    }

    @Override
    public int getItemCount() {
        return moneyToCoinRates.size();
    }



}
