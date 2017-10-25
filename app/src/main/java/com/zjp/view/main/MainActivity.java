package com.zjp.view.main;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjp.view.R;


/**
 * Created by zjp on 2017/10/25
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener{




    /**
     * 用于展示消息的Fragment
     */
    private MessageFragment messageFragment;

    /**
     * 用于展示主页的Fragment
     */
    private MainFragment mainFragment;

    /**
     * 用于展示课程表的Fragment
     */
    private TimeFragment timeFragment;

    /**
     * 用于展示设置的Fragment
     */
    private SettingFragment settingFragment;

    /**
     * 消息界面布局
     */
    private View messageLayout;

    /**
     * 主页界面布局
     */
    private View mainLayout;

    /**
     * 课程表界面布局
     */
    private View timeLayout;

    /**
     * 设置界面布局
     */
    private View settingLayout;

    /**
     * 在Tab布局上显示消息图标的控件
     */
    private ImageView messageImage;

    /**
     * 在Tab布局上显示主页图标的控件
     */
    private ImageView mainImage;

    /**
     * 在Tab布局上显示课程表图标的控件
     */
    private ImageView timeImage;

    /**
     * 在Tab布局上显示设置图标的控件
     */
    private ImageView settingImage;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView messageText;

    /**
     * 在Tab布局上显示主页标题的控件
     */
    private TextView mainText;

    /**
     * 在Tab布局上显示课程表标题的控件
     */
    private TextView timeText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView settingText;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化布局元素
        initViews();

        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    private void initViews() {
        messageLayout = findViewById(R.id.message_layout);
        mainLayout = findViewById(R.id.main_layout);
        timeLayout = findViewById(R.id.time_layout);
        settingLayout = findViewById(R.id.setting_layout);
        messageImage =  findViewById(R.id.message_image);
        mainImage = findViewById(R.id.main_image);
        timeImage =  findViewById(R.id.time_image);
        settingImage =  findViewById(R.id.setting_image);
        messageText = findViewById(R.id.message_text);
        mainText =  findViewById(R.id.main_text);
        timeText =  findViewById(R.id.time_text);
        settingText = findViewById(R.id.setting_text);
        messageLayout.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
        timeLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.main_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.time_layout:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.setting_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:

                // 当点击了消息tab时，改变控件的图片和文字颜色
                messageImage.setImageResource(R.drawable.messaged);
                messageText.setTextColor(Color.parseColor("#1296db"));


                if (messageFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.content, messageFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(messageFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mainImage.setImageResource(R.drawable.mained);
                mainText.setTextColor(Color.parseColor("#1296db"));
                if (mainFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mainFragment = new MainFragment();
                    transaction.add(R.id.content, mainFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mainFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                timeImage.setImageResource(R.drawable.timed);
                timeText.setTextColor(Color.parseColor("#1296db"));
                if (timeFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    timeFragment = new TimeFragment();
                    transaction.add(R.id.content, timeFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(timeFragment);
                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                settingImage.setImageResource(R.drawable.settinged);
                settingText.setTextColor(Color.parseColor("#1296db"));
                if (settingFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (timeFragment != null) {
            transaction.hide(timeFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }

    private void clearSelection() {
        messageImage.setImageResource(R.drawable.message);
        messageText.setTextColor(Color.parseColor("#82858b"));
        mainImage.setImageResource(R.drawable.main);
        mainText.setTextColor(Color.parseColor("#82858b"));
        timeImage.setImageResource(R.drawable.time);
        timeText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.setting);
        settingText.setTextColor(Color.parseColor("#82858b"));

    }
}
