package com.trivia.lambatriviaapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trivia.lambatriviaapp.Adapter.LeagueMoreAdapter;
import com.trivia.lambatriviaapp.All_Url.Api_Call;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Model;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link PlayFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link PlayFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class PlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recy_play_leag_more;
    LeagueMoreAdapter leagueMoreAdapter;

   // private OnFragmentInteractionListener mListener;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
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
        View root = inflater.inflate(R.layout.fragment_play, container, false);

        recy_play_leag_more=root.findViewById(R.id.recy_play_leag_more);
        if (Connectivity.isConnected(getActivity())){
            get_League_game();

        }else {
            Toast.makeText(getActivity(), "Plaese check internet", Toast.LENGTH_SHORT).show();
        }




        return root;
    }

    private void get_League_game() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url.BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        String user_id= AppPreference.getUser_Id(getActivity());
        Api_Call service = retrofit.create(Api_Call.class);

       // Call<Show_League_Model> call = service.get_league(user_id);
        Call<Show_League_Model> call = service.get_league1(user_id);

        call.enqueue(new Callback<Show_League_Model>() {
            @Override
            public void onResponse(Call<Show_League_Model> call, Response<Show_League_Model> response) {

                try{

                    if (response!=null){
                        Log.e("league_msg",""+response.body().getResult());

//                        for (int i=0;i<response.body().getData().size();i++)
//                        {
//                           // Log.e("get_league_resp" , ""+response.body().getData().get(i).getStartDateTime());
//
//                            if (response.body().getData().get(i).getLeague_questions()!=null){
//
//                                //sqlitehelper.addPracticeQuistion((QuizDataCount) response.body().getData().get(i).getQuestions());
//                            }
//
//                        }

                        leagueMoreAdapter= new LeagueMoreAdapter( getActivity(),response.body().getData(),response.body().getRules());
                        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                        recy_play_leag_more.setLayoutManager(mLayoutManger);
                        recy_play_leag_more.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        recy_play_leag_more.setItemAnimator(new DefaultItemAnimator());
                        recy_play_leag_more.setAdapter(leagueMoreAdapter);

                    }
                }catch (Exception e){
                    Log.e("excep_leage", e.getMessage());
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Show_League_Model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_league",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

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
