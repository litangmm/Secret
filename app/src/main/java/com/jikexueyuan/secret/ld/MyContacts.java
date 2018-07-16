package com.jikexueyuan.secret.ld;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import com.jikexueyuan.secret.Config;
import com.jikexueyuan.secret.tools.MD5Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyContacts {

    public static String getContactsJSONString(Context context) {
        Cursor c = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null, null);
        String phoneNum=null;
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj;
        while (c.moveToNext()) {
            phoneNum = c.getString(c.getColumnIndex(Phone.NUMBER));

            if (phoneNum.charAt(0) == '+' &&
                    phoneNum.charAt(1) == '8' &&
                    phoneNum.charAt(2) == '6') {
                phoneNum = phoneNum.substring(3);
            }
            Log.d("mycontact", phoneNum);
        }

        jsonObj = new JSONObject();
        try {
            jsonObj.put(Config.KEY_PHONE_NUM, MD5Tool.md5(phoneNum));
        }catch (JSONException e){
            e.printStackTrace();
        }

        jsonArr.put(jsonObj);
        return null;
    }
}
