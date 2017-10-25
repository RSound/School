package com.zjp.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zjp.view.R;
import com.zjp.view.common.CommonDialog;
import com.zjp.view.login.LoginActivity;
import com.zjp.view.login.Session;

/**
 * Created by zjp on 2017/10/25
 */

public class SettingFragment extends Fragment {

    private Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.setting_layout,container,false);


        session = new Session(getActivity());
        if(!session.loggedin()){
            logout();
        }


        Button exit = settingLayout.findViewById(R.id.exit);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CommonDialog dialog = new CommonDialog(getActivity());

                dialog.getWindow().setGravity(Gravity.CENTER);

                dialog.setMytitle("提示");
                dialog.setMycontent("确定退出登录");
                dialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {

                        logout();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegtiveClick() {

                        dialog.dismiss();
                    }
                }).show();




            }
        });

        return settingLayout;
    }

    private void logout(){
        session.setLoggedin(false);
        getActivity().finish();
        startActivity(new Intent(getActivity(),LoginActivity.class));
    }

}
