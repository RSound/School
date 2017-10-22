package com.zjp.view.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjp.view.R;
import com.zjp.view.main.MainActivity;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**
 * Created by zjp on 2017/10/21
 */

public class LoginActivity extends FragmentActivity implements View.OnClickListener  {
    private ImageView logo;
    private ScrollView scrollView;
    private EditText username;
    private EditText password;
    private ImageView iv_clean_phone;
    private ImageView clean_password;
    private ImageView iv_show_pwd;
    private Button bt_login;
    private TextView forget_password;
    private TextView register;
    private Session session;
    private LoadingActivity loadingActivity;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private View service,content;
    private int height = 0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //连接服务器，获取数据
        Bmob.initialize(this, "d9c5aea1719c1e9522a5aa01af4d5ca8");

        if(isFullScreen(this)){
            AndroidBug.assistActivity(this);
        }
        initView();
        initListener();

        //判断之前是否登录，若登录则直接进入主页

        session = new Session(this);

        if(session.loggedin()){

            startActivity(new Intent(LoginActivity.this,MainActivity.class));

            finish();
        }



    }

    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN)==WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    private void initView() {

        logo = findViewById(R.id.logo);
        scrollView = findViewById(R.id.scrollView);
        username =  findViewById(R.id.et_mobile);
        password =  findViewById(R.id.et_password);
        iv_clean_phone =  findViewById(R.id.iv_clean_phone);
        clean_password =  findViewById(R.id.clean_password);
        iv_show_pwd =  findViewById(R.id.iv_show_pwd);
        bt_login =  findViewById(R.id.btn_login);
        forget_password =  findViewById(R.id.forget_password);
        register =  findViewById(R.id.regist);
        service = findViewById(R.id.service);
        content = findViewById(R.id.content);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }
    private void initListener() {
        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    password.setSelection(s.length());
                }
            }
        });

        //登录按钮，设置事件
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

        //注册，设置事件

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));


            }
        });

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>"+(oldBottom - bottom));
                    int dist = content.getBottom() - bottom;
                    if (dist>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomIn(logo, dist);
                    }
                    service.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>"+(bottom - oldBottom));
                    if ((content.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        zoomOut(logo);
                    }
                    service.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    /**
     * 缩小
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * f放大
     * @param view
     */
    public void zoomOut(final View view) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                username.setText("");
                break;
            case R.id.clean_password:
                password.setText("");
                break;
            case R.id.iv_show_pwd:
                if (password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    password.setSelection(pwd.length());
                break;
        }
    }

    //登录检测代码
    public void login() {

        String name = username.getText().toString();
        String pass = password.getText().toString();
        if (name.equals("") || pass.equals("")) {
            Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }


        BmobUser.loginByAccount(this,name, pass, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {

                    //用户登录状态保存（用户已登录）
                    session.setLoggedin(true);
                    //登录动画
                    loadingActivity.setTitle("正在登录");
                    loadingActivity = LoadingActivity.showDialog(LoginActivity.this);
                    loadingActivity.show();


                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //等待10000毫秒后销毁此页面，并提示登陆成功
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                } else {

                    loadingActivity.setTitle("正在登录");
                    loadingActivity = LoadingActivity.showDialog(LoginActivity.this);
                    loadingActivity.show();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //等待10000毫秒后销毁此页面，并提示登陆失败
                            Toast.makeText(LoginActivity.this, "登录出现错误", Toast.LENGTH_LONG).show();

                        }
                    }, 3000);


                }
            }
        });

    }
}
