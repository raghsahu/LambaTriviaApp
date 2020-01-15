package com.trivia.lambatriviaapp.Activity.ProfileAll_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import com.trivia.lambatriviaapp.Activity.Splash_Activity;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Connectivity.Utility;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewUser_Profile_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button tv_profile_submit;
    LinearLayout ll_logout,ll_add_referal;
    TextView tv_update,show_referal;
    EditText et_fullname,et_user_name,et_mobile,et_email;
    ImageView iv_profile_capture,iv_profile,iv_back;
     String Et_fullname=" ",Et_mobile=" ",Et_email=" ";

    File destination;
    EditText item_name,item_normal_price,item_offer_prise;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String userChoosenTask;
    String filenew;
    SessionManager manager;
    SwitchCompat notific_switch;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_name_);
        manager=new SessionManager(NewUser_Profile_Activity.this);

        tv_update=findViewById( R.id.tv_update);
        show_referal=findViewById( R.id.show_referal);
        et_fullname=findViewById( R.id.et_fullname);
        et_email=findViewById( R.id.et_email);
        et_user_name=findViewById( R.id.et_user_name);
        et_mobile=findViewById( R.id.et_mobile);
        iv_profile=findViewById( R.id.iv_profile);
        iv_profile_capture=findViewById( R.id.iv_profile_capture);
        ll_add_referal=findViewById( R.id.ll_add_referal);
        iv_back=findViewById( R.id.iv_back);
        ll_logout=findViewById( R.id.ll_logout);
        notific_switch=findViewById( R.id.notific_switch);

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        //******setup text data in profile using session **
        SetupProfileDetails_Pref();

        notific_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                        Notification_status_Api("On");

                    }else {
                        Toast.makeText(NewUser_Profile_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                        Notification_status_Api("Off");

                    }else {
                        Toast.makeText(NewUser_Profile_Activity.this, "Plaese check internet", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });

        iv_profile_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout_user();

            }
        });

        ll_add_referal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReferDialog();
                //Toast.makeText(NewUser_Profile_Activity.this, "rrr", Toast.LENGTH_SHORT).show();
            }
        });


        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Et_fullname=et_fullname.getText().toString();
                String Et_username=et_user_name.getText().toString();
                Et_mobile=et_mobile.getText().toString();
                Et_email=et_email.getText().toString();

                if (!Et_username.isEmpty()){
                    if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                        String url= Base_Url.BaseUrl+Base_Url.UpdateProfile;
                        String user_id=AppPreference.getUser_Id(NewUser_Profile_Activity.this);
                        Profile_Update(url,user_id,Et_username);

                    }else {
                        Toast.makeText(NewUser_Profile_Activity.this, "Please check internet", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (Et_username.isEmpty()){
                        et_user_name.setError("username not found");
                    }

                }
            }
        });

    }

    //*************notification on off api-
    private void Notification_status_Api(String status_on_off) {

        progressDialog = new ProgressDialog(NewUser_Profile_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String user_id= AppPreference.getUser_Id(NewUser_Profile_Activity.this);
        String noti_url=Base_Url.BaseUrl+Base_Url.notification_status;
        Log.e("notif_url"," "+Base_Url.BaseUrl+Base_Url.notification_status);
        AndroidNetworking.post(noti_url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("status",status_on_off)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_notifi_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                           // String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")){

                                if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                                    String user_id=AppPreference.getUser_Id(NewUser_Profile_Activity.this);
                                    String url=Base_Url.BaseUrl+Base_Url.get_profile;
                                    getProfile(url,user_id);
                                }else {
                                    Toast.makeText(NewUser_Profile_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                                }


                            }else {
                               // Toast.makeText(NewUser_Profile_Activity.this, ""+message, Toast.LENGTH_SHORT).show();
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

    //*************profile edit details setup****
    private void SetupProfileDetails_Pref() {
        try {
        String json_array = AppPreference.getJsondata(this);
        if (json_array != null) {
            try {
                JSONArray jsoArray=new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    JSONObject job = jsoArray.getJSONObject(i);
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

                    et_fullname.setText(name);
                    et_mobile.setText(mobile);
                    et_user_name.setText(user_name);
                    et_email.setText(email);

                    if (notification.equalsIgnoreCase("On")){
                        notific_switch.setChecked(true);
                    }
                    if(notification.equalsIgnoreCase("Off")) {
                        notific_switch.setChecked(false);
                    }


                    if (!reffered_by.equalsIgnoreCase("") && !reffered_by.isEmpty() &&
                            reffered_by!=null){
                        ll_add_referal.setEnabled(false);
                        show_referal.setText(reffered_by);
                    }

                    if (!image.isEmpty()){
                    Picasso.with(this)
                            .load(Base_Url.MenuImageUrl+image)
                            //.placeholder(R.drawable.person)
                            .into(iv_profile);
                    }
                }
            } catch (JSONException e) {
                Log.e("complete_dialog_error", e.toString());
            }
        }else {
            if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                String user_id=AppPreference.getUser_Id(NewUser_Profile_Activity.this);
                String url=Base_Url.BaseUrl+Base_Url.get_profile;
                getProfile(url,user_id);
            }else {
                Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
            }
        }

    }catch (Exception e){ }

    }

    //*************************add referral method api***
    private void addReferDialog() {

        final Dialog dialog = new Dialog(NewUser_Profile_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_referal_dialog);

        final EditText et_referral = (EditText) dialog.findViewById(R.id.et_referral);

        Button btn_refferal = (Button) dialog.findViewById(R.id.btn_refferal);
        btn_refferal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ET_referral=et_referral.getText().toString();
                String ref_url=Base_Url.BaseUrl+Base_Url.add_referral;
                String user_id=AppPreference.getUser_Id(NewUser_Profile_Activity.this);

                if (!ET_referral.isEmpty()){
                    if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                        Add_Refferal(ET_referral,ref_url,user_id);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(NewUser_Profile_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    et_referral.setError("Please enter referral code!");
                }


            }
        });

        try {
            if (!NewUser_Profile_Activity.this.isFinishing()){
                dialog.show();
            }
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }

    }

    private void Add_Refferal(final String et_referral, String ref_url, String user_id) {

        final ProgressDialog progressDialog = new ProgressDialog(NewUser_Profile_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("get_ref_url"," "+ref_url);
        AndroidNetworking.post(ref_url)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("refferal_code",et_referral)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("get_ref_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equalsIgnoreCase("true")){
                                Toast.makeText(NewUser_Profile_Activity.this, "add referral successfully", Toast.LENGTH_SHORT).show();
                                ll_add_referal.setEnabled(false);
                                show_referal.setText(et_referral);
                            }else {
                                Toast.makeText(NewUser_Profile_Activity.this, ""+message, Toast.LENGTH_SHORT).show();
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


    private void getProfile(String url, String user_id) {
//        final ProgressDialog progressDialog = new ProgressDialog(NewUser_Profile_Activity.this,R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

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
                                AppPreference.setJsondata(NewUser_Profile_Activity.this, jsonArray);
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

                                    AppPreference.setUser_Id(NewUser_Profile_Activity.this, user_id);

                                    et_fullname.setText(name);
                                    et_mobile.setText(mobile);
                                    et_user_name.setText(user_name);
                                    et_email.setText(email);


                                    if (!reffered_by.equalsIgnoreCase("") && !reffered_by.isEmpty() &&
                                        reffered_by!=null){
                                        ll_add_referal.setEnabled(false);
                                        show_referal.setText(reffered_by);
                                    }

                                    if (!image.isEmpty()){
                                        Picasso.with(NewUser_Profile_Activity.this)
                                                .load(Base_Url.MenuImageUrl+image)
                                                //.placeholder(R.drawable.person)
                                                .into(iv_profile);
                                    }



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

    private void logout_user() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(NewUser_Profile_Activity.this).setTitle("Lamba")
                .setMessage("Are you sure, you want to logout this app");

        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                if (googleApiClient != null && googleApiClient.isConnected()) {
                    // signed in. Show the "sign out" button and explanation.
                    google_logout();
                    disconnectFromFacebook();
                } else{
                    Log.e("logout_app", "local login logout");
                    // not signed in. Show the "sign in" button and explanation.
                    disconnectFromFacebook();
                    manager.logoutUser();
                    AppPreference.setName(NewUser_Profile_Activity.this, "");
                    Intent intent=new Intent(NewUser_Profile_Activity.this, Splash_Activity.class);
                    startActivity(intent);
                    finish();

                }


            }

        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

    private void disconnectFromFacebook() {
        Log.e("logout_app_fb", "fb login logout");
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
                manager.logoutUser();
                AppPreference.setName(NewUser_Profile_Activity.this, "");
                Intent intent=new Intent(NewUser_Profile_Activity.this, Splash_Activity.class);
                startActivity(intent);
                finish();

            }
        }).executeAsync();
    }

    private void google_logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()){
                            Log.e("logout_app_gmail", "google login logout");
                            manager.logoutUser();
                            AppPreference.setName(NewUser_Profile_Activity.this, "");
                            Intent intent=new Intent(NewUser_Profile_Activity.this,Splash_Activity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //*****************************************************************
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewUser_Profile_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(NewUser_Profile_Activity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
//***************************************************

    private void Profile_Update(String url, String user_id, String etUsername) {

        final ProgressDialog progressDialog = new ProgressDialog(NewUser_Profile_Activity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("show_prof_url"," "+url);
        AndroidNetworking.upload(url)
                .addMultipartParameter("user_id",user_id)
                .addMultipartParameter("name",Et_fullname)
                .addMultipartParameter("mobile",Et_mobile)
                .addMultipartParameter("username", etUsername)
                .addMultipartFile("image", destination)
                .addMultipartParameter("email", Et_email)
                // .setTag("show_menu")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("profile_update = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            Toast.makeText(NewUser_Profile_Activity.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                                 JSONArray jsonArray = jsonObject.getJSONArray("data");
                                 for (int i = 0; i < jsonArray.length(); i++) {
                                 JSONObject job = jsonArray.getJSONObject(i);
                                String user_id = job.getString("id");
                                String name = job.getString("name");
                                String username = job.getString("username");
                                String mobile = job.getString("mobile");
                                String image = job.getString("image");

                                AppPreference.setUser_Id(NewUser_Profile_Activity.this, user_id);

                                     if (Connectivity.isConnected(NewUser_Profile_Activity.this)){
                                         String user_id1=AppPreference.getUser_Id(NewUser_Profile_Activity.this);
                                         String url=Base_Url.BaseUrl+Base_Url.get_profile;
                                         getProfile(url,user_id1);
                                     }else {
                                         Toast.makeText(NewUser_Profile_Activity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                                     }
                            }

                                // AppPreference.setMobile(Create_registration.this, et_mobile);
//                                Intent intent=new Intent(NewUser_Profile_Activity.this, Profile_setup_Activity.class);
//                                startActivity(intent);
//                                finish();
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

    //*****************************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {

            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = NewUser_Profile_Activity.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();

            if(destination !=null)
            {
                filenew=destination.getAbsolutePath();

            }else {
                Toast.makeText(NewUser_Profile_Activity.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, ""+destination, Toast.LENGTH_SHORT).show();
            try {
                bm = MediaStore.Images.Media.getBitmap(NewUser_Profile_Activity.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        iv_profile.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            if(destination !=null)
            {
                filenew=destination.getAbsolutePath();
                // Toast.makeText(getActivity(), "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(NewUser_Profile_Activity.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv_profile.setImageBitmap(thumbnail);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
           progressDialog.dismiss();
        }catch (Exception e){

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
