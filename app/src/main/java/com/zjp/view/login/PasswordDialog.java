package com.zjp.view.login;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.zjp.view.R;


public class PasswordDialog extends Dialog {



    /**
     * 输入的信息
     */
    private EditText new_password;

    /**
     * 确认和取消按钮
     */
    private Button negtiveBn, positiveBn;


    public PasswordDialog(Context context) {
        super(context, R.style.PasswordDialog);
    }

    private String positive, negtive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
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

    public PasswordDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
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

    public PasswordDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegtive() {
        return negtive;
    }

    public PasswordDialog setNegtive(String negtive) {
        this.negtive = negtive;
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