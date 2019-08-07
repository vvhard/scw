package com.atguigu.scw.manager.bean;

public class CertAuthTemp {
    private Integer id;

    private Integer authinfoId;

    private String certPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthinfoId() {
        return authinfoId;
    }

    public void setAuthinfoId(Integer authinfoId) {
        this.authinfoId = authinfoId;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath == null ? null : certPath.trim();
    }
}