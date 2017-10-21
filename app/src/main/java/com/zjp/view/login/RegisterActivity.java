package com.zjp.view.login;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjp.view.R;


public class RegisterActivity extends AppCompatActivity {

    private EditText phone;
    private EditText password;
    private EditText state;//验证码

    private Button bt_state;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListener();
        //BmobSMS.initialize(this,"d9c5aea1719c1e9522a5aa01af4d5ca8");

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

        String number=phone.getText().toString();
        String pass = password.getText().toString();
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
        }
        if(stat.length()==0)
        {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
            return;
        }else {

         /*  BmobSMS.verifySmsCode(this,number, pass, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {

                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        String name=phone.getText().toString();
                        String pass=password.getText().toString();

                        BmobUser user = new BmobUser();
                        user.setUsername(name);
                        user.setPassword(pass);

                        user.signUp(new SaveListener(){
                            @Override
                            public void done(Object o, cn.bmob.v3.exception.BmobException e) {

                                if(e == null){

                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });

                    } else {

                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });

*/
        }

    }





    //获取短信验证码
    public void getMess(){

        String number=phone.getText().toString();

        if(number.length()==0)
        {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if(number.length()!=11)
        {
            Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
            return;
        }
      /*  BmobSMS.requestSMSCode(this, number, "短信模板", new RequestSMSCodeListener() {

            @Override
            public void done(Integer integer, BmobException e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    //发送成功时，让获取验证码按钮不可点击，且为灰色
                    bt_state.setClickable(false);

                    Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            bt_state.setText(millisUntilFinished / 1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            bt_state.setClickable(true);

                            bt_state.setText("重新发送");
                        }
                    }.start();
                } else {
                    Toast.makeText(RegisterActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }

}
