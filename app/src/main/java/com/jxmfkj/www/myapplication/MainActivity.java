package com.jxmfkj.www.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.Entity.ThereEntity;
import com.jxmfkj.www.myapplication.api.ApiAserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        request();
    }

    private void initView() {
        textView = findViewById(R.id.tvClick);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SwActivity.class));
            }
        });
    }

    private void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiAserver request = retrofit.create(ApiAserver.class);

        Call<ThereEntity> call = request.getCall();
        call.enqueue(new Callback<ThereEntity>() {
            @Override
            public void onResponse(Call<ThereEntity> call, Response<ThereEntity> response) {
                response.body().show();
            }

            @Override
            public void onFailure(Call<ThereEntity> call, Throwable t) {
                System.out.println("连接失败");
            }
        });
    }
}
