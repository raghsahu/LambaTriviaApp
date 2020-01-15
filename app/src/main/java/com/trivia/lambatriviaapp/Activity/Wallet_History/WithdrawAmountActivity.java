package com.trivia.lambatriviaapp.Activity.Wallet_History;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.BankDetailsModel;
import com.trivia.lambatriviaapp.Model_Class.Wallet_History.MyCardList;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WithdrawAmountActivity extends AppCompatActivity {

    BankDetailsModel bankDetailsModel;
    MyCardList myCardList;
    ImageView iv_back;
    TextView tv_avail_naira,tv_bank_details,tv_withdraw;
    EditText et_main_amount;
     String Payment_Type;
    private String Payment_Method_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_amount);
        iv_back=findViewById(R.id.iv_back);
        tv_avail_naira=findViewById(R.id.tv_avail_naira);
        tv_bank_details=findViewById(R.id.tv_bank_details);
        tv_withdraw=findViewById(R.id.tv_withdraw);
        et_main_amount=findViewById(R.id.et_main_amount);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        try {
            if (getIntent()!=null){

                Payment_Type=getIntent().getStringExtra("Payment_Type");
                Log.e("pay_type", Payment_Type);

                if (Payment_Type.equalsIgnoreCase("Card")){
                    myCardList=(MyCardList) getIntent().getSerializableExtra("BankModel");

                    Payment_Method_id=myCardList.getId();

                    tv_bank_details.setText("Payment Type: "+Payment_Type + "\n"+ "Name: "+myCardList.getCard_holder()+
                            "\n"+"Card number: "+ myCardList.getCart_number()+ "\n"+
                            "Expiry: "+ myCardList.getExpiry_month()+" / "+myCardList.getExpiry_year()+ "\n"+
                            "Cvv: "+myCardList.getCart_ccv());

                }else if (Payment_Type.equalsIgnoreCase("Bank Deposit")){

                    bankDetailsModel=(BankDetailsModel)getIntent().getSerializableExtra("BankModel");

                    Payment_Method_id=bankDetailsModel.getId();

                    tv_bank_details.setText("Payment_Type: "+Payment_Type + "\n"+ "Name: "+bankDetailsModel.getAccount_holder()+
                            "\n"+"Account: "+ bankDetailsModel.getAccount_number()+ "\n"+
                            "Branch code: "+ bankDetailsModel.getBranch_code());

                }

            }

        }catch (Exception e){
        }



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


        tv_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Amount=et_main_amount.getText().toString();

                if (!Amount.isEmpty()){

                    if (Connectivity.isConnected(WithdrawAmountActivity.this)){

                        SubmitWithdraw(Amount,Payment_Type,Payment_Method_id);
                    }else {
                        Toast.makeText(WithdrawAmountActivity.this, "Please check Interne", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(WithdrawAmountActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void SubmitWithdraw(String amount, String payment_type, String payment_method_id) {
        final ProgressDialog progressDialog = new ProgressDialog(WithdrawAmountActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(WithdrawAmountActivity.this);
        String url= Base_Url.BaseUrl+Base_Url.money_withdraw_request;
        Log.e("get_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("payment_type",payment_type)
                .addBodyParameter("payment_method_id",payment_method_id)
                .addBodyParameter("amount",amount)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            Toast.makeText(WithdrawAmountActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                Intent intent=new Intent(WithdrawAmountActivity.this,WithdrawPendingActivity.class);
                                intent.putExtra("msg", msg);
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
                        Log.e("error_res", String.valueOf(anError));

                    }
                });

    }
}
