package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.braintreepayments.cardform.view.CardForm;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.ProfileAll_Activity.NewUser_Profile_Activity;
import com.trivia.lambatriviaapp.Activity.Video_Clip_list_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class AddWalletActivity extends AppCompatActivity {

    AppCompatSpinner sp_main_year,sp_main_month;
    AppCompatButton bt_main_proceed;
    AppCompatEditText et_main_card,et_main_cvv,et_card_name,et_main_amount;
    ImageView iv_back;
    CardForm cardForm;
   Transaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);

        sp_main_year=findViewById(R.id.sp_main_year);
        sp_main_month=findViewById(R.id.sp_main_month);
        bt_main_proceed=findViewById(R.id.bt_main_proceed);
        et_main_card=findViewById(R.id.et_main_card);
        et_main_cvv=findViewById(R.id.et_main_cvv);
        et_card_name=findViewById(R.id.et_card_name);
        et_main_amount=findViewById(R.id.et_main_amount);
        iv_back=findViewById(R.id.iv_back);
        cardForm = findViewById(R.id.card_form);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //*********************
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
               // .postalCodeRequired(true)
                //.mobileNumberRequired(true)
               // .mobileNumberExplanation("SMS is required on this number")
                .setup(AddWalletActivity.this);

        //******************

        getYears();//get future year for card validity

        //setspinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddWalletActivity.this, android.R.layout.simple_list_item_1, getYears());
        //here we set the adapter to the year spinner
        sp_main_year.setAdapter(adapter);

        Bootstrap.setPaystackKey(getString(R.string.paystack_live_public_key));
        bt_main_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bootstrap.setPaystackKey(getString(R.string.paystack_live_public_key));
                String Amount = et_main_amount.getText().toString().trim();

                String cardCvv = cardForm.getCvv();
                String cardNumber = cardForm.getCardNumber();
                String month    =    cardForm.getExpirationMonth();
                String year =    cardForm.getExpirationYear();

                if (!cardCvv.isEmpty() && !cardNumber.isEmpty() && !month.isEmpty() && !year.isEmpty()){
                    if (!Amount.isEmpty()){


                        if (Connectivity.isConnected(AddWalletActivity.this)){
                            chargeCard(Integer.parseInt(Amount));
                        }else {
                            Toast.makeText(AddWalletActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddWalletActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddWalletActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    //*******get year
    private String[] getYears() {
        String[] years = new String[10];
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        years[0] = "Year";
        for (int x = 1; x < years.length; x++) {
            String currentYear = String.valueOf(year++);
            years[x] = currentYear;
        }
        return years;
    }

    private void chargeCard(final int amount) {

//        String cardCvv = et_main_cvv.getText().toString().trim();
//        String cardNumber = et_main_card.getText().toString().trim();
//        String card_name = et_card_name.getText().toString().trim();
//        String month, year;


        String cardCvv = cardForm.getCvv();
        String cardNumber = cardForm.getCardNumber();
        String month    =    cardForm.getExpirationMonth();
        String year =    cardForm.getExpirationYear();

        final ProgressDialog progressDialog = new ProgressDialog(AddWalletActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();


        cardForm.getExpirationMonth();

       // if (sp_main_month.getSelectedItemPosition() > 0 &&
              //  sp_main_year.getSelectedItemPosition() > 0) {
           // month = sp_main_month.getSelectedItem().toString();
           // year = sp_main_year.getSelectedItem().toString();

           // if (card_name.isEmpty()) {
              //  snackBar("Please enter the name on the card");
                //Toast.makeText(this, "Please enter the name on the card", Toast.LENGTH_SHORT).show();
          //  } else {
                if (!cardNumber.isEmpty() && !cardCvv.isEmpty()) {

                    //here we pass the details to the card object
                    Card card = new Card(cardNumber, Integer.valueOf(month), Integer.valueOf(year), cardCvv);

                    //check if the card is valid before attempting to charge the card
                    if (card.isValid()) {
                        //we disable the button so the user doesn't tap multiple times and create a duplicate transaction
                        bt_main_proceed.setEnabled(false);

                        //every transaction requires you to send along a unique reference
                        String customRef = generateReference();

                        //setup a charge object to set values like amount, reference etc
                        Charge charge = new Charge();
                        //the amount(in KOBO eg 1000 kobo = 10 Naira) the customer is to pay for the product or service
                        // basically add 2 extra zeros at the end of your amount to convert from kobo to naira.
                        charge.setAmount(amount);
                        charge.setReference(customRef);
                        charge.setCurrency("NGN");
                        charge.setCard(card);
                        charge.setEmail("rrr@test.com");

                        //Charge the card
                        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                progressDialog.dismiss();
                                bt_main_proceed.setEnabled(true);
                               // snackBar("onSuccess");
                                AddWalletActivity.this.transaction = transaction;
                                Toast.makeText(AddWalletActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                                Toast.makeText(AddWalletActivity.this, transaction.getReference(), Toast.LENGTH_LONG).show();

                                FetchTransactionDetails(transaction.getReference());
                                AddAmountToWallet(transaction.getReference(),amount);

                            }

                            @Override
                            public void beforeValidate(Transaction transaction) {
                               // progressDialog.dismiss();
                              //  snackBar("beforeValidate");
                                Toast.makeText(AddWalletActivity.this, "beforeValidate", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable error, Transaction transaction) {
                                progressDialog.dismiss();
                                bt_main_proceed.setEnabled(true);

                               // snackBar(error.getMessage());
                                Toast.makeText(AddWalletActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        progressDialog.dismiss();
                       // snackBar("Invalid Card");
                        Toast.makeText(this, "Invalid Card", Toast.LENGTH_SHORT).show();
                    }
                }
            }

    private void FetchTransactionDetails(String reference) {
        //this.reference = reference[0];
      // String json = String.format("{\"reference\":\"%s\"}", this.reference);



    }

    private void AddAmountToWallet(String reference, int amount) {
      //  this.reference = reference[0];

       final ProgressDialog progressDialog = new ProgressDialog(AddWalletActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(AddWalletActivity.this);
        String url= Base_Url.BaseUrl+Base_Url.add_balance_to_wallet;
        Log.e("get_ads_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("amount", String.valueOf(amount))
                .addBodyParameter("status","success")
              //  .addBodyParameter("transaction_date",reference)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                         progressDialog.dismiss();
                        Log.e("get_ads_response = ", jsonObject.toString());

                        try {
                             progressDialog.dismiss();
                            String result = jsonObject.getString("result");

                            Intent intent=new Intent(AddWalletActivity.this, WalletGetCoinsActivity.class);
                            startActivity(intent);
                            finish();

                            //Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (Connectivity.isConnected(AddWalletActivity.this)){
                                String user_id=AppPreference.getUser_Id(AddWalletActivity.this);
                                String url=Base_Url.BaseUrl+Base_Url.get_profile;
                                getProfile(url,user_id);
                            }else {
                                Toast.makeText(AddWalletActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
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

    private void getProfile(String url, String user_id) {
        Log.e("get_prof_url"," "+url);
        AndroidNetworking.upload(url)
                .addMultipartParameter("user_id",user_id)
                // .setTag("show_menu")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        // progressDialog.dismiss();
                        Log.e("get_profile_response = ", jsonObject.toString());

                        try {
                            //  progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            // Toast.makeText(NewUser_Profile_Activity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                AppPreference.setJsondata(AddWalletActivity.this, jsonArray);
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
                                    String notification = job.getString("notification");





                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        // progressDialog.dismiss();
                        Log.e("error_prof_show", String.valueOf(anError));

                    }
                });


    }
//        } else {
//            //snackBar("Select Card Expiry Date");
//            Toast.makeText(this, "Select Card Expiry Date", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void snackBar(String message) {
//        Snackbar.make(et_main_cvv,message, Snackbar.LENGTH_LONG).show();
//    }

    private String generateReference() {
        String keys = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = (int)(keys.length() * Math.random());
            sb.append(keys.charAt(index));
        }

        return sb.toString();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
