package com.jikexueyuan.secret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jikexueyuan.secret.atys.AtyTimeline;
import com.jikexueyuan.secret.atys.AtyLogin;

public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String token = Config.getCacheToken(this);
        String phone_num = Config.getCachePhoneNum(this);

        if(token!=null&&phone_num!=null){
            Intent i = new Intent(this,AtyTimeline.class);
            i.putExtra(Config.KEY_PHONE_NUM,phone_num);
            i.putExtra(Config.KEY_TOKEN,token);
            startActivity(i);
        }else {
            startActivity(new Intent(this,AtyLogin.class));
        }

        finish();

    }
}
