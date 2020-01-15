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
import com.trivia.lambatriviaapp.Adapter.BankDetailsAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.BankDetailsModel;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.get_user_banks;
import static com.trivia.lambatriviaapp.All_Url.Base_Url.get_user_cards;

public class SeeMyBankActivity extends AppCompatActivity {
    ImageView iv_back;
    RecyclerView recycler_bank;
    BankDetailsAdapter bankDetailsAdapter;
    ArrayList<BankDetailsModel>bankDetailsModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_bank);

        recycler_bank=findViewById(R.id.recycler_bank);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (Connectivity.isConnected(SeeMyBankActivity.this)){

            getMybankAc();
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMybankAc() {

        final ProgressDialog progressDialog = new ProgressDialog(SeeMyBankActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

      //  Log.e("get__url"," "+ Base_Url.BaseUrl+get_user_banks);
        AndroidNetworking.post(Base_Url.BaseUrl+get_user_banks)
                .addBodyParameter("user_id", AppPreference.getUser_Id(SeeMyBankActivity.this))
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
                                    String account_holder = jsonObject1.getString("account_holder");
                                    String branch_code = jsonObject1.getString("branch_code");
                                    String account_number = jsonObject1.getString("account_number");


                                    bankDetailsModels.add(i, new BankDetailsModel(id,user_id,account_holder,
                                            branch_code,account_number));

                                }

                                bankDetailsAdapter= new BankDetailsAdapter( SeeMyBankActivity.this,bankDetailsModels);
                                recycler_bank.setLayoutManager(new LinearLayoutManager(SeeMyBankActivity.this, RecyclerView.VERTICAL, false));
                                recycler_bank.setAdapter(bankDetailsAdapter);

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
