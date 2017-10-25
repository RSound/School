package com.zjp.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjp.view.R;

/**
 * Created by zjp on 2017/10/25
 */

public class MessageFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View messageLayout = inflater.inflate(R.layout.message_layout,container,false);
        return messageLayout;
    }
}
