package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.Activity.LiveGameActivity.Start_RulesActivity;
import com.trivia.lambatriviaapp.Activity.Wallet_History.Wallet_transaction_Activity;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Connectivity.Connectivity;
import com.trivia.lambatriviaapp.Connectivity.Utility;
import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;
import com.trivia.lambatriviaapp.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Create_registration extends AppCompatActivity {

   // TextView tv_signup;
    LinearLayout tv_signin,tv_signup;
    ImageView iv_profile_capture,iv_profile;
    EditText et_user_name,et_pass,et_reffer,et_email;
     //String Et_refer=" ";
    File destination;
    EditText item_name,item_normal_price,item_offer_prise;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String userChoosenTask;
    String filenew;
    SessionManager manager;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_registration);
        manager=new SessionManager(Create_registration.this);

        tv_signin=findViewById(R.id.ll_login);
        tv_signup=findViewById(R.id.ll_signup);
       // et_reffer=findViewById(R.id.et_reffer);
        et_user_name=findViewById(R.id.et_user_name);
        et_pass=findViewById(R.id.et_pass);
        et_email=findViewById(R.id.et_email);
        //iv_profile_capture=findViewById(R.id.iv_profile_capture);
        //iv_profile=findViewById(R.id.iv_profile);

        mediaPlayer= MediaPlayer.create(Create_registration.this,R.raw.theme_song);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Create_registration.this, SignIn_SignUp_Activity.class);
                startActivity(intent);
            }
        });
//        iv_profile_capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImage();
//            }
//        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Et_refer=et_reffer.getText().toString();
                String Et_username=et_user_name.getText().toString();
                String Et_Pass=et_pass.getText().toString();
                String Et_email=et_email.getText().toString();

                if (!Et_username.isEmpty() && !Et_Pass.isEmpty() && !Et_email.isEmpty()){
                    if (Connectivity.isConnected(Create_registration.this)){
                        String url= Base_Url.BaseUrl+Base_Url.create_profile;
                         New_Regist(url,Et_username,Et_Pass,Et_email);

                    }else {
                        Toast.makeText(Create_registration.this, "Please check internet", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (Et_username.isEmpty()){
                        et_user_name.setError("Please enter username");
                    }else if (Et_Pass.isEmpty()){
                        et_pass.setError("Please enter password");
                    }else if (Et_email.isEmpty()){
                        et_email.setError("Please enter email id");
                    }

                }

             }

        });
    }

    //*****************************************************************
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Create_registration.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(Create_registration.this);

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

    private void New_Regist(String url, String et_username, String et_pass,  String et_email) {
        final ProgressDialog progressDialog = new ProgressDialog(Create_registration.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Log.e("show_reg_url"," "+url);
        AndroidNetworking.upload(url)
                .addMultipartParameter("username",et_username)
                .addMultipartParameter("password", et_pass)
                // .addMultipartParameter("refferal_code", et_refer)
                 //.addMultipartFile("image", destination)
                 .addMultipartParameter("email", et_email)
                // .setTag("show_menu")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("login_response = ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            Toast.makeText(Create_registration.this, ""+message, Toast.LENGTH_SHORT).show();
                            if (result.equalsIgnoreCase("true")){

                               // JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                               // for (int i = 0; i < jsonArray.length(); i++) {
                                   // JSONObject job = jsonArray.getJSONObject(i);
                                    String user_id = jsonObject1.getString("id");
                                    String username = jsonObject1.getString("username");
                                    String refferal_code = jsonObject1.getString("refferal_code");
                                    String email = jsonObject1.getString("email");

                                    AppPreference.setUser_Id(Create_registration.this, user_id);
                                manager.setLogin(true);
                                    //  }

                               // AppPreference.setMobile(Create_registration.this, et_mobile);
                                Intent intent=new Intent(Create_registration.this, MainActivity.class);
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
                        Log.e("error_reg_show", String.valueOf(anError));

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
            Cursor cursor = Create_registration.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();

            if(destination !=null)
            {
                filenew=destination.getAbsolutePath();

            }else {
                Toast.makeText(Create_registration.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, ""+destination, Toast.LENGTH_SHORT).show();
            try {
                bm = MediaStore.Images.Media.getBitmap(Create_registration.this.getContentResolver(), data.getData());
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
                Toast.makeText(Create_registration.this, "something wrong", Toast.LENGTH_SHORT).show();
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
    protected void onPause() {
        super.onPause();
        try {
            mediaPlayer.pause();
        }catch (Exception e){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mediaPlayer.stop();
        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mediaPlayer.stop();
        }catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mediaPlayer.stop();
        }catch (Exception e){

        }

    }

}
