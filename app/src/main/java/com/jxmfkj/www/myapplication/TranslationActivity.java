package com.jxmfkj.www.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.Entity.Translation1;
import com.jxmfkj.www.myapplication.api.ApiAserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslationActivity extends AppCompatActivity {
    private TextView tvText,tvClick;
    private EditText edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        tvClick = findViewById(R.id.tvClick);
        tvText = findViewById(R.id.tvText);
        edtMessage = findViewById(R.id.edtMessage);
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtMessage.getText().toString())){
                    Toast.makeText(TranslationActivity.this,"请输入文字",Toast.LENGTH_LONG).show();
                    return;
                }else {
                request();
                
                }

            }
        });
    }

    private void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        ApiAserver request = retrofit.create(ApiAserver.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation1> call = request.getCall(edtMessage.getText().toString());
        call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                tvText.setText(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {
                System.out.println("请求失败");
                System.out.println(t.getMessage());

            }
        });
        
       
    }
}
