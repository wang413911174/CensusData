package com.firstvrp.censusdata.Entity;

/**
 * Created by chenyuan on 16/6/13.
 */
public class UpdataUserInfoEntity {
//    params.put("user_account", register_username.getText().toString());
//    params.put("user_name", register_realname.getText().toString());
//    params.put("mobile", register_mobile.getText().toString());
//    params.put("password", register_confirm_password.getText().toString());
//    params.put("invite_code", invitation_code.getText().toString());

    private String id;
    private String user_account;
    private String user_name;
    private String mobile;
    private String password;
    private String invite_code;
    private int user_type;
    private int is_use;

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getIs_user() {
        return is_use;
    }

    public void setIs_user(int is_user) {
        this.is_use = is_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }
}
