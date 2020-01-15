package com.trivia.lambatriviaapp.Activity.PracticePlay_Acticvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.trivia.lambatriviaapp.R;
import com.trivia.lambatriviaapp.Session.SessionManager;

import static com.trivia.lambatriviaapp.MainActivity.total_coin;

public class Practice_quizActivity extends AppCompatActivity {
    Button btn_lets_play;
    SessionManager sessionManager;
    TextView tv_quiz_total_coin,tv_wallet_coin;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);

        sessionManager=new SessionManager(Practice_quizActivity.this);
        btn_lets_play=findViewById(R.id.btn_lets_play);
        tv_quiz_total_coin=findViewById(R.id.tv_quiz_total_coin);
        tv_wallet_coin=findViewById(R.id.tv_wallet_coin);
        iv_back=findViewById(R.id.iv_back);

        try {
            tv_wallet_coin.setText(total_coin);
            if (getIntent()!=null){
                tv_quiz_total_coin.setText(getIntent().getStringExtra("quiz_coin"));
                Log.e("quiz_coin_intent", getIntent().getStringExtra("quiz_coin"));
            }
        }catch (Exception e){
            Log.e("intent_error", e.toString());
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_lets_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // if (sessionManager.isPracticeRules()){
                    Intent intent=new Intent(Practice_quizActivity.this, ThreeSecondSplashActivity.class);
                    intent.putExtra("startgame", "practice");
                    startActivity(intent);
                    finish();
//                }else {
//                    Intent intent=new Intent(Practice_quizActivity.this, Start_RulesActivity.class);
//                    intent.putExtra("Practice_rule", getIntent().getStringExtra("Practice_rule"));
//                    startActivity(intent);
//                    finish();
//                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
