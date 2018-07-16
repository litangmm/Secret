package com.jikexueyuan.secret.net;

import android.os.AsyncTask;

import com.jikexueyuan.secret.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetConnection {

    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCalback, final FailCallback failCalback, final String ... kvs) {

        new AsyncTask<Void, Void, String>() {

            protected String doInBackground(Void... arg0) {
                //网络连接

                //创建参数对
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i < kvs.length; i += 2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i + 1]).append("&"); //key1=value1&
                }

                try {
                    URLConnection uc;

                    switch (method) {
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
                            bw.write(paramsStr.toString());
                            bw.flush(); //发送
                            break;
                        default:
                            //Get
                            uc = new URL(url + "?" + paramsStr.toString()).openConnection();
                            break;
                    }

                    System.out.print("Requests url:" + uc.getURL());
                    System.out.print("Requests data:" + paramsStr);

                    //读取数据
                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }

                    System.out.print("Result:" + result);
                    return result.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            //在doInBackground完成后使用前者返回值当参数
            protected void onPostExecute(String result) {

                if (result != null) {

                    //发送成功
                    if (successCalback != null) {

                        //通知外界
                        successCalback.onSuccess(result);
                    }
                } else {

                    //发生错误
                    if (failCalback != null) {

                        //通知外界
                        failCalback.onFail();
                    }
                }
                super.onPostExecute(result);
            }
        }.execute();

    }


    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail();
    }
}
