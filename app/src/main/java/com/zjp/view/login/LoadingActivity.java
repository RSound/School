package com.zjp.view.login;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zjp.view.R;


/**
 * 自定义dialog
 */
public class LoadingActivity extends Dialog {

    private Context context;
    private static LoadingActivity dialog;
    private ImageView ivProgress;


    public LoadingActivity(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingActivity(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }
    //显示dialog的方法
    public static LoadingActivity showDialog(Context context){
        dialog = new LoadingActivity(context, R.style.MyDialogStyle);//dialog样式
        dialog.setContentView(R.layout.activity_loading);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        return dialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && dialog != null){
            ivProgress = (ImageView) dialog.findViewById(R.id.ivProgress);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_progress_anim);
            ivProgress.startAnimation(animation);
        }
    }
}
