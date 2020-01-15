package com.trivia.lambatriviaapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.Adapter.Country_codeAdapter;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.Model_Class.Country_CodeModel;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.trivia.lambatriviaapp.All_Url.Base_Url.facebook_login;

public class SignIn_SignUp_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    LinearLayout tv_signin,tv_signup;
    EditText et_username,et_userPass;
    SessionManager sessionManager;
    Spinner spinner_country;
    TextView tv_forgot_pw;
    ArrayList<String> ChooseCode=new ArrayList<>();
    public ArrayList<Country_CodeModel> countryCodeModelArrayList = new ArrayList<Country_CodeModel>();
    Country_codeAdapter country_codeAdapter;
    private ArrayAdapter<String> countryCodeAdapter;
    SignInButton sign_in_button;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    LoginButton loginBtn_facebook;
    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__sign_up_);
        sessionManager=new SessionManager(SignIn_SignUp_Activity.this);

        callbackManager = CallbackManager.Factory.create();
       loginBtn_facebook = (LoginButton)findViewById(R.id.fb_login_button);


        tv_signin=findViewById(R.id.ll_next);
        tv_signup=findViewById(R.id.ll_signup);
        et_username=findViewById(R.id.et_username);
        et_userPass=findViewById(R.id.et_userPass);
        tv_forgot_pw=findViewById(R.id.tv_forgot_pw);
        sign_in_button=findViewById(R.id.g_sign_in_button);

        spinner_country=findViewById(R.id.tv_country);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
           // Spin_country(Base_Url.Country_code);
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }
//           loginBtn_facebook.setPermissions(Arrays.asList("user_photos", "email",
//          "user_birthday", "public_profile", "AccessToken"));
        loginBtn_facebook.setPermissions("email", "public_profile");
//**************************facebook login******************
//        loginBtn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
//                Log.e("fblogin", loggedIn + " ??");
//                //Toast.makeText(SignIn_SignUp_Activity.this, "success", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCancel() {
//                Toast.makeText(SignIn_SignUp_Activity.this, "cancel1", Toast.LENGTH_SHORT).show();
//                // App code
//            }
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Toast.makeText(SignIn_SignUp_Activity.this, "fail1", Toast.LENGTH_SHORT).show();
//            }
//        });


//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        Toast.makeText(SignIn_SignUp_Activity.this, "success", Toast.LENGTH_SHORT).show();
//                        // App code
//                        getUserProfile(AccessToken.getCurrentAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText(SignIn_SignUp_Activity.this, "cancel", Toast.LENGTH_SHORT).show();
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Log.e("fb_error", exception.getMessage());
//                        Log.e("fb_error", exception.getLocalizedMessage());
//                        Toast.makeText(SignIn_SignUp_Activity.this, "fail", Toast.LENGTH_SHORT).show();
//                        // App code
//                    }
//                });

        //**********************************************
         //   loginBtn_facebook.setPermissions(Arrays.asList("user_photos", "email",
                      //  "user_birthday", "public_profile", "AccessToken"));
        //loginBtn_facebook.setPermissions("email", "public_profile");
//        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
//                "user_birthday", "public_profile", "AccessToken");
        loginBtn_facebook.registerCallback(callbackManager,
                new FacebookCallback < LoginResult > () {
            @Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object,GraphResponse response) {

                                Log.i("LoginActivity",response.toString());
                                try {
                                    String id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic",profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                  //  String gender = object.getString("gender");
                                    //String birthday = object.getString("birthday");
                                    if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
                                        gotohomeFacebook(id,email,name);
                                    }else {
                                        Toast.makeText(SignIn_SignUp_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                        Toast.makeText(SignIn_SignUp_Activity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Toast.makeText(SignIn_SignUp_Activity.this, "fail", Toast.LENGTH_SHORT).show();
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

//*******************************
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Et_username=et_username.getText().toString();
                String Et_userPass=et_userPass.getText().toString();

            if (!Et_username.isEmpty() && !Et_userPass.isEmpty()){
                if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
                    String url= Base_Url.BaseUrl+Base_Url.login;
                    User_Login(url,Et_username,Et_userPass);

                }else {
                    Toast.makeText(SignIn_SignUp_Activity.this, "Please check internet", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (Et_username.isEmpty()){
                    et_username.setError("Please enter Username");
                }else if (Et_userPass.isEmpty()){
                    et_userPass.setError("Please enter Password");
                }

            }

            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent,RC_SIGN_IN);

                }else {
                    Toast.makeText(SignIn_SignUp_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn_SignUp_Activity.this, Create_registration.class);
                startActivity(intent);
                //finish();
            }
        });

        tv_forgot_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn_SignUp_Activity.this, Forget_password.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    //****************fb profile*******************
    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            //txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            Log.e("fb_details", "First Name: " + first_name + "\nLast Name: " + last_name);
                            //txtEmail.setText(email);
                            //Picasso.with(MainActivity.this).load(image_url).into(imageView);

//                            if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
//                                gotohomeFacebook(id,email,first_name,last_name);
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    //*************fb login**api call
    private void gotohomeFacebook(String id, String email, String name) {
      String url=Base_Url.BaseUrl+facebook_login;

        final ProgressDialog progressDialog = new ProgressDialog(SignIn_SignUp_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("show_login_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("facebook_id",id)
                .addBodyParameter("name", name)
                .addBodyParameter("mobile", "")
                .addBodyParameter("email", email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("fb_response=", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");
                            String message = jsonObject.getString("msg");

                            Toast.makeText(SignIn_SignUp_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){
                                JSONObject jsonObject1=jsonObject.getJSONObject("data");

                                String user_id = jsonObject1.getString("id");
                                String username = jsonObject1.getString("username");
                                String mobile = jsonObject1.getString("mobile");
                                String refferal_code = jsonObject1.getString("refferal_code");

                                AppPreference.setUser_Id(SignIn_SignUp_Activity.this, user_id);
                                sessionManager.setLogin(true);
                                Intent intent=new Intent(SignIn_SignUp_Activity.this, MainActivity.class);
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
                        Log.e("error_fb_login", String.valueOf(anError));

                    }
                });


    }


    private void Spin_country(String country_code) {

        final ProgressDialog progressDialog = new ProgressDialog(SignIn_SignUp_Activity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        Log.e("show_login_url"," "+country_code);
        AndroidNetworking.get(country_code)
                //.addBodyParameter("mobile",et_mobile)
                // .setTag("show_menu")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressDialog.dismiss();
                       Log.e("country_code_res", response.toString());
                        try {
                            progressDialog.dismiss();


                            JSONArray jsonarray=new JSONArray(response.toString());
                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String name = jsonobject.getString("name");
                                String flag = jsonobject.getString("flag");
                                String callingCodes = jsonobject.getString("callingCodes");


                                Log.e("GetCountry_list",name);
                                Log.e("GetCountry_flag",flag);
                                Log.e("callingCodes",callingCodes);


                                ChooseCode.add(callingCodes);
                                countryCodeModelArrayList.add(new Country_CodeModel(name,flag,callingCodes));
                            }
//                            country_codeAdapter= new Country_codeAdapter(SignIn_SignUp_Activity.this,R.layout.spinner_rows, countryCodeModelArrayList);
//
//                            // Set adapter to spinner
//                            spinner_country.setAdapter(country_codeAdapter);

                            countryCodeAdapter = new ArrayAdapter<String>(SignIn_SignUp_Activity.this, android.R.layout.simple_spinner_dropdown_item, ChooseCode);
                            spinner_country.setAdapter(countryCodeAdapter);
                            countryCodeAdapter.notifyDataSetChanged();


                        }catch (Exception e){
                            Log.e("error_exception", String.valueOf(e));
                                 }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_code_show", String.valueOf(anError));
                    }
                });


    }

    private void User_Login(String url, String et_username, String et_userPass) {

        final ProgressDialog progressDialog = new ProgressDialog(SignIn_SignUp_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("show_login_url"," "+url);
        AndroidNetworking.post(url)
                .addBodyParameter("username",et_username)
                .addBodyParameter("password", et_userPass)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("login_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("message");

                            Toast.makeText(SignIn_SignUp_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (message.equalsIgnoreCase("successfull")){
                                JSONObject jsonObject1=jsonObject.getJSONObject("result");

                                String user_id = jsonObject1.getString("id");
                                String username = jsonObject1.getString("username");
                                String refferal_code = jsonObject1.getString("refferal_code");

                                AppPreference.setUser_Id(SignIn_SignUp_Activity.this, user_id);
                                sessionManager.setLogin(true);
                                Intent intent=new Intent(SignIn_SignUp_Activity.this, MainActivity.class);
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
                        Log.e("error_menu_show", String.valueOf(anError));

                    }
                });


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    //***********
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            social_name = acct.getDisplayName();
            social_email = acct.getEmail();

            if (acct.getPhotoUrl() != null) {
                social_img = acct.getPhotoUrl().toString();
            } else {
                social_img = "";
            }
            Log.e("social_img ", " " + social_img);
            social_id = acct.getId();

            acct.getId();
            Log.e("GoogleResult", social_id + "------" + social_name + "------" + social_email);

            if (Connectivity.isConnected(SignIn_SignUp_Activity.this)){
                gotoHome();
            }else {
                Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }
    private void gotoHome(){
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        final ProgressDialog progressDialog = new ProgressDialog(SignIn_SignUp_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("googlelogin_url"," "+Base_Url.BaseUrl+Base_Url.Google_Login);
        AndroidNetworking.post(Base_Url.BaseUrl+Base_Url.Google_Login)
                .addBodyParameter("name",social_name)
                .addBodyParameter("email", social_email)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("googlelogin_response = ", jsonObject.toString());

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                           // Toast.makeText(SignIn_SignUp_Activity.this, ""+message, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){
                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for (int i=0; i<jsonArray.length();i++){
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    String id = job.getString("id");
                                    String name = job.getString("name");
                                    String email = job.getString("email");
                                    String username = job.getString("username");
                                    String password = job.getString("password");
                                    String mobile = job.getString("mobile");
                                    String image = job.getString("image");
                                    String total_coin = job.getString("total_coin");
                                    String total_naira = job.getString("total_naira");

                                    AppPreference.setUser_Id(SignIn_SignUp_Activity.this, id);
                                }

                                sessionManager.setLogin(true);
                                Intent intent=new Intent(SignIn_SignUp_Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(SignIn_SignUp_Activity.this, "please try again", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_menu_show", String.valueOf(anError));

                    }
                });


    }



}
