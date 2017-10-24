package com.zjp.view.login;

import cn.bmob.v3.BmobObject;

/**
 * Created by zjp on 2017/10/22.
 */

public class User extends BmobObject {

    private String username;
    private String password;
    private  String image;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
