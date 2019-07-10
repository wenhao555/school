package com.example.school.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.school.R;
import com.example.school.activity.MomentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MomentsFragment extends Fragment
{

    public MomentsFragment()
    {
        // Required empty public constructor
    }

    private LinearLayout watch_moment, watch_community;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_moments, container, false);
        watch_moment = view.findViewById(R.id.watch_moment);//班级圈
        watch_moment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), MomentActivity.class).putExtra("momtype", "1"));
            }
        });
        watch_community = view.findViewById(R.id.watch_community);//社区板块
        watch_community.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), MomentActivity.class).putExtra("momtype", "2"));
            }
        });
        return view;
    }

}
