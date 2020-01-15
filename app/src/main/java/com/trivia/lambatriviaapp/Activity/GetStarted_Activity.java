package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trivia.lambatriviaapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetStarted_Activity extends AppCompatActivity {

    Button get_start;

//    private static final int REQUEST_CODE_PERMISSION = 2;
//    String[] mPermission = {
//            Manifest.permission.INTERNET,
//            Manifest.permission.CAMERA,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.READ_SMS,
//            Manifest.permission.READ_CONTACTS,
//            Manifest.permission.SEND_SMS,
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_);

        get_start=findViewById(R.id.get_start);
        printHashKey();
       // CheckPermission();
        get_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GetStarted_Activity.this,SignIn_SignUp_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

   // private void CheckPermission() {

//        try {
//            if (ActivityCompat.checkSelfPermission(this, mPermission[0])
//                    != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[1])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[2])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[3])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[4])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[5])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[6])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[7])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[8])
//                            != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, mPermission[9])
//                            != PackageManager.PERMISSION_GRANTED)
//            {
//
//                ActivityCompat.requestPermissions(this,
//                        mPermission, REQUEST_CODE_PERMISSION);
//
//                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
//            }
//
//        }catch (Exception e){
//
//        }



 //   }

    private void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.trivia.lambatriviaapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }



   // @Override
   // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.e("Req Code", "" + requestCode);
//        System.out.println(grantResults[0] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[1] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[2] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[3] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[4] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[5] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[6] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[7] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[8] == PackageManager.PERMISSION_GRANTED);
//        System.out.println(grantResults[9] == PackageManager.PERMISSION_GRANTED);
//
//
//    }
}
