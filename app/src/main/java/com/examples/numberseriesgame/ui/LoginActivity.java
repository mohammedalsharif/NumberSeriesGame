package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.examples.numberseriesgame.MainActivity;
import com.examples.numberseriesgame.R;

public class LoginActivity extends AppCompatActivity {
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
        setTitle("LoginActivity");
        initViews();
        spLogIn = getSharedPreferences(RegisterActivity.PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        editor = spLogIn.edit();
        Intent intent = getIntent();

        if (spLogIn.getBoolean("CheckBoxValue", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
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
                    Toast.makeText(LoginActivity.this, "Please full all field", Toast.LENGTH_SHORT).show();
                } else {
                    if (username.equals(spLogIn.getString(RegisterActivity.PREF_KEY_USERNAME, "")) &&
                            userPassword.equals(spLogIn.getString(RegisterActivity.PREF_KEY_PASSWORD, ""))) {

                        if (cbRemember.isChecked()) {
                            editor.putBoolean("CheckBoxValue", true);
                            editor.apply();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "The password or username is incorrect", Toast.LENGTH_SHORT).show();
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