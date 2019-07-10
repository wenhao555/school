package com.example.school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.model.Discovery;

public class DiscoverActivity extends BaseActivity
{
    private TextView discover_title, discover_content;

    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_discover;
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
        setupToolbar();
        setActivityTitle("广告详情");
        String name = getIntent().getStringExtra("discoverco");
        String content = getIntent().getStringExtra("discoverti");
        discover_title = findViewById(R.id.discover_title);
        discover_content = findViewById(R.id.discover_content);
        discover_title.setText(name);
        discover_content.setText(content);
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
