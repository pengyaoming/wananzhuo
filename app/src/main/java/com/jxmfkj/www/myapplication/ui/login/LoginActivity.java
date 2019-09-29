package com.jxmfkj.www.myapplication.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.LoginEntity;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.api.ApiServer;
import com.jxmfkj.www.myapplication.api.RetrofitUtil;
import com.jxmfkj.www.myapplication.base.BaseEntity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 */
public class LoginActivity extends RxAppCompatActivity {
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
        RetrofitUtil.getInstance(this)
                .create(ApiServer.class)
                .getLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<LoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<LoginEntity> registerEntity) {
                        if (registerEntity.getErrorCode() != 0 || registerEntity.getData() == null) {
                            Toast.makeText(LoginActivity.this,registerEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            int id = registerEntity.getData().getId();
                            SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("name", registerEntity.getData().getNickname());
                            edit.putInt("id", registerEntity.getData().getId());
                            edit.commit();
                            Intent data = new Intent();
                            data.putExtra("linkId", id);
                            data.putExtra("name", registerEntity.getData().getNickname());
                            setResult(15, data);
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
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
