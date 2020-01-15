package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Adapter.NewsBlogAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.News_blog_model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import static com.trivia.lambatriviaapp.MainActivity.total_coin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News_ReadMore_Activity extends AppCompatActivity {

    RecyclerView recycler_news_blog;
    ArrayList<News_blog_model> news_blog_models=new ArrayList<>();
    private NewsBlogAdapter showNewsBlogAdapter;
    ImageView iv_back;
    TextView tv_wallet_coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__read_more_);

        recycler_news_blog=findViewById(R.id.recycler_news_blog);
        iv_back=findViewById(R.id.iv_back);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);

        if (Connectivity.isConnected(News_ReadMore_Activity.this)){
            get_News_blog();
        }else {
            Toast.makeText(News_ReadMore_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
        }

        try{
            tv_wallet_coin.setText(total_coin);
        }catch (Exception e){

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }

    private void get_News_blog() {

        final ProgressDialog progressDialog = new ProgressDialog(News_ReadMore_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(News_ReadMore_Activity.this);
        String url= Base_Url.BaseUrl+Base_Url.news_blog;
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.get(url)
                //.addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_news_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String id = job.getString("id");
                                    String title = job.getString("title");
                                    String image = job.getString("image");
                                    String news_link = job.getString("news_link");
                                    String coin = job.getString("coin");
                                    news_blog_models.add(i,new News_blog_model(id,title,image,news_link,coin) );
                                }

                                showNewsBlogAdapter= new NewsBlogAdapter( News_ReadMore_Activity.this,news_blog_models);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(News_ReadMore_Activity.this);
                                recycler_news_blog.setLayoutManager(mLayoutManger);
                                recycler_news_blog.setLayoutManager(new LinearLayoutManager(News_ReadMore_Activity.this, RecyclerView.VERTICAL, false));
                                recycler_news_blog.setItemAnimator(new DefaultItemAnimator());
                                recycler_news_blog.setAdapter(showNewsBlogAdapter);

                            }



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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
