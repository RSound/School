package com.zjp.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zjp.view.R;
import com.zjp.view.login.LoginActivity;
import com.zjp.view.login.Session;

public class MainActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }


        Button exit = (Button)findViewById(R.id.exit);



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });


    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}
