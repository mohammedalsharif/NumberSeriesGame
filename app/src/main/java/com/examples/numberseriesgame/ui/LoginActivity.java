package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.examples.numberseriesgame.R;
import com.examples.numberseriesgame.Utils.Util;

public class LoginActivity extends AppCompatActivity {
    public static final String CHECK_BOX_VALUE="CheckBoxValue";
    private ImageView imUser;
    private EditText etUserName, etPassword;
    private Button btnLogin, btnRegister;
    private CheckBox cbRemember;
    private SharedPreferences spLogIn;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.str_Login));
        initViews();
        spLogIn = getSharedPreferences(RegisterActivity.PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        editor = spLogIn.edit();
        if (spLogIn.getBoolean(CHECK_BOX_VALUE, false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra(RegisterActivity.PREF_KEY_USERNAME);
            String password = intent.getStringExtra(RegisterActivity.PREF_KEY_PASSWORD);
            etUserName.setText(userName);
            etPassword.setText(password);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUserName.getText().toString();
                String userPassword = etPassword.getText().toString();
                if (username.isEmpty() || userPassword.isEmpty()) {
                    Util.makeToast(LoginActivity.this, getResources().getString(R.string.str_PFullAllData));
                } else {
                    String userNameInSP=spLogIn.getString(RegisterActivity.PREF_KEY_USERNAME, "");
                    String PasswordInSP=spLogIn.getString(RegisterActivity.PREF_KEY_PASSWORD, "");
                    if (username.equals(username)&&userPassword.equals(PasswordInSP)) {
                        if (cbRemember.isChecked()) {
                            editor.putBoolean(CHECK_BOX_VALUE, true);
                            editor.apply();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                      Util.makeToast(LoginActivity.this,getResources().getString( R.string.incorrectUserOrPass));
                    }

                }
            }
        });
    }

    private void initViews() {
        imUser = findViewById(R.id.im_LogoIn);
        etUserName = findViewById(R.id.et_NameLogIn);
        etPassword = findViewById(R.id.et_PasswordLogIn);
        btnLogin = findViewById(R.id.btn_LogIn);
        btnRegister = findViewById(R.id.btn_Register);
        cbRemember = findViewById(R.id.cb_remember);
    }
}