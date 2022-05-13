package com.examples.numberseriesgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.numberseriesgame.Utils.Question;
import com.examples.numberseriesgame.Utils.Util;
import com.examples.numberseriesgame.ui.RegisterActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView tvScore, tvUserName, tvAge;
    private SharedPreferences spGame;

    private TextView tv0to0, tv0to1, tv0to2;
    private TextView tv1to0, tv1to1, tv1to2;
    private TextView tv2to0, tv2to1, tv2to2;

    private EditText etEnterNumber;
    private Button btnCheck;
    private Button btnNewGame;

    Question question = Util.generateQuestion();
    String[][] data = question.getData();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("GameActivity");
        initView();
        spGame = getSharedPreferences(RegisterActivity.PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        tvUserName.setText(spGame.getString(RegisterActivity.PREF_KEY_USERNAME, "No Name"));
        //  tvAge.setText(spGame.getString(RegisterActivity.PREF_KEY_USERNAME,"No Name"));
        setDataInViews();


        // tv1to1.setText(question.getHiddenNumber() + "");
        Toast.makeText(this, question.getHiddenNumber() + "", Toast.LENGTH_SHORT).show();

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EnterNumber = etEnterNumber.getText().toString();

                if (EnterNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                } else {
                    int number = Integer.parseInt(EnterNumber);
                    if (number == question.getHiddenNumber()) {
                        Toast.makeText(MainActivity.this, "Great", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "loss", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvScore.setText("0");
                question = Util.generateQuestion();
                data = question.getData();
                setDataInViews();
            }
        });


    }

    private void setDataInViews() {
        tv0to0.setText(data[0][0]);
        tv0to1.setText(data[0][1]);
        tv0to2.setText(data[0][2]);
        tv1to0.setText(data[1][0]);
        tv1to1.setText(data[1][1]);
        tv1to2.setText(data[1][2]);
        tv2to0.setText(data[1][0]);
        tv2to1.setText(data[2][1]);
        tv2to2.setText(data[2][2]);
    }

    private void initView() {
        tvUserName = findViewById(R.id.tv_NameUser);
        etEnterNumber = findViewById(R.id.et_enterNumber);
        btnCheck = findViewById(R.id.btn_Check);
        btnNewGame = findViewById(R.id.btn_newGame);
        tvScore = findViewById(R.id.tv_score);

        tv0to0 = findViewById(R.id.tv_0to0);
        tv0to1 = findViewById(R.id.tv_0to1);
        tv0to2 = findViewById(R.id.tv_0to2);

        tv1to0 = findViewById(R.id.tv_1to0);
        tv1to1 = findViewById(R.id.tv_1to1);
        tv1to2 = findViewById(R.id.tv_1to2);

        tv2to0 = findViewById(R.id.tv_2to0);
        tv2to1 = findViewById(R.id.tv_2to1);
        tv2to2 = findViewById(R.id.tv_2to2);

    }

    private void calcAge(){
        Calendar calendar=Calendar.getInstance();
    }


}