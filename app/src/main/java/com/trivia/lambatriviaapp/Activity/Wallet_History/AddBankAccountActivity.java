package com.trivia.lambatriviaapp.Activity.Wallet_History;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.add_bank_detail;
import static com.trivia.lambatriviaapp.All_Url.Base_Url.add_card_detail;

/**
 * Created by Raghvendra Sahu on 25/11/2019.
 */
public class AddBankAccountActivity extends AppCompatActivity {

    ImageView iv_back;
    Button bt_your_ac,bt_add_bank;
    EditText et_holder_name,et_branch_code,et_ac_nmbr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_account);

        iv_back = findViewById(R.id.iv_back);
        et_holder_name = findViewById(R.id.et_holder_name);
        et_branch_code = findViewById(R.id.et_branch_code);
        et_ac_nmbr = findViewById(R.id.et_ac_nmbr);
        bt_your_ac = findViewById(R.id.bt_your_ac);
        bt_add_bank = findViewById(R.id.bt_add_bank);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bt_your_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AddBankAccountActivity.this,SeeMyBankActivity.class);
                startActivity(intent);

            }
        });


        bt_add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Et_ac_Name = et_holder_name.getText().toString();
                String Branch_code = et_branch_code.getText().toString();
                String Ac_number = et_ac_nmbr.getText().toString();

                if (!Et_ac_Name.isEmpty() && !Branch_code.isEmpty() &&
                        !Ac_number.isEmpty()){
                    if (Connectivity.isConnected(AddBankAccountActivity.this)){

                        AddBankDetails(Et_ac_Name,Branch_code,Ac_number);

                    }else {
                        Toast.makeText(AddBankAccountActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AddBankAccountActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }

        }
    });


    }

    private void AddBankDetails(String et_ac_name, String branch_code, String ac_number) {
        final ProgressDialog progressDialog = new ProgressDialog(AddBankAccountActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

       // Log.e("get__url"," "+ Base_Url.BaseUrl+add_card_detail);
        AndroidNetworking.post(Base_Url.BaseUrl+add_bank_detail)
                .addBodyParameter("account_holder",et_ac_name)
                .addBodyParameter("branch_code",branch_code)
                .addBodyParameter("account_number",ac_number)
                .addBodyParameter("user_id", AppPreference.getUser_Id(AddBankAccountActivity.this))
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

                            Toast.makeText(AddBankAccountActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){

                                Intent intent=new Intent(AddBankAccountActivity.this,SeeMyBankActivity.class);
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
