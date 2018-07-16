package com.jikexueyuan.secret.net;

import com.jikexueyuan.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCode {

    public GetCode(String phone, final SuccessCallback successCalback, final FailCallback failCalback) {

        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    switch (jsonObj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCalback != null) {
                                successCalback.onSuccess();
                            }
                            break;
                        default:
                            if (failCalback != null) {
                                failCalback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    if(failCalback!=null){
                        failCalback.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCalback != null) {
                    failCalback.onFail();
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_GET_CODE, Config.KEY_PHONE_NUM, phone);
    }

    public static interface SuccessCallback {
        void onSuccess();
    }

    public static interface FailCallback {
        void onFail();
    }
}
