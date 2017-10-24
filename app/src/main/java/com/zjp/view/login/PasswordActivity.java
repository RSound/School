package com.zjp.view.login;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.zjp.view.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class PasswordActivity extends AppCompatActivity {

    private Button button;
    private Button bt_send;
    private EditText phone;
    private EditText state;

    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initView();

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getMess();

            }
        });

        //修改密码按钮
        button = (Button) findViewById(R.id.btn_message_pass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updatePass();


            }

        });

    }

    public void initView(){

        bt_send = (Button) findViewById(R.id.btn_send);
        phone = (EditText)findViewById(R.id.et_phone);
        state = (EditText)findViewById(R.id.et_verify_code);

    }


    //判断验证码信息
    public void updatePass() {

        final String number = phone.getText().toString();
        String stat = state.getText().toString();


        if (number.equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (number.length() != 11) {
            Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
            return;
        }
        if (stat.length() == 0) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
            return;
        } else {

            BmobSMS.verifySmsCode(this, number, stat, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {

                    if (e == null) {

                        final CommonDialog dialog = new CommonDialog(PasswordActivity.this);
                        dialog.getWindow().setGravity(Gravity.TOP);
                        dialog.setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick() {

                        //获得输入信息
                        final String s = dialog.getNew_password().getText().toString();

                        if (s.toString().isEmpty()||!s.toString().matches("[A-Za-z0-9]+")) {

                            Toast.makeText(PasswordActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                            return;

                        }else if(s.length()<6){

                            Toast.makeText(PasswordActivity.this, "密码不能小于6位", Toast.LENGTH_SHORT).show();
                            return;

                        }else{

                            //获得objectID
                            BmobQuery<User> query = new BmobQuery<>();
                            query.addWhereEqualTo("username",number);
                            query.findObjects(PasswordActivity.this,new FindListener<User>() {
                                @Override
                                public void onSuccess(List<User> list) {

                                    for (User user :list){

                                    objectId = user.getObjectId();

                                    //更新数据
                                    User use = new User();
                                    use.setPassword(s);

                                       use.update(PasswordActivity.this, objectId, new UpdateListener() {

                                           @Override
                                           public void onSuccess() {

                                                  Toast.makeText(PasswordActivity.this,"密码修改成功！", Toast.LENGTH_SHORT).show();

                                                  return;
                                            }

                                           @Override
                                           public void onFailure(int code, String msg) {

                                               Toast.makeText(PasswordActivity.this,"密码修改失败！", Toast.LENGTH_SHORT).show();

                                               return;

                                         }
                                       });

                                    }

                                    Toast.makeText(PasswordActivity.this, "此用户没有注册！", Toast.LENGTH_LONG).show();

                                }

                        @Override
                        public void onError(int i, String s) {

                            Toast.makeText(PasswordActivity.this, "密码修改失败！", Toast.LENGTH_SHORT).show();

                        }
                    });

                    dialog.dismiss();

                }
            }

            // @Override
            public void onNegtiveClick() {

                dialog.dismiss();

            }

        }).show();

        //弹出软键盘
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }else {

         Toast.makeText(PasswordActivity.this, "信息输入错误", Toast.LENGTH_SHORT).show();

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
        } if(!checkNetwork(PasswordActivity.this)){
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
                        bt_send.setClickable(false);
                        bt_send.setBackgroundColor(Color.GRAY);
                        Toast.makeText(PasswordActivity.this, "验证码发送成功，请尽快使用！", Toast.LENGTH_SHORT).show();
                        /**
                         * 倒计时1分钟操作
                         */
                        new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                                bt_send.setBackgroundResource(R.drawable.button_shape);
                                bt_send.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                                bt_send.setClickable(true);
                                bt_send.setBackgroundResource(R.drawable.button_shape);
                                bt_send.setText("重新发送");
                            }
                        }.start();

                    } else {
                        Toast.makeText(PasswordActivity.this, "此手机号操作过于频繁！", Toast.LENGTH_SHORT).show();
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
