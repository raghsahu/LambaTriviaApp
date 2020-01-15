package com.trivia.lambatriviaapp.Activity.ProfileAll_Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.How_to_play_Activity;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.BuildConfig;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_setup_Activity extends AppCompatActivity {

    LinearLayout ll_how_to_play,ll_profile,ll_share_frnds,ll_coin_wallet;
    CardView card_profile;
    CircleImageView iv_profile;
    ImageView iv_main_profile,iv_back;
    TextView tv_user_name,tv_coin,tv_naira,tv_total_earn_coin,tv_taotal_earn_naira,
            tv_share,tv_total_frnds,tv_invite_coin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup_);

        ll_how_to_play=findViewById(R.id.ll_how_to_play);
        card_profile=findViewById(R.id.card_profile);
        ll_profile=findViewById(R.id.ll_profile);
        ll_share_frnds=findViewById(R.id.ll_share_frnds);
        iv_profile=findViewById(R.id.iv_profile);
        tv_user_name=findViewById(R.id.tv_user_name);
        tv_coin=findViewById(R.id.tv_coin);
        tv_naira=findViewById(R.id.tv_naira);
        tv_total_earn_coin=findViewById(R.id.tv_total_earn_coin);
        tv_taotal_earn_naira=findViewById(R.id.tv_taotal_earn_naira);
        tv_total_frnds=findViewById(R.id.tv_total_frnds);
        iv_main_profile=findViewById(R.id.iv_main_profile);
        tv_share=findViewById(R.id.tv_share);
        ll_coin_wallet=findViewById(R.id.ll_coin_wallet);
        tv_invite_coin=findViewById(R.id.tv_invite_coin);
        iv_back=findViewById(R.id.iv_back);

        try {
            if (getIntent()!=null){
                tv_coin.setText(getIntent().getStringExtra("total_coin"));
                tv_naira.setText(getIntent().getStringExtra("total_naira"));
                tv_user_name.setText(getIntent().getStringExtra("username"));
                String image=getIntent().getStringExtra("image");

                if (!image.isEmpty()) {
                    Picasso.with(Profile_setup_Activity.this)
                            .load(Base_Url.MenuImageUrl + image)
                            //.placeholder(R.drawable.person)
                            .into(iv_profile);
                }
            }
        }catch (Exception e){

        }

        if (Connectivity.isConnected(Profile_setup_Activity.this)){
            String user_id= AppPreference.getUser_Id(Profile_setup_Activity.this);
            String url= Base_Url.BaseUrl+Base_Url.get_profile;
            getProfile(url,user_id);
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });
        ll_coin_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile_setup_Activity.this, Wallet_transaction_Activity.class);
                startActivity(intent);
            }
        });

        ll_how_to_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile_setup_Activity.this, How_to_play_Activity.class);
                startActivity(intent);
            }
        });
        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile_setup_Activity.this, NewUser_Profile_Activity.class);
                startActivity(intent);
            }
        });
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile_setup_Activity.this, NewUser_Profile_Activity.class);
                startActivity(intent);
            }
        });
        iv_main_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile_setup_Activity.this, NewUser_Profile_Activity.class);
                startActivity(intent);
            }
        });
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFriends();
            }
        });

    }

    private void getProfile(String url, String user_id) {
        final ProgressDialog progressDialog = new ProgressDialog(Profile_setup_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("get_prof_url"," "+url);
        AndroidNetworking.upload(url)
                .addMultipartParameter("user_id",user_id)
                // .setTag("show_menu")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_profile_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                AppPreference.setJsondata(Profile_setup_Activity.this, jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String user_id = job.getString("user_id");
                                    String name = job.getString("name");
                                    String user_name = job.getString("username");
                                    String email = job.getString("email");
                                    String mobile = job.getString("mobile");
                                    String image = job.getString("image");
                                    String reffered_by = job.getString("reffered_by");
                                    String total_coin = job.getString("total_coin");
                                    String total_naira = job.getString("total_naira");
                                    String total_coin_earned = job.getString("total_coin_earned");
                                    String total_money_won = job.getString("total_money_won");
                                    String friends = job.getString("friends");
                                    String invite_coin = job.getString("invite_coin");

                                    AppPreference.setUser_Id(Profile_setup_Activity.this, user_id);
                                    AppPreference.setName(Profile_setup_Activity.this,user_name);

                                    //*********text view set***
                                    tv_user_name.setText(user_name);
                                    tv_coin.setText(total_coin);
                                    tv_naira.setText(total_naira);
                                    tv_total_earn_coin.setText(total_coin_earned);
                                    tv_taotal_earn_naira.setText(total_money_won);
                                    tv_total_frnds.setText(friends);
                                    tv_invite_coin.setText(invite_coin);

                                    if (!image.isEmpty()){
                                        Picasso.with(Profile_setup_Activity.this)
                                                .load(Base_Url.MenuImageUrl+image)
                                                //.placeholder(R.drawable.person)
                                                .into(iv_profile);
                                    }
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_prof_show", String.valueOf(anError));

                    }
                });

    }

    private void shareFriends() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Lamba");
            String shareMessage= "\nLet me recommend you this app\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
