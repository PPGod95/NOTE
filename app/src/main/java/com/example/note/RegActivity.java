package com.example.note;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note.DB.userDB;

public class RegActivity extends AppCompatActivity {
    private EditText userName, passWord, rePassword;
    private TextView userLogin;
    private Button register;
    private userDB userDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        userDB = new userDB(this);
        init();
        viewClick();
    }

    private void viewClick() {
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = userName.getText().toString();
                final String password = passWord.getText().toString();
                final String repassword = rePassword.getText().toString();
                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"帐号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!password.equals(repassword)){
                    Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = userDB.query(username,password);
                if (cursor.moveToNext()){
                    Toast.makeText(getApplicationContext(),"该用户已被注册，请重新输入",Toast.LENGTH_SHORT).show();
                    userName.requestFocus();
                }else{
                    userDB.insertUser(username,password);
                    cursor.close();
                    Toast.makeText(getApplicationContext(),"用户注册成功，请前往登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void init() {
        userName = findViewById(R.id.et_userName);
        passWord = findViewById(R.id.et_password);
        rePassword = findViewById(R.id.et_repassword);
        userLogin = findViewById(R.id.link_signup);
        register= findViewById(R.id.btn_login);
    }

}
