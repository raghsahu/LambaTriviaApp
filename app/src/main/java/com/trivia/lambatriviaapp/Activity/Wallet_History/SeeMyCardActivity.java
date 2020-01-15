package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;
import com.trivia.lambatriviaapp.Activity.Video_Clip_list_Activity;
import com.trivia.lambatriviaapp.Adapter.CardShowAdapter;
import com.trivia.lambatriviaapp.Adapter.VideoClipAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MyCardList;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.add_card_detail;
import static com.trivia.lambatriviaapp.All_Url.Base_Url.get_user_cards;

public class SeeMyCardActivity extends AppCompatActivity {

    RecyclerView recycler_card;
    CardShowAdapter cardShowAdapter;
    ArrayList<MyCardList>myCardLists=new ArrayList<>();
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_card);

        recycler_card=findViewById(R.id.recycler_card);
        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(SeeMyCardActivity.this)){

            getMyCard();
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMyCard() {
        final ProgressDialog progressDialog = new ProgressDialog(SeeMyCardActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("get__url"," "+ Base_Url.BaseUrl+get_user_cards);
        AndroidNetworking.post(Base_Url.BaseUrl+get_user_cards)
                .addBodyParameter("user_id", AppPreference.getUser_Id(SeeMyCardActivity.this))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_card_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                          //  Toast.makeText(SeeMyCardActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for(int i=0; i<jsonArray.length();i++){

                                    JSONObject jsonObject1= jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String user_id = jsonObject1.getString("user_id");
                                    String card_holder = jsonObject1.getString("card_holder");
                                    String cart_number = jsonObject1.getString("cart_number");
                                    String expiry_month = jsonObject1.getString("expiry_month");
                                    String expiry_year = jsonObject1.getString("expiry_year");
                                    String cart_ccv = jsonObject1.getString("cart_ccv");

                                    myCardLists.add(i, new MyCardList(id,user_id,card_holder,
                                            cart_number,expiry_month,expiry_year,cart_ccv));

                                }


                                cardShowAdapter= new CardShowAdapter( SeeMyCardActivity.this,myCardLists);
                                recycler_card.setLayoutManager(new LinearLayoutManager(SeeMyCardActivity.this, RecyclerView.VERTICAL, false));
                                recycler_card.setAdapter(cardShowAdapter);


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
        super.onBackPressed();
    }
}
