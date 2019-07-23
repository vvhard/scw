package com.atguigu.scw.manager.bean;

import java.util.ArrayList;
import java.util.List;

public class TAcctType {
	private int id;
	private String name;
	private List<Boolean> certList = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
	public List<Boolean> getCertList() {
		return certList;
	}
	public void setCertList(List<Boolean> certList) {
		this.certList = certList;
	}
	@Override
	public String toString() {
		return "TAcctType [id=" + id + ", name=" + name + ", certMap=" + certList + "]";
	}
	
}
