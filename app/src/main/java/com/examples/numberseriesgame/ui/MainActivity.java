package com.examples.numberseriesgame.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.numberseriesgame.Game;
import com.examples.numberseriesgame.R;
import com.examples.numberseriesgame.Utils.Question;
import com.examples.numberseriesgame.Utils.Util;
import com.examples.numberseriesgame.data.MyDatabase;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences spGame;
    private SharedPreferences.Editor editor;

    private TextView tvScore, tvFullName, tvAge, tvNumQuestion;

    private TextView tv0to0, tv0to1, tv0to2;
    private TextView tv1to0, tv1to1, tv1to2;
    private TextView tv2to0, tv2to1, tv2to2;

    private EditText etEnterNumber;
    private Button btnCheck;
    private Button btnNewGame;

    private Game currentGame;
    private List<Question> listQuestions;
    private Question question;
    private int numberQuestion = 0;

    private Toast toast;
    private TextView tv_toast;
    private View viewToast;


    private MyDatabase database;
    private String fullName;
    private Calendar calendar = Calendar.getInstance();
    private boolean isStore = false;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.str_theGame));

        spGame = getSharedPreferences(RegisterActivity.PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        editor = spGame.edit();
        fullName = spGame.getString(RegisterActivity.PREF_KEY_FUllNAME, "No Name");

        initView();
        initToast();
        setNameAndAge();

        currentGame = new Game();
        listQuestions = currentGame.getListQuestion();
        question = listQuestions.get(numberQuestion);
        setDataInViews(question);

        database = MyDatabase.getINSTANCE(this);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EnterNumber = etEnterNumber.getText().toString();
                if (!EnterNumber.isEmpty() && numberQuestion < 5) {
                    checkNumber(EnterNumber);
                    numberQuestion++;
                    tvNumQuestion.setText(numberQuestion + "/5");
                    if (numberQuestion < 5) {
                        goToNextQuestion();
                    } else {
                        Util.makeToast(MainActivity.this, getString(R.string.str_Qes_Over));
                        setGameInDataBase(currentGame);
                    }

                } else {
                    if (numberQuestion == 5) {
                        Util.makeToast(MainActivity.this, getString(R.string.str_P_Click_NGame));

                    } else {
                        Util.makeToast(MainActivity.this, getString(R.string.str_P_EnterNum));
                    }

                }

            }
        });


        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberQuestion > 4) {

                }
                startNewGame();
                //    setDataInViews();
            }
        });


    }

    private void startNewGame() {
        isStore = false;
        numberQuestion = 0;
        tvScore.setText("0");
        tvNumQuestion.setText(numberQuestion + "/5");
        currentGame = new Game();
        listQuestions = currentGame.getListQuestion();
        goToNextQuestion();

    }

    private void goToNextQuestion() {
        question = listQuestions.get(numberQuestion);
        setDataInViews(question);
    }

    private void setGameInDataBase(Game game) {
        if (!isStore) {
            game.setFullName(fullName);
            game.setGameDate(getCurrentDate());
            isStore = database.insertGame(game);
        }
    }

    private void checkNumber(String enterNumber) {
        int number = Integer.parseInt(enterNumber);
        if (number == question.getHiddenNumber()) {
            numberIsTrue();
        } else {
            numberIsFalse();
        }
    }

    private void numberIsFalse() {
        viewToast.getRootView().setBackgroundColor(getResources().getColor(R.color.red));
        tv_toast.setText(R.string.str_wrong_num);
        etEnterNumber.setText("");
        startSong(R.raw.wrong);

        toast.show();
    }

    private void startSong(int songId) {
        mediaPlayer = MediaPlayer.create(this, songId);
        mediaPlayer.start();
    }

    private void numberIsTrue() {
        int score = Integer.parseInt(tvScore.getText().toString());
        currentGame.setScore(score + 20);
        tvScore.setText(String.valueOf(currentGame.getScore()));
        viewToast.getRootView().setBackgroundColor(getResources().getColor(R.color.green));
        tv_toast.setText(R.string.str_great);
        etEnterNumber.setText("");
        startSong(R.raw.correct);
        toast.show();

    }


    private void setNameAndAge() {
        tvFullName.setText(fullName);
        tvAge.setText("[" + calcAge() + "]");
    }

    private void initToast() {
        toast = new Toast(MainActivity.this);
        viewToast = LayoutInflater.from(MainActivity.this).inflate(R.layout.cusrom_toast, null);
        tv_toast = viewToast.findViewById(R.id.tv_toast);
        toast.setView(viewToast);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.item_logout:
                editSpToLogout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editSpToLogout() {
        editor.putString(RegisterActivity.PREF_KEY_EMAIL, "");
        editor.putString(RegisterActivity.PREF_KEY_PASSWORD, "");
        editor.putBoolean(LoginActivity.CHECK_BOX_VALUE, false);
        editor.apply();
    }

    private void setDataInViews(Question question) {
        String[][] data = question.getData();
        tv0to0.setText(data[0][0]);
        tv0to1.setText(data[0][1]);
        tv0to2.setText(data[0][2]);
        tv1to0.setText(data[1][0]);
        tv1to1.setText(data[1][1]);
        tv1to2.setText(data[1][2]);
        tv2to0.setText(data[1][0]);
        tv2to1.setText(data[2][1]);
        tv2to2.setText(data[2][2]);
        Util.makeToast(MainActivity.this, question.getHiddenNumber() + "");
    }

    private void initView() {
        tvFullName = findViewById(R.id.tv_NameUser);
        etEnterNumber = findViewById(R.id.et_enterNumber);
        btnCheck = findViewById(R.id.btn_Check);
        btnNewGame = findViewById(R.id.btn_newGame);
        tvScore = findViewById(R.id.tv_score);
        tvAge = findViewById(R.id.tv_ageUser);
        tvNumQuestion = findViewById(R.id.tv_numQuestion);

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

    private int calcAge() {
        int currentYear = calendar.get(Calendar.YEAR);
        int birthYear = spGame.getInt(RegisterActivity.PREF_KEY_BIRTH_YEAR, 0);
        return currentYear - birthYear;
    }

    private String getCurrentDate() {
        String currentDate = calendar.get(Calendar.YEAR) + "/" +
                calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH);

        return currentDate;
    }


}