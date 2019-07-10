package com.example.school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.school.R;

public class MomentInfoActivity extends BaseActivity
{
    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_moment_info;
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

    @Override
    protected void initView()
    {
        TextView mominfo_content = findViewById(R.id.mominfo_content);
        mominfo_content.setText(getIntent().getStringExtra("content"));
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
        finish();
    }

}
