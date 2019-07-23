package com.atguigu.scw.manager.bean;

public class TToken {
    private Integer id;

    private Integer userid;

    private String pswToken;

    private String autoToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPswToken() {
        return pswToken;
    }

    public void setPswToken(String pswToken) {
        this.pswToken = pswToken == null ? null : pswToken.trim();
    }

    public String getAutoToken() {
        return autoToken;
    }

    public void setAutoToken(String autoToken) {
        this.autoToken = autoToken == null ? null : autoToken.trim();
    }
}