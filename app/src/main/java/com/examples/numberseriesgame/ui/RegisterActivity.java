package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.examples.numberseriesgame.R;

public class RegisterActivity extends AppCompatActivity {
    public final static String PREF_NAME_USER_REGISTER = "UserRegister";
    public final static String PREF_KEY_FUllNAME = "fullName";
    public final static String PREF_KEY_EMAIL = "emailAddress";
    public final static String PREF_KEY_USERNAME = "userName";
    public final static String PREF_KEY_PASSWORD = "password";
    public final static String PREF_KEY_BIRTHDATE = "birthDate";
    public final static String PREF_KEY_GENDER_VALUE = "genderValue";
    private EditText etFullName, etEmailAddress, etUserName, etPassword, etBirthDate;
    private RadioGroup rgGender;
    private RadioButton rbGenderChecked;
    private Button btnSave;
    private String fullName, emailAddress, userName, password, birthDate, genderValue;

    private SharedPreferences sharedRegister;
    //  للتصواصل مع الملف والتعديل عليه
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("RegisterActivity");
        initViews();
        //  MODE_PRIVATE  لا يمكن لأي تطبيق غير هذا التطبيق الوصول لهذا الملف
        sharedRegister = getSharedPreferences(PREF_NAME_USER_REGISTER, MODE_PRIVATE);
        editor = sharedRegister.edit();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = etFullName.getText().toString();
                emailAddress = etEmailAddress.getText().toString();
                userName = etUserName.getText().toString();
                password = etPassword.getText().toString();
                birthDate = etBirthDate.getText().toString();
                rbGenderChecked = findViewById(rgGender.getCheckedRadioButtonId());
                genderValue = rbGenderChecked.getText().toString();

                if (fullName.isEmpty() || emailAddress.isEmpty() || userName.isEmpty() || password.isEmpty() || birthDate.isEmpty() || genderValue.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please full all  field", Toast.LENGTH_SHORT).show();

                } else {
                    editor.putString(PREF_KEY_FUllNAME, fullName);
                    editor.putString(PREF_KEY_EMAIL, emailAddress);
                    editor.putString(PREF_KEY_USERNAME, userName);
                    editor.putString(PREF_KEY_PASSWORD, password);
                    editor.putString(PREF_KEY_BIRTHDATE, birthDate);
                    editor.putString(PREF_KEY_GENDER_VALUE, genderValue);
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra(PREF_KEY_USERNAME, userName);
                    intent.putExtra(PREF_KEY_PASSWORD, password);
                    startActivity(intent);
                    finish();
                }


            }
        });


        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmailAddress = findViewById(R.id.et_Email);
        etUserName = findViewById(R.id.et_UserName);
        etPassword = findViewById(R.id.et_Password);
        etBirthDate = findViewById(R.id.et_BirthDate);
        rgGender = findViewById(R.id.rg_Gender);
        btnSave = findViewById(R.id.btn_save);
    }
}