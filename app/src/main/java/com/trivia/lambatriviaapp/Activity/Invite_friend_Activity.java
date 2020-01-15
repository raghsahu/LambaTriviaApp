package com.trivia.lambatriviaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.trivia.lambatriviaapp.MainActivity;
import com.trivia.lambatriviaapp.R;

public class Invite_friend_Activity extends AppCompatActivity {

    TextView tv_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend_);

        tv_skip=findViewById(R.id.tv_skip);
        hideKeyboard(Invite_friend_Activity.this);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Invite_friend_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void hideKeyboard(Invite_friend_Activity activity) {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


}
