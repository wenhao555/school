package com.example.school.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.model.Teacher;
import com.example.school.model.User;
import com.example.school.net.Urls;
import com.example.school.utils.PrefUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoActivity extends BaseActivity
{

    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_user_info;
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

    private EditText user_name, user_realname, user_stuname, user_password;
    private Button commit;

    @Override
    protected void initView()
    {
        setupToolbar();
        setActivityTitle("个人信息");
        user_name = findViewById(R.id.user_name);
        user_realname = findViewById(R.id.user_realname);
        user_stuname = findViewById(R.id.user_stuname);
        user_password = findViewById(R.id.user_password);
        user_name.setText(PrefUtils.getString(context, "name", ""));
        user_password.setText(PrefUtils.getString(context, "password", ""));
        user_stuname.setText(PrefUtils.getString(context, "stuName", ""));
        user_realname.setText(PrefUtils.getString(context, "realName", ""));
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                requestData();
            }
        });
    }

    private void requestData()
    {
        String name = user_name.getText().toString();
        String realname = user_realname.getText().toString();
        String password = user_password.getText().toString();
        String stuname = user_stuname.getText().toString();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();


        if (name.equals("") || password.equals("") || realname.equals(""))
        {
            Toast.makeText(UserInfoActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String Json = "";
        String urls = "";
        int rId = PrefUtils.getInt(context, "userRole", 0);
        if (rId == 1)
        {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setUserRole(PrefUtils.getInt(context, "userRole", 0));
            Gson gson = new Gson();
            urls = Urls.UPDATE_USERS;
            Json = gson.toJson(user);
        } else
        {
            Teacher user = new Teacher();
            user.setName(name);
            user.setPassword(password);
            user.setLoginId(PrefUtils.getString(context, "name", ""));
            Gson gson = new Gson();
            urls = Urls.UPDATETEACHER;
            Json = gson.toJson(user);
        }
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = response.body().string();
                mHandler.sendMessage(msg);
            }
        });
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            String name = user_name.getText().toString();
            String realname = user_realname.getText().toString();
            String password = user_password.getText().toString();
            String stuname = user_stuname.getText().toString();
            if (msg.what == 1)
            {
                String string = msg.obj.toString();
                if (string.equals("true"))
                {
                    PrefUtils.setString(context, "name", name);
                    PrefUtils.setString(context, "password", password);
                    PrefUtils.setString(context, "realName", realname);
                    PrefUtils.setString(context, "stuName", stuname);
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                {
                    Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

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
