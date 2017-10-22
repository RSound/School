package com.zjp.view.login;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by zjp on 2017/10/22.
 */

public class User extends BmobUser {


    private  String image;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
