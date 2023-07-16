package com.init.mybatis_9.bean;

public enum Empstatus {
    LOGIN(100, "用户登录"),
    LOGOUT(200, "用户退出"),
    REMOVE(400, "用户不存在");

    private int code;
    private String msg;

    Empstatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    Empstatus() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static Empstatus getEmpStatusByCode(int code) {
        switch (code) {
            case 100:
                return LOGIN;
            case 200:
                return LOGOUT;
            case 400:
                return REMOVE;
            default:
                return LOGOUT;
        }
    }
}
