package com.example.school;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.school.fragments.DiscoverFragment;
import com.example.school.fragments.MineFragment;
import com.example.school.fragments.MomentsFragment;
import com.example.school.vies.FragmentTabHost;

public class MainActivity extends AppCompatActivity
{
    //定义数组来存放Fragment界面
    private Class mFragmentArray[] = {MomentsFragment.class, DiscoverFragment.class
            , MineFragment.class};
    //  定义数组来存放按钮图片
    private int mImageArray[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String mTextArray[] = {"交流", "发现", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        //实例化TabHost对象，得到TabHost
        if (tabHost != null)
        {
            tabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
            tabHost.getTabWidget().setDividerDrawable(null); //设置分割线，这里不显示，所以传了null
            //遍历Fram界面
            for (int i = 0; i < mFragmentArray.length; i++)
            {
                //为每一个Tab按钮设置图标、文字和内容
                TabHost.TabSpec tabSpec = tabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
                //将Tab按钮添加进Tab选项卡中
                tabHost.addTab(tabSpec, mFragmentArray[i], null);
            }
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index)
    {
        View view = View.inflate(this, R.layout.item_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab);
        textView.setText(mTextArray[index]);
        return view;

    }
}
