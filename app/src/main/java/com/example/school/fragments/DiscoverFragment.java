package com.example.school.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.activity.DiscoverActivity;
import com.example.school.adapter.BaseRecyclerAdapter;
import com.example.school.listeners.GlideImageLoader;
import com.example.school.listeners.OnItemClickListener;
import com.example.school.model.Discovery;
import com.example.school.net.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment
{

    private Banner banner;
    private List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private RecyclerView recyclerview;
    private BaseRecyclerAdapter adapter;

    public DiscoverFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        requestData();
        banner = view.findViewById(R.id.icon_banner);
        recyclerview = view.findViewById(R.id.recyclerview);
        //设置banner样式
        images.add(R.mipmap.a1);
        images.add(R.mipmap.a2);
        images.add(R.mipmap.a3);
        titles.add(" ");
        titles.add(" ");
        titles.add(" ");
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerTitles(titles);
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        adapter = new BaseRecyclerAdapter()
        {
            @Override
            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull final int position)
            {
                Discovery discovery = discoveries.get(position);
                TextView title = holder.getView(R.id.dis_item_title);
                TextView content = holder.getView(R.id.dis_item_content);
                Log.e("Asd", discovery.adContent);
                title.setText(discovery.adContent);
                content.setText(discovery.activeContent);

            }

            @Override
            protected int getLayoutResId(int position)
            {
                return R.layout.item_discover;
            }

            @Override
            public int getItemCount()
            {
                return discoveries.size();
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(@NonNull int position)
            {
                startActivity(new Intent(getActivity(), DiscoverActivity.class).putExtra("discoverco", discoveries.get(position).adContent)
                        .putExtra("discoverti", discoveries.get(position).activeContent));
            }
        });
        recyclerview.setAdapter(adapter);


        return view;

    }

    private void requestData()
    {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "");
        final Request request = new Request.Builder()
                .url(Urls.FIND_DISCOVER)
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
                msg.obj = response.body().string();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    private List<Discovery> discoveries = new ArrayList<>();
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                String string = (String) msg.obj;
                Gson gson1 = new Gson();
                discoveries = gson1.fromJson(string, new TypeToken<List<Discovery>>()
                {
                }.getType());
            }
        }
    };
}
