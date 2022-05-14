package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examples.numberseriesgame.Game;
import com.examples.numberseriesgame.R;
import com.examples.numberseriesgame.Utils.Util;
import com.examples.numberseriesgame.data.MyDatabase;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private Button btnShowAllGames, btnShowLastGame, btnChangePass, btnClearGame;
    private EditText etNewPassword;
    private MyDatabase database;
    private ArrayList<Game> listGames;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getResources().getString(R.string.str_setting));
        initViews();
        sp = getSharedPreferences(RegisterActivity.PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        editor = sp.edit();
        database = MyDatabase.getINSTANCE(this);
        listGames = database.getAllGames();
        btnShowAllGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ShowAllGamesActivity.class));
            }
        });
        btnShowLastGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listGames.size() > 0) {
                    Game lastGame = listGames.get(listGames.size() - 1);
                    Util.makeToast(SettingsActivity.this, lastGame.getGameDate());
                } else {
                    Util.makeToast(SettingsActivity.this, getResources().getString(R.string.noRegiGames));
                }


            }
        });
        btnClearGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (database.deleteAllGAmes()) {
                    Util.makeToast(SettingsActivity.this, getResources().getString(R.string.str_removed));
                } else {
                    Util.makeToast(SettingsActivity.this, getResources().getString(R.string.str_noRegGame));
                }
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = etNewPassword.getText().toString();
                if (!newPassword.isEmpty()) {
                    editor.putString(RegisterActivity.PREF_KEY_PASSWORD, newPassword);
                    editor.apply();
                    Util.makeToast(SettingsActivity.this,getResources().getString(R.string.str_passChanged));
                    etNewPassword.setText("");
                } else {
                    Util.makeToast(SettingsActivity.this,getResources().getString(R.string.str_PleasEnNewPass));
                }

            }
        });
    }

    private void initViews() {
        btnShowAllGames = findViewById(R.id.btn_showAllGames);
        btnShowLastGame = findViewById(R.id.btn_showLastGames);
        btnChangePass = findViewById(R.id.btn_ChangePass);
        btnClearGame = findViewById(R.id.btn_clearGame);
        etNewPassword = findViewById(R.id.et_NewPassword);
    }
}