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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.Wallet_History.WithdrawAmountActivity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MyCardList;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raghvendra Sahu on 26/11/2019.
 */
public class CardShowAdapter extends RecyclerView.Adapter<CardShowAdapter.ViewHolder> {

    private static final String TAG = "NewsBlogAdapter";
    private ArrayList<MyCardList> news_blog_models;
    public Context context;
    View viewlike;

    public CardShowAdapter(Context activity, ArrayList<MyCardList> news_blog_models1) {
        context = activity;
        news_blog_models = news_blog_models1;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_holder_name,tv_card_number,tv_expiry,tv_cvv,tv_delete_card,tv_select_card;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_holder_name=viewlike.findViewById(R.id.tv_holder_name);
            tv_card_number=viewlike.findViewById(R.id.tv_card_number);
            tv_expiry=viewlike.findViewById(R.id.tv_expiry);
            tv_cvv=viewlike.findViewById(R.id.tv_cvv);
            tv_delete_card=viewlike.findViewById(R.id.tv_delete_card);
            tv_select_card=viewlike.findViewById(R.id.tv_select_card);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_show_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final MyCardList newsBlogModel = news_blog_models.get(position);

        viewHolder.tv_holder_name.setText(newsBlogModel.getCard_holder());
        viewHolder.tv_card_number.setText(newsBlogModel.getCart_number());
        viewHolder.tv_expiry.setText(newsBlogModel.getExpiry_month()+" / "+newsBlogModel.getExpiry_year());
        viewHolder.tv_cvv.setText(newsBlogModel.getCart_ccv());

        viewHolder.tv_delete_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMyCard(newsBlogModel.getId(),position,news_blog_models.get(position));

            }
        });

    viewHolder.tv_select_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WithdrawAmountActivity.class);
                intent.putExtra("BankModel", news_blog_models.get(position));
                intent.putExtra("Payment_Type", "Card");
                context.startActivity(intent);

            }
        });


    }

    private void deleteMyCard(String _id, final int position, final MyCardList myCardList) {

        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(context);
        String url=Base_Url.BaseUrl+Base_Url.delete_user_card;
        Log.e("get_url"," "+url);
        AndroidNetworking.post(url)
              //  .addBodyParameter("user_id",user_id)
                .addBodyParameter("id",_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){
                                news_blog_models.remove(position);
                                news_blog_models.remove(myCardList);
                                notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_res", String.valueOf(anError));

                    }
                });
    }

    @Override
    public int getItemCount() {
        return news_blog_models.size();
    }

}
