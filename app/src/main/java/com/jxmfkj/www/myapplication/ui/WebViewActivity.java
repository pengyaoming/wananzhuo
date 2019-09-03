package com.jxmfkj.www.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.jxmfkj.www.myapplication.R;

public class WebViewActivity extends AppCompatActivity {
    public AgentWeb  agentWeb;
    public LinearLayout web;
    public View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        web = findViewById(R.id.web);
    }

        

}
