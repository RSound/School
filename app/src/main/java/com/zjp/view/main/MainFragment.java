package com.zjp.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjp.view.R;

/**
 * Created by zjp on 2017/10/25
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.main_layout,container,false);
        return contactsLayout;
    }
}
