package com.jikexueyuan.secret.atys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jikexueyuan.secret.Config;
import com.jikexueyuan.secret.ld.MyContacts;
import com.jikexueyuan.secret.net.Message;
import com.jikexueyuan.secret.net.Timeline;
import com.jikexueyuan.secret.net.UploadContacts;
import com.jikexueyuan.secret.tools.MD5Tool;

import org.json.JSONArray;

import java.util.List;
import java.util.PropertyPermission;

import secret.jikexueyuan.com.secret.R;

public class AtyTimeline extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        adapter = new AtyTimelineMessageListAdapter(this);
        setListAdapter(adapter);

        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        phone_md5 = MD5Tool.md5(phone_num);

        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));

        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                loadMessage();

                pd.dismiss();

            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode == Config.RESULT_STATUS_INVAILD_TOKEN) {
                    //重新登录
                    startActivity(new Intent(AtyTimeline.this, AtyLogin.class));
                    finish();
                } else {
                    loadMessage();
                }
            }
        });
    }

    private void loadMessage() {

        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));

        new Timeline(phone_md5, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<Message> timeline) {
                pd.dismiss();


                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();

                Toast.makeText(AtyTimeline.this, R.string.fail_to_load_timeline_data, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String phone_num, token, phone_md5;
    private AtyTimelineMessageListAdapter adapter = null;

}
