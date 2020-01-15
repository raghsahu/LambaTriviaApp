package com.trivia.lambatriviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.ProfileAll_Activity.Profile_setup_Activity;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Fragments.Home_Frament;
import com.trivia.lambatriviaapp.Fragments.Invite_Fragment;
import com.trivia.lambatriviaapp.Fragments.Leaderboard_Fragment;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_profile,ll_wallet_details;
    CircleImageView iv_profile;
    TextView tv_user_id;
    public static TextView tv_coinMain,tv_naira;
    String total_naira,image;
    public static String user_name;
    public static String total_coin;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment_home=new Home_Frament();
                    FragmentManager fragmentManager1 = getFragmentManager();
                    FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.frame_content, fragment_home);
                    ft_home.commit();
                    return true;
                case R.id.navigation_dashboard:
                    Fragment fragment_leader=new Leaderboard_Fragment();
                    FragmentTransaction ft_leader = getSupportFragmentManager().beginTransaction();
                    ft_leader.replace(R.id.frame_content, fragment_leader);
                    // ft.addToBackStack(null);
                    ft_leader.commit();

                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText(R.string.title_notifications);

//                    Intent intent=new Intent(MainActivity.this, Invite_friend_Activity.class);
//                    startActivity(intent);

                    Fragment fragment=new Invite_Fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_content, fragment);
                   // ft.addToBackStack(null);
                    ft.commit();


                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // MobileAds.initialize(this, getString(R.string.admob_app_id));

        FrameLayout frameLayout=findViewById(R.id.frame_content);
        ll_profile  = findViewById(R.id.ll_profile);
        iv_profile  = findViewById(R.id.iv_profile);
        tv_user_id  = findViewById(R.id.tv_user_id);
        tv_coinMain  = findViewById(R.id.tv_coin);
        tv_naira  = findViewById(R.id.tv_naira);
        ll_wallet_details  = findViewById(R.id.ll_wallet_details);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navView.setItemIconTintList(null);

        if (Connectivity.isConnected(MainActivity.this)){
            String user_id= AppPreference.getUser_Id(MainActivity.this);
            String url= Base_Url.BaseUrl+Base_Url.get_profile;
            getProfile(url,user_id);
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_content, new Home_Frament());
        tx.commit();

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Profile_setup_Activity.class);
               intent.putExtra("username", user_name);
               intent.putExtra("total_coin", total_coin);
               intent.putExtra("total_naira", total_naira);
               intent.putExtra("image", image);
                startActivity(intent);

            }
        });

        ll_wallet_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Wallet_transaction_Activity.class);
                startActivity(intent);

            }
        });

    }

    private void getProfile(String url, String user_id) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,R.style.MyGravity);
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
                                AppPreference.setJsondata(MainActivity.this, jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String user_id = job.getString("user_id");
                                    String name = job.getString("name");
                                    user_name = job.getString("username");
                                    String email = job.getString("email");
                                    String mobile = job.getString("mobile");
                                    image = job.getString("image");
                                    String reffered_by = job.getString("reffered_by");
                                     total_coin = job.getString("total_coin");
                                     total_naira = job.getString("total_naira");
                                    String total_coin_earned = job.getString("total_coin_earned");
                                    String total_money_won = job.getString("total_money_won");
                                    String friends = job.getString("friends");
                                    String invite_coin = job.getString("invite_coin");
                                    String notification = job.getString("notification");

                                    AppPreference.setUser_Id(MainActivity.this, user_id);
                                    AppPreference.setName(MainActivity.this,user_name);

                                    tv_user_id.setText(user_name);
                                    tv_coinMain.setText(total_coin);
                                    tv_naira.setText(total_naira);

                                    if (!image.isEmpty()){
                                    Picasso.with(MainActivity.this)
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


    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.navigation_home != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            super.onBackPressed();
        }

    }

    private void setHomeItem(MainActivity mainActivity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                mainActivity.findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }
}
