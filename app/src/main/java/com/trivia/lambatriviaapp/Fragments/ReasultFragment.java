package com.trivia.lambatriviaapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.lambatriviaapp.Adapter.LeagueMoreAdapter;
import com.trivia.lambatriviaapp.Adapter.LeagueResultAdapter;
import com.trivia.lambatriviaapp.Adapter.ShowLeaderAdapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.LeaderBoard_Model;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Model;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Result;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ReasultFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ReasultFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ReasultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recy_result_leag_more;
    LeagueResultAdapter leagueResultAdapter;
    TextView tv_not_found;

    ArrayList<Show_League_Result>show_league_result=new ArrayList<>();

  //  private OnFragmentInteractionListener mListener;

    public ReasultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReasultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReasultFragment newInstance(String param1, String param2) {
        ReasultFragment fragment = new ReasultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_reasult, container, false);
        recy_result_leag_more=root.findViewById(R.id.recy_result_leag_more);
        tv_not_found=root.findViewById(R.id.tv_not_found);

        if (Connectivity.isConnected(getActivity())){
            get_League_result_game();

        }else {
            Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    private void get_League_result_game() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id=AppPreference.getUser_Id(getActivity());
        String url=Base_Url.BaseUrl+Base_Url.get_result_league_games;
        Log.e("get_res_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
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
                                    String id = job.getString("id");
                                    String game_title = job.getString("game_title");
                                    String total_coin = job.getString("total_coin");
                                    String naira_prize = job.getString("naira_prize");
                                    String start_date_time = job.getString("start_date_time");
                                    String image = job.getString("image");

                                    show_league_result.add(i,new Show_League_Result(id,game_title,total_coin,naira_prize,start_date_time,image) );
                                }

                                leagueResultAdapter= new LeagueResultAdapter( getActivity(),show_league_result);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recy_result_leag_more.setLayoutManager(mLayoutManger);
                                recy_result_leag_more.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                recy_result_leag_more.setItemAnimator(new DefaultItemAnimator());
                                recy_result_leag_more.setAdapter(leagueResultAdapter);

                            }else {
                                tv_not_found.setVisibility(View.VISIBLE);
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

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
