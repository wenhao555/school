package com.example.school.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.school.MainActivity;
import com.example.school.R;
import com.example.school.utils.PrefUtils;

public class WelcomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (PrefUtils.getString(WelcomeActivity.this, "name", "").equals("") || PrefUtils.getString(WelcomeActivity.this, "password", "").equals(""))
                {
                    Log.e("error", "no session");
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                } else
                {
//                    requestData(PrefUtils.getString(context, "name", ""), PrefUtils.getString(context, "password", ""));
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
