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

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.LeaderBoard_Model;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsBlogAdapter extends RecyclerView.Adapter<NewsBlogAdapter.ViewHolder> {

    private static final String TAG = "NewsBlogAdapter";
    private ArrayList<News_blog_model> news_blog_models;
    public Context context;
    View viewlike;

    public NewsBlogAdapter(FragmentActivity activity, ArrayList<News_blog_model> news_blog_models1) {
        context = activity;
        news_blog_models = news_blog_models1;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_news_title,tv_news_coin;
        ImageView iv_news_image;
        LinearLayout ll_news_blog,ll_news_read;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_news_title=viewlike.findViewById(R.id.tv_news_title);
            iv_news_image=viewlike.findViewById(R.id.iv_news_image);
            ll_news_blog=viewlike.findViewById(R.id.ll_news_blog);
            ll_news_read=viewlike.findViewById(R.id.ll_news_read);
            tv_news_coin=viewlike.findViewById(R.id.tv_news_coin);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_blog_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final News_blog_model newsBlogModel = news_blog_models.get(position);

        viewHolder.tv_news_title.setText(newsBlogModel.getTitle());
        viewHolder.tv_news_coin.setText(newsBlogModel.getCoin());

        Picasso.with(context)
                .load(Base_Url.game_news+newsBlogModel.getImage())
                .placeholder(R.drawable.news_img)
                .into(viewHolder.iv_news_image);

//        Glide.with(context) // replace with 'this' if it's in activity
//                .load(R.drawable.progress_bar)
//                //.error(R.drawable.error_image) // show error drawable if the image is not a gif
//                .into(viewHolder.iv_news_image);


        viewHolder.pos = position;

        viewHolder.ll_news_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String news_url=newsBlogModel.getNews_link();

                if (!news_url.startsWith("http://") && !news_url.startsWith("https://")){
                    String  url = "http://" + news_url;

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_url));
                    context.startActivity(browserIntent);
                }

                if (Connectivity.isConnected(context)){
                    String news_id=newsBlogModel.getId();
                    getNews_Coin(news_id);
                }

            }
        });

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
        return news_blog_models.size();
    }

}
