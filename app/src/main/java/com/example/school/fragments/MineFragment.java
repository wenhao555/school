package com.example.school.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.school.R;
import com.example.school.activity.LoginActivity;
import com.example.school.activity.UserInfoActivity;
import com.example.school.utils.PrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment
{


    public MineFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        LinearLayout watch_info = view.findViewById(R.id.watch_info);
        watch_info.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
        Button commit = view.findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PrefUtils.setInt(getActivity(), "id", 999);
                PrefUtils.setString(getActivity(), "name", "");
                PrefUtils.setString(getActivity(), "password", "");
                PrefUtils.setInt(getActivity(), "userRole", 999);
                PrefUtils.setString(getActivity(), "realName", "");
                PrefUtils.setString(getActivity(), "stuName", "");
                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });
        return view;
    }

}
