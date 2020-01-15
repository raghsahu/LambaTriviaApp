package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.braintreepayments.cardform.view.CardForm;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.ProfileAll_Activity.Profile_setup_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.paystack.android.model.Card;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.add_card_detail;
import static com.trivia.lambatriviaapp.All_Url.Base_Url.card_recharge_request;

public class Card_request_Activity extends AppCompatActivity {

    CardForm cardForm;
    ImageView iv_back;
    AppCompatEditText et_phone_number,et_amount;
    AppCompatButton bt_main_proceed;
    Button bt_your_card;
    TextView tv_avail_naira;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_request_);

      //  cardForm = findViewById(R.id.card_form);
        iv_back = findViewById(R.id.iv_back);
        et_phone_number = findViewById(R.id.et_phone_number);
        bt_main_proceed = findViewById(R.id.bt_main_proceed);
        bt_your_card = findViewById(R.id.bt_your_card);
        tv_avail_naira = findViewById(R.id.tv_avail_naira);
        et_amount = findViewById(R.id.et_amount);


        try {
            String json_array = AppPreference.getJsondata(this);
            if (json_array != null) {
                try {
                    JSONArray jsoArray = new JSONArray(json_array);
                    for (int i = 0; i < jsoArray.length(); i++) {
                        JSONObject job = jsoArray.getJSONObject(i);
                        String total_naira = job.getString("total_naira");

                        tv_avail_naira.setText(total_naira);

                    }

                } catch (Exception e) {

                }
            }
        } catch (Exception e) {

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bt_your_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Card_request_Activity.this,SeeMyCardActivity.class);
                startActivity(intent);

            }
        });

        //*************************card credit*********
//        cardForm.cardRequired(true)
//                .expirationRequired(true)
//                .cvvRequired(true)
//                // .postalCodeRequired(true)
//                //.mobileNumberRequired(true)
//                // .mobileNumberExplanation("SMS is required on this number")
//                .setup(Card_request_Activity.this);

        bt_main_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phone_number=et_phone_number.getText().toString();
                String Et_amount=et_amount.getText().toString();

                if (!Phone_number.isEmpty() && !Et_amount.isEmpty()){
                    if (Connectivity.isConnected(Card_request_Activity.this)){
                        SubmitCardRequest(Phone_number,Et_amount);

                    }else {
                        Toast.makeText(Card_request_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    if (Phone_number.isEmpty()){
                        Toast.makeText(Card_request_Activity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                    }else if (Et_amount.isEmpty()){
                        Toast.makeText(Card_request_Activity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });


//        bt_main_proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String Et_Card_Name=et_holder_name.getText().toString();
//                String cardCvv = cardForm.getCvv();
//                String cardNumber = cardForm.getCardNumber();
//                String month    =    cardForm.getExpirationMonth();
//                String year =    cardForm.getExpirationYear();
//
//                if (!cardNumber.isEmpty() && !cardCvv.isEmpty()) {
//                    Card card = new Card(cardNumber, Integer.valueOf(month), Integer.valueOf(year), cardCvv);
//                    if (card.isValid()) {
//
//                        if (!Et_Card_Name.isEmpty()){
//
//                            if (Connectivity.isConnected(Card_request_Activity.this)){
//                                SaveCardDetails(Et_Card_Name,cardCvv,cardNumber,month,year);
//                            }else {
//                                Toast.makeText(Card_request_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }else {
//                            Toast.makeText(Card_request_Activity.this, "Please enter Card holder name", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                    else {
//                        Toast.makeText(Card_request_Activity.this, "Invalid Card", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(Card_request_Activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        //***********************************
    }

    private void SubmitCardRequest(String phone_number, String et_amount) {
        final ProgressDialog progressDialog = new ProgressDialog(Card_request_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("get_url"," "+Base_Url.BaseUrl+card_recharge_request);
        AndroidNetworking.post(Base_Url.BaseUrl+card_recharge_request)
                .addBodyParameter("amount",et_amount)
                .addBodyParameter("phone_numer",phone_number)
                .addBodyParameter("user_id",AppPreference.getUser_Id(Card_request_Activity.this))
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

                            Toast.makeText(Card_request_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                Intent intent=new Intent(Card_request_Activity.this,WithdrawPendingActivity.class);
                                intent.putExtra("msg", message);
                                startActivity(intent);
                                finish();

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

    private void SaveCardDetails(String et_card_name, String cardCvv, String cardNumber,
                                 String month, String year) {
        final ProgressDialog progressDialog = new ProgressDialog(Card_request_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("get__url"," "+Base_Url.BaseUrl+add_card_detail);
        AndroidNetworking.post(Base_Url.BaseUrl+add_card_detail)
                .addBodyParameter("card_holder",et_card_name)
                .addBodyParameter("cart_ccv",cardCvv)
                .addBodyParameter("cart_number",cardNumber)
                .addBodyParameter("expiry_year",year)
                .addBodyParameter("expiry_month",month)
                .addBodyParameter("user_id",AppPreference.getUser_Id(Card_request_Activity.this))
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

                            Toast.makeText(Card_request_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                Intent intent=new Intent(Card_request_Activity.this,SeeMyCardActivity.class);
                                startActivity(intent);
                                finish();

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
