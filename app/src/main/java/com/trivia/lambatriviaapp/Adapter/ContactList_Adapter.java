package com.trivia.lambatriviaapp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trivia.lambatriviaapp.All_Url.Base_Url;
import com.trivia.lambatriviaapp.Model_Class.ContactModel;
import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactList_Adapter extends RecyclerView.Adapter<ContactList_Adapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "ContactList_Adapter";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private ArrayList<ContactModel> contactModels;
    ContactModel contactModel;
    public Context context;
    View viewlike;
     boolean isPermitted;
     String mobile_nmbr;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contact_name,contact_nmbr;
        Button btn_invite;
        int pos;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            contact_name=viewlike.findViewById(R.id.contact_name);
            contact_nmbr=viewlike.findViewById(R.id.contact_nmbr);
            btn_invite=viewlike.findViewById(R.id.btn_invite);


        }
    }

    public ContactList_Adapter(Context mContext, ArrayList<ContactModel> contactModelArrayList) {
        context = mContext;
        contactModels = contactModelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (contactModels.size() > 0) {
            contactModel = contactModels.get(position);

            viewHolder.contact_name.setText(contactModel.getName());
            viewHolder.contact_nmbr.setText(contactModel.getPhoneNumber());

        } else
        {
            Toast.makeText(context, "no record for list", Toast.LENGTH_SHORT).show();
        }


        viewHolder.btn_invite.setTag(viewHolder);

        viewHolder.pos = position;



        viewHolder.btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_nmbr=contactModels.get(position).getPhoneNumber();
                String user_id= AppPreference.getUser_Id(context);
//                Intent intent=new Intent(context, EditMenu_Activity.class);
//                intent.putExtra("menu_id", menu_id);
//                context.startActivity(intent);
                //Invite_frnds(mobile_nmbr,user_id);
                //Toast.makeText(context, "Invite Success", Toast.LENGTH_SHORT).show();
                sendSms(mobile_nmbr);
            }
        });


    }

    private void sendSms(String mobile_nmbr) {

        //Toast.makeText(context, "send sms", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.SEND_SMS)) {

                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.e("perm_if", "perm_if");
            } else {
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.e("perm_else", "perm_else");
            }
        }else {
            Log.e("perm_else_try", "perm_else");
            try{
                redirectSms(mobile_nmbr);
            }catch(Exception e){
                Log.e("send_sms_error", e.toString());
            }
        }
//************************new method*********
//        String[] permissionArrays = new String[]{Manifest.permission.SEND_SMS};
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions((Activity)context, permissionArrays, MY_PERMISSIONS_REQUEST_SEND_SMS);
//        } else {
//            // if already permition granted
//            Log.e("perm_else", "perm_else");
//            redirectSms(mobile_nmbr);
//            //Toast.makeText(context, "Invite Success", Toast.LENGTH_SHORT).show();
//        }


    }

    private void Invite_frnds(String mobile_nmbr, String user_id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        Log.e("invite_url", " " + Base_Url.BaseUrl + Base_Url.invite);
        AndroidNetworking.post(Base_Url.BaseUrl + Base_Url.invite)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("mobile", mobile_nmbr)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        progressDialog.dismiss();
                        Log.e("invite_response ", jsonObject.toString());

                        try {
                            progressDialog.dismiss();
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");

                            if (result.equals("true")) {
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                            //notifyDataSetChanged();
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
//*****************************************************************************

    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public void updateList(ArrayList<ContactModel> contact_li) {
        contactModels = contact_li;
        notifyDataSetChanged();

    }



    @Override
    public void onRequestPermissionsResult ( int requestCode, String permissions[],
                                             int[] grantResults)
    {
       //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                boolean openDialogOnce = true;
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    redirectSms(mobile_nmbr);
                    //SmsManager smsManager = SmsManager.getDefault();
                     //smsManager.sendTextMessage("7879014", null, "lamba quiz", null, null);
                   // Toast.makeText(context, "SMS sent.",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,"SMS faild, please try again.", Toast.LENGTH_LONG).show();

                }
                return;
//***************************************************************************
//                for (int i = 0; i < grantResults.length; i++) {
//                    String permission = permissions[i];
//
//                    isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
//
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        // user rejected the permission
//                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, permission);
//                        if (!showRationale) {
//                            //execute when 'never Ask Again' tick and permission dialog not show
//                        } else {
//
//                            if (openDialogOnce) {
//                                // Toast.makeText(getActivity(), "Contact permission required", Toast.LENGTH_SHORT).show();
//                                // alertView();
//                            }
//                        }
//                    }
//                }
//                if (isPermitted){
//                    redirectSms("123456");
//                }else {
//                    Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_SHORT).show();
//                }

            }
        }

    }



    private void redirectSms(String mobile_nmbr) {
       // Toast.makeText(context, "redirect Success", Toast.LENGTH_SHORT).show();
        Log.e("redirect_sms", "invite_sms");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "lamba quiz earn money (playstore.com)");
        sendIntent.putExtra("address"  , mobile_nmbr);
        //sendIntent.setData(Uri.parse("smsto:"));
        sendIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(sendIntent);
    }


}
