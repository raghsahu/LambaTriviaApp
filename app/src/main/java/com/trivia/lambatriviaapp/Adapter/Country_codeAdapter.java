package com.trivia.lambatriviaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.lambatriviaapp.Activity.SignIn_SignUp_Activity;
import com.trivia.lambatriviaapp.Model_Class.Country_CodeModel;
import com.trivia.lambatriviaapp.R;

import java.util.ArrayList;

public class Country_codeAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList data;
    ArrayList<Country_CodeModel>country_codeModels;
    Country_CodeModel tempValues=null;
    LayoutInflater inflater;


    public Country_codeAdapter(SignIn_SignUp_Activity signIn_signUp_activity,
                               int spinner_rows, ArrayList<Country_CodeModel> countryCodeModelArrayList) {
        super(signIn_signUp_activity,spinner_rows);
        activity = signIn_signUp_activity;
        data     = countryCodeModelArrayList;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
       // View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_rows, parent, false);


        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (Country_CodeModel) data.get(position);

        TextView label        = (TextView)row.findViewById(R.id.company);
        TextView sub          = (TextView)row.findViewById(R.id.sub);
        ImageView companyLogo = (ImageView)row.findViewById(R.id.image);

        if(position==0){

            // Default selected Spinner item
            label.setText("contry");
            sub.setText("");
        }
        else
        {
            // Set values for spinner each row
            label.setText(tempValues.callingCodes);
            //sub.setText(tempValues.callingCodes);
            //companyLogo.setImageResource();

        }




        return row;
    }
}
