package com.jikexueyuan.secret.net;

public class Message {

    public Message(String msgId, String msg, String phone_md5) {
        this.msg = msg;
        this.msgId = msgId;
        this.phone_md5 = phone_md5;
    }

    private String msgId = null;
    private String msg = null;
    private String phone_md5 = null;

    public String getMsgId() {
        return msgId;
    }

    public String getMsg() {
        return msg;
    }

    public String getPhone_md5() {
        return phone_md5;
    }


}
