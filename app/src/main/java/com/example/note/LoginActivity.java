package com.example.note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note.DB.userDB;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, passWord;
    private ImageView unameClear, pwdClear;
    private TextView touristLogin, userReg;
    private CheckBox rememberPw, autoLogin;
    private ImageView iv_icon;
    private Button login;
    private userDB userDB;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        loadSP();
        viewClick();
    }


    private void init() {
        userName = findViewById(R.id.et_userName);
        passWord = findViewById(R.id.et_password);
        unameClear = findViewById(R.id.iv_unameClear);
        pwdClear = findViewById(R.id.iv_pwdClear);
        iv_icon = findViewById(R.id.iv_icon);
        touristLogin = findViewById(R.id.link_tourist);
        userReg = findViewById(R.id.link_signup);
        login = findViewById(R.id.btn_login);
        rememberPw = findViewById(R.id.remember_pw);
        autoLogin = findViewById(R.id.auto_login);
    }

    private void loadSP() {
        sp = getSharedPreferences("userInfo", 0);
        ed = sp.edit();
        boolean rem_pwd = sp.getBoolean("REMEMBER_PW", false);
        boolean auto_login = sp.getBoolean("AUTO_LOGIN", false);
        rememberPw.setChecked(rem_pwd);
        autoLogin.setChecked(auto_login);
        String name = sp.getString("USER_NAME", "");
        String password = sp.getString("PASSWORD", "");
        if (rem_pwd) {
            userName.setText(name);
            passWord.setText(password);
        }

        if (auto_login) {
            login();
        }
    }


    private void login() {
        userDB = new userDB(LoginActivity.this);
        final String username = userName.getText().toString();
        final String password = passWord.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "帐号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = userDB.query(username, password);
        if (cursor.moveToNext()) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            intent.putExtra("login_user", username);

            cursor.close();
            if (rememberPw.isChecked()) {
                ed.putString("USER_NAME", username);
                ed.putString("PASSWORD", password);
                ed.putBoolean("REMEMBER_PW", true);
                ed.commit();
            }
            if (autoLogin.isChecked()) {
                ed.putBoolean("AUTO_LOGIN", true);
                ed.commit();
            }
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "密码验证失败，请重新验证登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void goReg() {
        Intent intent = new Intent(getApplicationContext(), RegActivity.class);
        startActivity(intent);
        finish();

    }

    private void viewClick() {
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goReg();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        touristLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("login_user", "tourist");
                startActivity(intent);
            }
        });

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (autoLogin.isChecked()) {
                    rememberPw.setChecked(true);
                }
                ed.putBoolean("AUTO_LOGIN", autoLogin.isChecked());
                ed.commit();
            }
        });
        rememberPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                ed.putBoolean("REMEMBER_PW", rememberPw.isChecked());
                ed.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            return;
        } else {
            Toast.makeText(getBaseContext(), "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }


}
