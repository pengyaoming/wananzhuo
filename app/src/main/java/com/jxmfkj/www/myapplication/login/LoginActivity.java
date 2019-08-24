package com.jxmfkj.www.myapplication.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.RegisterEntity;
import com.jxmfkj.www.myapplication.api.ApiAserver;
import com.jxmfkj.www.myapplication.MainActivity;
import com.jxmfkj.www.myapplication.R;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtAccount)
    EditText edtAccount;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.tvClick)
    TextView tvClick;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.liner)
    LinearLayout liner;
    @BindView(R.id.edtRepassword)
    EditText edtRepassword;

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
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLogin.setText("注册");
                liner.setVisibility(View.VISIBLE);
                tvClick.setVisibility(View.GONE);
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
            Toast.makeText(LoginActivity.this, "请输入完整的注册信息", Toast.LENGTH_LONG).show();
            return;
        }
        if (username.length() < 6 || password.length() < 6) {
            Toast.makeText(LoginActivity.this, "账号或密码不能小于6位数", Toast.LENGTH_LONG).show();
            return;
        }
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();//创建拦截对象

        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这一句一定要记得写，否则没有数据输出
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)  //设置打印拦截日志
//                .addInterceptor(new LogInterceptor())  //自定义的拦截日志，拦截简单东西用，后面会有介绍
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        final ApiAserver request = retrofit.create(ApiAserver.class);
        Call<RegisterEntity> call = request.getLogin(username, password);
        call.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    //注册接口
    private void register() {
        String username = edtAccount.getText().toString();
        String password = edtPassword.getText().toString();
        String repassword = edtRepassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(LoginActivity.this, "请输入完整的注册信息", Toast.LENGTH_LONG).show();
            return;
        }
        if (username.length() < 6 || password.length() < 6 || repassword.length() < 6) {
            Toast.makeText(LoginActivity.this, "账号或密码不能小于6位数", Toast.LENGTH_LONG).show();
            return;
        }
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();//创建拦截对象

        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这一句一定要记得写，否则没有数据输出
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)  //设置打印拦截日志
//                .addInterceptor(new LogInterceptor())  //自定义的拦截日志，拦截简单东西用，后面会有介绍
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        final ApiAserver request = retrofit.create(ApiAserver.class);
        Call<RegisterEntity> call = request.getRegister(username, password, repassword);
        call.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                onResume();
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {

            }
        });
    }
}
