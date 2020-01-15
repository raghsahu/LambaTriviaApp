package com.trivia.lambatriviaapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trivia.lambatriviaapp.Activity.Invite_friend_Activity;
import com.trivia.lambatriviaapp.Adapter.ContactList_Adapter;
import com.trivia.lambatriviaapp.Model_Class.ContactModel;
import com.trivia.lambatriviaapp.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class Invite_Fragment extends Fragment {
    private static final int PERMISSION_REQUEST_CONTACT = 2;
    String phoneNumber;
    SearchView searchView;
    RecyclerView recycler_contact;
    ArrayList <ContactModel> Contact_list= new ArrayList<ContactModel>();
   // private ArrayAdapter<String> ContactAdapter;
    ContactList_Adapter contactList_adapter;
    boolean isPermitted;
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view= inflater.inflate(R.layout.invite_fragment, container, false);
        rootview=view;

        recycler_contact=view.findViewById(R.id.recycler_contact);

        checkRunTimePermission();

        //*********************************************
        searchView = (SearchView) view.findViewById(R.id.search_hint);
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        searchView.clearFocus();
        searchView.setFocusable(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });

    return view;
    }

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,Manifest.permission.SEND_SMS};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
            getNumber(getActivity().getContentResolver());
        }
    }
//*********************************************************
    private void filter(String newText) {
       // ArrayList<SearchProductList> temp = new ArrayList();
        ArrayList <ContactModel> Contact_li= new ArrayList<ContactModel>();
        for (ContactModel smodel : Contact_list) {
            //use .toLowerCase() for better matches
            if (smodel.getName().toLowerCase().startsWith(newText.toLowerCase())) {
                Contact_li.add(smodel);
            }else if (smodel.getPhoneNumber().toLowerCase().startsWith(newText.toLowerCase())){
                Contact_li.add(smodel);
            }
        }
        //update recyclerview
        contactList_adapter.updateList(Contact_li);

    }

//*************************
    public void getNumber(ContentResolver cr)
    {
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        // use the cursor to access the contacts
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            // get display name
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // get phone number
            System.out.println("......."+phoneNumber);
            Log.e("contact_list", phoneNumber);
            Log.e("contact_name", name);
            Contact_list.add(new ContactModel(name,phoneNumber));

            Log.e("contact_size",""+Contact_list.size());
        }

        phones.close();
//        ContactAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Contact_list);
//        recycler_contact.setAdapter(ContactAdapter);
        contactList_adapter= new ContactList_Adapter( getActivity(),Contact_list);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
        recycler_contact.setLayoutManager(mLayoutManger);
        recycler_contact.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycler_contact.setItemAnimator(new DefaultItemAnimator());
        recycler_contact.setAdapter(contactList_adapter);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
       // setHasOptionsMenu(true);
        // getActivity().setTitle("My Activity");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == 11111) {

            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                           // Toast.makeText(getActivity(), "Contact permission required", Toast.LENGTH_SHORT).show();
                           // alertView();
                        }
                    }
                }
            }

            try {
                getNumber(getActivity().getContentResolver());
            }catch (Exception e){

            }

            if (isPermitted){
               // getNumber(getActivity().getContentResolver());
            }else {
                //Toast.makeText(getActivity(), "Contact list not show", Toast.LENGTH_SHORT).show();
            }
                
        }
    }


    private void alertView() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Permission Denied")
                .setInverseBackgroundForced(true)
                //.setIcon(R.drawable.ic_info_black_24dp)
                .setMessage("Without those permission the app is unable to save your profile. App needs to save profile image in your external storage and also need to get profile image from camera or external storage.Are you sure you want to deny this permission?")

                .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                })
                .setPositiveButton("RE-TRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                        checkRunTimePermission();

                    }
                }).show();
    }



}
