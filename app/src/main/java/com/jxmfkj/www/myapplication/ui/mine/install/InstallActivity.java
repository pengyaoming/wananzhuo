package com.jxmfkj.www.myapplication.ui.mine.install;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxmfkj.www.myapplication.Entity.MessageEvent;
import com.jxmfkj.www.myapplication.R;
import com.jxmfkj.www.myapplication.base.BaseActivity;
import com.jxmfkj.www.myapplication.ui.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class InstallActivity extends BaseActivity {
    @BindView(R.id.left_img)
    ImageView left_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_sign_out)
    LinearLayout tv_sign_out;
    @BindView(R.id.tv_login)
    LinearLayout tv_login;
    boolean isErr = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_install;
    }

    @OnClick({R.id.left_img, R.id.tv_sign_out, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_img:
                finish();
                break;
            case R.id.tv_sign_out:
                Dialog();
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }


    @Override
    public void initView() {
        Intent intent = getIntent();
        isErr = intent.getBooleanExtra("isErr", isErr);
        left_img.setVisibility(View.VISIBLE);
        tvTitle.setText("设置");
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        if (isErr) {
            tv_sign_out.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
        } else {
            tv_sign_out.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {


    }

    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("退出登录")
                .setMessage("是否退出登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new MessageEvent(false));
                        tv_sign_out.setVisibility(View.GONE);
                        tv_login.setVisibility(View.VISIBLE);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Message("取消操作，操作失败");
                    }
                });
        builder.create().show();
    }
}
