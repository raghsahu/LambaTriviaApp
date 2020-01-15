package com.trivia.lambatriviaapp.All_Url;

import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Complete_Details;
import com.trivia.lambatriviaapp.Model_Class.League_game_model.Show_League_Model;
import com.trivia.lambatriviaapp.Model_Class.Practice_Quiz_model.Show_Practice_Model;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.Wallet_history_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api_Call {

    @GET(Base_Url.get_practices)
    Call<Show_Practice_Model> get_practice();

    @FormUrlEncoded
    @POST(Base_Url.get_League_Game)
    Call<Show_League_Model> get_league(
                    @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Base_Url.get_league_detail)
    Call<Show_League_Complete_Details> get_completeDetails(
            @Field("user_id") String user_id,
            @Field("id") String quiz_comp_id);


    @FormUrlEncoded
    @POST(Base_Url.get_play_league_game)
    Call<Show_League_Model> get_league1(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Base_Url.get_league_result_detail)
    Call<Show_League_Complete_Details> get_ResultDetails(
            @Field("user_id") String user_id,
            @Field("id") String quiz_comp_id);


    @FormUrlEncoded
    @POST(Base_Url.money_transaction_history)
    Call<Wallet_history_Model> get_wallet_history(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(Base_Url.coin_transaction_history)
    Call<Wallet_history_Model> get_wallet_history_coin(
            @Field("user_id") String user_id);
}
