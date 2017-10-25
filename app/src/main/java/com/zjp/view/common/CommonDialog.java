package com.zjp.view.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zjp.view.R;


/**
 * Created by zjp on 2017/10/25.
 */

public class CommonDialog extends Dialog{

    private Context context;

    private String mycontent;//内容

    private String mytitle;//标题


    private Button sure;
    private Button cancel;


    public String getMytitle() {
        return mytitle;
    }

    public void setMytitle(String mytitle) {
        this.mytitle = mytitle;
    }

    public String getMycontent() {
        return mycontent;
    }

    public void setMycontent(String mycontent) {
        this.mycontent = mycontent;
    }


    public CommonDialog(Context context){

        this(context,R.style.CommonDialog);

    }

    public CommonDialog(Context context,int themeResId){

        super(context,themeResId);
        this.context = context;

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog);

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

       TextView title = findViewById(R.id.dialog_text);
       TextView content = findViewById(R.id.dialog_content);

        content.setText(mycontent);
        title.setText(mytitle);

        sure = findViewById(R.id.dialog_sure);
        cancel = findViewById(R.id.dialog_cancel);

        //设置大小

        Window window = this.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        DisplayMetrics displayMetrics = content.getResources().getDisplayMetrics();

        layoutParams.width = (int)(displayMetrics.widthPixels*0.7);

        window.setAttributes(layoutParams);



        //确认取消按钮事件监听
        initEvent();

    }


    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 设置确定取消按钮的回调
     */
    public CommonDialog.OnClickBottomListener onClickBottomListener;

    public CommonDialog setOnClickBottomListener(CommonDialog.OnClickBottomListener onClickBottomListener) {
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

}
