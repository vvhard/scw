package com.atguigu.scw.manager.bean;

public class TRole {
    private Integer id;

    private String name;

    private String createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

	@Override
	public String toString() {
		return "TRole [id=" + id + ", name=" + name + ", createtime=" + createtime + "]";
	}
    
}