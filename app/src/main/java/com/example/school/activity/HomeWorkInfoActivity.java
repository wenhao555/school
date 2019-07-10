package com.example.school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.school.R;

public class HomeWorkInfoActivity extends BaseActivity
{

    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_home_work_info;
    }

    @Override
    protected int returnMenuResource()
    {
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    private TextView home_info_title, home_info_content;
    private ImageView home_info_img;

    @Override
    protected void initView()
    {
        setupToolbar();
        setActivityTitle("作业详情");
        home_info_title = findViewById(R.id.home_info_title);
        home_info_content = findViewById(R.id.home_info_content);
        home_info_img = findViewById(R.id.home_info_img);
        
    }

    @Override
    protected void setupMenu(Menu menu)
    {

    }

    @Override
    protected void onViewClickListener(View view)
    {

    }

    @Override
    protected void onBackClickOpt()
    {

    }
}
