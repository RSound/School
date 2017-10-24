package com.zjp.view.login;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.zjp.view.R;


public class CommonDialog extends Dialog {



    /**
     * 输入的信息
     */
    private EditText new_password;

    /**
     * 确认和取消按钮
     */
    private Button negtiveBn, positiveBn;


    public CommonDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    private String positive, negtive;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {

        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        } else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        } else {
            negtiveBn.setText("取消");
        }


    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = findViewById(R.id.dialog_cancel);
        positiveBn = findViewById(R.id.dialog_sure);
        new_password = findViewById(R.id.new_passwords);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }


    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegtive() {
        return negtive;
    }

    public CommonDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public CommonDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }

    public EditText getNew_password() {
        return new_password;
    }

    public void setNew_password(EditText new_password) {
        this.new_password = new_password;
    }


    //返回键直接退出
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

           this.dismiss();

            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}