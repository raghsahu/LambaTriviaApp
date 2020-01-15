package com.trivia.lambatriviaapp.Adapter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.FullScreenVideoClip_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.Model_Class.Video_Clip_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoClipAdapter extends RecyclerView.Adapter<VideoClipAdapter.ViewHolder> {

    private static final String TAG = "VideoClipAdapter";
    private ArrayList<Video_Clip_model> video_clip_models;
    public Context context;
    View viewlike;

    public VideoClipAdapter(Context activity, ArrayList<Video_Clip_model> video_clip_models1) {
        context = activity;
        video_clip_models = video_clip_models1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_video_coin,tv_video_title;
        ImageView iv_video_img;
        LinearLayout ll_video_play;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            tv_video_coin=viewlike.findViewById(R.id.tv_video_coin);
            iv_video_img=viewlike.findViewById(R.id.iv_video_img);
            tv_video_title=viewlike.findViewById(R.id.tv_video_title);
            ll_video_play=viewlike.findViewById(R.id.ll_video_play);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_clip_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Video_Clip_model video_clip_model = video_clip_models.get(position);

        viewHolder.tv_video_title.setText(video_clip_model.getTitle());
        viewHolder.tv_video_coin.setText(video_clip_model.getCoin());

        final String vid_url=Base_Url.video_clip+video_clip_model.getVideo();

        //************find thumbnails image from video url**
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.isMemoryCacheable();
        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(vid_url)
                .into(viewHolder.iv_video_img);

        //**********click video ****
        viewHolder.ll_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String video_url=video_clip_model.getVideo();

                    Intent browserIntent = new Intent(context,FullScreenVideoClip_Activity.class);
                    browserIntent.putExtra("video_url", video_url);
                    context.startActivity(browserIntent);


                if (Connectivity.isConnected(context)){
                    String video_id=video_clip_model.getId();
                    getvideo_Coin(video_id);
                }

            }
        });

    }

    private void getvideo_Coin(String video_id) {

        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(context);
        String url=Base_Url.BaseUrl+Base_Url.video_clip_click;
        Log.e("get_video_coin_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("video_id",video_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_video_response = ", jsonObject.toString());

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
                        Log.e("error_video_coin", String.valueOf(anError));

                    }
                });
    }

    @Override
    public int getItemCount() {
        return video_clip_models.size();
    }

}
