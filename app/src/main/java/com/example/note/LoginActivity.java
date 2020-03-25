package com.example.note;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, passWord;
    private ImageView unameClear, pwdClear;
    private TextView touristLogin, userReg;
    private CheckBox rememberPw, autoLogin;
    private ImageView iv_icon;
    private Button login;
    private UserDao userdao;
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
        sp = getSharedPreferences("userinfo",0);
        ed = sp.edit();
        boolean rem_pwd = sp.getBoolean("REMBER_PW",false);
        boolean auto_login = sp.getBoolean("AUTO_LOGIN",false);
        rememberPw.setChecked(rem_pwd);
        autoLogin.setChecked(auto_login);
        String name = sp.getString("USER_NAME","");
        String password = sp.getString("PASSWORD","");
        if (rem_pwd){
            userName.setText(name);
            passWord.setText(password);
        }
        Intent intent = getIntent();
        if (intent.getStringExtra("code")!=null){
            if(intent.getStringExtra("code").equals("relogin")){
                auto_login=false;
            }
        }
        if(auto_login){
            login();
        }
    }

    private void login() {
        userdao = new UserDao(LoginActivity.this);
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            System.exit(0);
            return;
        }
        else { Toast.makeText(getBaseContext(), "再按一次返回退出程序", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }


}
