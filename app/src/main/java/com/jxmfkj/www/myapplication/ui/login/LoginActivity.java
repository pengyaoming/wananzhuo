package com.jxmfkj.www.myapplication.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.LoginEntity;
import com.jxmfkj.www.myapplication.Entity.RegisterEntity;
import com.jxmfkj.www.myapplication.MainActivity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.RetrofitUitl;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    private EditText edtAccount;
    private EditText edtPassword;
    private TextView tvClick;
    private TextView tvLogin;
    private TextView tvRegister, tvRegister1;
    private LinearLayout liner;
    private EditText edtRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


    }

    private void initView() {
        tvClick = findViewById(R.id.tvClick);
        tvRegister = findViewById(R.id.tvRegister);
        tvLogin = findViewById(R.id.tvLogin);
        liner = findViewById(R.id.liner);
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtRepassword);
        tvRegister1 = findViewById(R.id.tvRegister1);
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    /**
     * 登录接口
     */
    private void Login() {
        String username = edtAccount.getText().toString();
        String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请输入完整的注册信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 6 || password.length() < 6) {
            Toast.makeText(LoginActivity.this, "账号或密码不能小于6位数", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitUitl.getInstance().Api()
                .getLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<LoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<LoginEntity> registerEntity) {
                        if (registerEntity.getCode() != 0) {
                            Toast.makeText(LoginActivity.this, registerEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("name", registerEntity.getData().getNickname());
                            edit.putInt("id", registerEntity.getData().getId());
                            edit.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //注册接口
    private void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }
}
