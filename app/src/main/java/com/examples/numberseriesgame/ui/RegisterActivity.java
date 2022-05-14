package com.examples.numberseriesgame.ui;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.examples.numberseriesgame.R;
import com.examples.numberseriesgame.Utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    public final static String PREF_NAME_USER_REGISTER = "UserRegister";
    public final static String PREF_KEY_FUllNAME = "fullName";
    public final static String PREF_KEY_EMAIL = "emailAddress";
    public final static String PREF_KEY_USERNAME = "userName";
    public final static String PREF_KEY_PASSWORD = "password";
    public final static String PREF_KEY_BIRTHDATE = "birthDate";
    public final static String PREF_KEY_GENDER_VALUE = "genderValue";
    public static final String PREF_KEY_BIRTH_YEAR = "birthYear";
    public static final String PREF_KEY_SPINNER = "spCountries";
    private EditText etFullName, etEmailAddress, etUserName, etPassword, etBirthDate;
    private RadioGroup rgGender;
    private RadioButton rbGenderChecked;
    private Button btnSave;
    private String fullName, emailAddress, userName, password, birthDate, genderValue;

    private Spinner spCountries;

    private SharedPreferences sharedRegister;
    //  للتصواصل مع الملف والتعديل عليه
    private SharedPreferences.Editor editor;
    private int yearDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle(getResources().getString(R.string.str_Register));

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
                    Util.makeToast(RegisterActivity.this, getResources().getString(R.string.str_PfullALl));

                } else {
                    editor.putString(PREF_KEY_FUllNAME, fullName);
                    editor.putString(PREF_KEY_EMAIL, emailAddress);
                    editor.putString(PREF_KEY_USERNAME, userName);
                    editor.putString(PREF_KEY_PASSWORD, password);
                    editor.putString(PREF_KEY_SPINNER, spCountries.getSelectedItem().toString());
                    editor.putString(PREF_KEY_BIRTHDATE, birthDate);
                    if (yearDate != 0) {
                        editor.putInt(PREF_KEY_BIRTH_YEAR, yearDate);
                    }
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
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        etBirthDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                        yearDate = year;
                    }
                }, Calendar.getInstance());

                datePickerDialog.show(getSupportFragmentManager(), null);
            }
        });

    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmailAddress = findViewById(R.id.et_Email);
        etUserName = findViewById(R.id.et_UserName);
        etPassword = findViewById(R.id.et_Password);
        spCountries = findViewById(R.id.sp_Countries);
        etBirthDate = findViewById(R.id.et_BirthDate);
        rgGender = findViewById(R.id.rg_Gender);
        btnSave = findViewById(R.id.btn_save);
    }
}