package com.example.school.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.model.ClassCircle;
import com.example.school.model.CommunityCircle;
import com.example.school.model.User;
import com.example.school.net.Urls;
import com.example.school.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//发送数据
public class AddMomentsActivity extends BaseActivity
{
    private EditText add_moments;
    private Button commit, come_back;
    private ImageView add_img;
    private final int RESULT_LOAD_IMAGE = 1001;

    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_add_moments;
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

    private String momtype = "";

    @Override
    protected void initView()
    {
        setupToolbar();
        setActivityTitle("编辑");
        momtype = getIntent().getStringExtra("momtype");

        add_moments = findViewById(R.id.add_moments);
        commit = findViewById(R.id.commit);
        come_back = findViewById(R.id.come_back);
        add_img = findViewById(R.id.add_img);
        come_back.setOnClickListener(clickListener);
        commit.setOnClickListener(clickListener);
        add_img.setOnClickListener(clickListener);
        add_img.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("是否删除照片")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                add_img.setImageResource(R.drawable.addpicture);
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    protected void setupMenu(Menu menu)
    {

    }

    @Override
    protected void onViewClickListener(View view)
    {
        switch (view.getId())
        {
            case R.id.add_img:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
            case R.id.commit:
                requestData();
                break;
            case R.id.come_back:
                finish();
                break;
        }
    }

    private String picturePath = "";
    private File file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE)
        {
            // 从相册返回的数据
            if (data != null)
            {
                // 得到图片的全路径
                Uri uri = data.getData();
                add_img.setImageURI(uri);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                file = new File(picturePath);

                cursor.close();
            }
        }
    }

    public static String fileToBase64(File file)
    {
        String base64 = null;
        InputStream in = null;
        try
        {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return base64;
    }

    private void requestData()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        String url = "";
        String Json = "";
        if (momtype.equals("2"))
        {
            final CommunityCircle user = new CommunityCircle();
            user.setContent(add_moments.getText().toString());
            if (file != null)
            {
                user.setPicture(fileToBase64(file));
            }
            user.setUserName(PrefUtils.getString(context, "name", ""));
            user.setUserId(PrefUtils.getInt(context, "id", 0));
            user.setTeacherId(PrefUtils.getInt(context, "teacherId", 0));
            url = Urls.ADDCOMCIRCLE;
            Gson gson = new Gson();
            Json = gson.toJson(user);
        } else
        {
            ClassCircle classCircle = new ClassCircle();
            classCircle.setHomework(add_moments.getText().toString());
            classCircle.setTeacherId(PrefUtils.getInt(context, "id", 0));
            if (file != null)
            {
                classCircle.setPicture(fileToBase64(file));
            }
            url = Urls.ADDCLASSCIRCLE;
            Gson gson = new Gson();
            Json = gson.toJson(classCircle);
        }
        Log.e("传递Json", Json);
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()

                .url(url)
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
                if (Boolean.parseBoolean(response.body().string()) == true)
                {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } else
                    Toast.makeText(AddMomentsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {

                Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    protected void onBackClickOpt()
    {
        finish();
    }
}
