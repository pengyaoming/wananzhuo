package com.jxmfkj.www.myapplication;

import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jxmfkj.www.myapplication.adapter.MainAdapter;
import com.jxmfkj.www.myapplication.ui.consult.ConsultFagment;
import com.jxmfkj.www.myapplication.ui.home.HomeFragment;
import com.jxmfkj.www.myapplication.ui.mine.MineFragment;
import com.jxmfkj.www.myapplication.ui.tixi.TiXiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private TabLayout tabLayout;
    private long clickTime = 0;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPage);
        tabLayout = findViewById(R.id.tablayout);
        List<String> titles = new ArrayList<>();
        titles.add("公众号");
        titles.add("体系");
        titles.add("首页");
        titles.add("我的");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.getInstance());
        fragments.add(TiXiFragment.getInstance());
        fragments.add(ConsultFagment.getInstance());
        fragments.add(MineFragment.getInstance());
        List<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.ic_insert_chart_black_24dp);
        integers.add(R.drawable.ic_book_normal);
        integers.add(R.drawable.ic_home_black_24dp);
        integers.add(R.drawable.ic_mine);
        adapter = new MainAdapter(getSupportFragmentManager(), integers, titles, this);
        adapter.setData(fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        //循环添加图片
        addlayout(titles);
    }

    private void addlayout(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            tabLayout.getTabAt(i).setText(titles.get(i));
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabVie(i));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                //如果两次的时间差＜1s，就不执行操作
            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
