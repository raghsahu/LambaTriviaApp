package com.trivia.lambatriviaapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Adapter.ShowLeaderAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.LeaderBoard_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Leaderboard_Fragment extends Fragment {

    RecyclerView recycler_leaderboard;
    SwitchCompat filter_leaderboard;
    View rootview;

    ShowLeaderAdapter showLeaderAdapter;
    ArrayList<LeaderBoard_Model>leaderBoardModelArrayList=new ArrayList<>();
    static Boolean isTouched = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view=inflater.inflate(R.layout.leaderboard_fragment, container, false);
        rootview=view;
        recycler_leaderboard=rootview.findViewById(R.id.recycler_leaderboard);
        filter_leaderboard=rootview.findViewById(R.id.filter_leaderboard);

        try {
            if (leaderBoardModelArrayList!=null){
                leaderBoardModelArrayList.clear();
            }
        }catch (Exception e){
        }
        if (Connectivity.isConnected(getActivity())){
            getLeaderBoard("");

        }else {
            Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
        }



        //**********switch compat filter using api**************
        filter_leaderboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    try {
                        if (leaderBoardModelArrayList!=null){
                            leaderBoardModelArrayList.clear();
                        }
                    }catch (Exception e){
                    }
                    if (Connectivity.isConnected(getActivity())){
                        getLeaderBoard("1");

                    }else {
                        Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    try {
                        if (leaderBoardModelArrayList!=null){
                            leaderBoardModelArrayList.clear();
                        }
                    }catch (Exception e){
                    }
                    if (Connectivity.isConnected(getActivity())){
                        getLeaderBoard("");

                    }else {
                        Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        return view;
    }

    private void getLeaderBoard(String s) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.leaderboard;
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.post(url)
               .addBodyParameter("status",s)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_leader_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                           // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String username = job.getString("username");
                                    String image = job.getString("image");
                                    String total_naira = job.getString("total_coin");

//                                    Picasso.with(MainActivity.this)
//                                            .load(Base_Url.MenuImageUrl+image)
//                                            .into(iv_profile);
                                    leaderBoardModelArrayList.add(i,new LeaderBoard_Model(username,total_naira,image) );
                                }

                                showLeaderAdapter= new ShowLeaderAdapter( getActivity(),leaderBoardModelArrayList);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycler_leaderboard.setLayoutManager(mLayoutManger);
                                recycler_leaderboard.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recycler_leaderboard.setItemAnimator(new DefaultItemAnimator());
                                recycler_leaderboard.setAdapter(showLeaderAdapter);

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        // setHasOptionsMenu(true);
        // getActivity().setTitle("My Activity");


    }



}
