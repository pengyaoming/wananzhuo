package com.jxmfkj.www.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.BaseResponse;
import com.jxmfkj.www.myapplication.Entity.RegisterEntity;
import com.jxmfkj.www.myapplication.MainActivity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.RetrofitUitl;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtAccount, edtPassword, edtRepassword;
    private TextView tvRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_register);
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtRepassword);
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRegister:
                Register();
                break;
        }
    }

    private void Register() {
        String username = edtAccount.getText().toString();
        String password = edtPassword.getText().toString();
        String repassword = edtRepassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(RegisterActivity.this, "请输入完整的注册信息", Toast.LENGTH_LONG).show();
            return;
        }

        RetrofitUitl.getInstance().Api()
                .getRegister(username, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse registerEntityBaseResponse) {
                        if (registerEntityBaseResponse.getCode() != 0) {
                            Toast.makeText(RegisterActivity.this, registerEntityBaseResponse.getMsg(), Toast.LENGTH_LONG).show();

                        }else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
