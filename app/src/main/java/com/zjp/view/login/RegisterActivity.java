package com.zjp.view.login;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjp.view.R;
import com.zjp.view.main.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;


public class RegisterActivity extends AppCompatActivity {

    private EditText phone;
    private EditText password;
    private EditText state;//验证码

    private Button bt_state;
    private Button bt_register;

    private LoadingActivity loadingActivity;

    private  Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListener();


    }

    private void initView(){

        phone = (EditText)findViewById(R.id.phoneNumber);
        password = (EditText)findViewById(R.id.password);
        state = (EditText)findViewById(R.id.state);

        bt_register = (Button)findViewById(R.id.bt_register);
        bt_state = (Button)findViewById(R.id.bt_state);

        bt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMess();

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });

    }

    private void initListener(){

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    password.setSelection(s.length());
                }
            }
        });

    }


    public void register(){

        final String number=phone.getText().toString();
        final String pass = password.getText().toString();
        String stat  = state.getText().toString();


        if(number.equals(""))
        {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(number.length()!=11)
        {
            Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
            return;
        }
        if(pass.equals(""))
        {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        } if(pass.length()<6){

            Toast.makeText(this, "密码必须大于5位", Toast.LENGTH_LONG).show();
            return;

        }
        if(stat.length()==0)
        {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
            return;
        }else {

            BmobSMS.verifySmsCode(this, number, stat, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {

                        User user = new User();

                        user.setUsername(number);
                        user.setPassword(pass);

                        user.save(RegisterActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {

                                //用户登录状态保存（用户已登录）
                                session = new Session(RegisterActivity.this);
                                session.setLoggedin(true);


                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                                loadingActivity = LoadingActivity.showDialog(RegisterActivity.this);
                                loadingActivity.show();

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        //等待10000毫秒后销毁此页面，并提示注册成功
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        RegisterActivity.this.finish();

                                    }
                                }, 3000);


                            }

                            @Override
                            public void onFailure(int i, String s) {

                                loadingActivity = LoadingActivity.showDialog(RegisterActivity.this);
                                loadingActivity.show();

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        //等待10000毫秒后销毁此页面，并提示登陆失败

                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                }, 3000);

                            }
                        });

                    } else {

                        Toast.makeText(RegisterActivity.this, "信息输入错误", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }



    //检查网络连接
    public static boolean checkNetwork(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    //检查是否符合手机号标准
    public static boolean isPhoneNum(String phone)
    {
        Pattern pattern = Pattern.compile("((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$");
        Matcher m = pattern.matcher(phone);
        return m.matches();
    }


    //获取短信验证码
    public void getMess(){

        String number=phone.getText().toString();


        if(number.length()==0)
        {
            Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        if (number.length() != 11) {
            Toast.makeText(this, "请输入11位有效手机号码！", Toast.LENGTH_SHORT).show();
            return;
        } if(!checkNetwork(RegisterActivity.this)){
            Toast.makeText(this, "当前无网络连接，请连接网络楼重试！", Toast.LENGTH_LONG).show();
            return;
        }if(!isPhoneNum(number)){
            Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_LONG).show();
            return;
        }else {

            //进行获取验证码操作和倒计时1分钟操作
            BmobSMS.requestSMSCode(this, number, "短信模板", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        //发送成功时，让获取验证码按钮不可点击，且为灰色
                        bt_state.setClickable(false);
                        bt_state.setBackgroundColor(Color.GRAY);
                        Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用！", Toast.LENGTH_SHORT).show();
                        /**
                         * 倒计时1分钟操作
                         */
                        new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                                bt_state.setBackgroundResource(R.drawable.button_shape);
                                bt_state.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                                bt_state.setClickable(true);
                                bt_state.setBackgroundResource(R.drawable.button_shape);
                                bt_state.setText("重新发送");
                            }
                        }.start();

                    } else {
                        Toast.makeText(RegisterActivity.this, "此手机号操作过于频繁！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    //返回键直接退出
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
