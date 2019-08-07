package com.atguigu.scw.portal.utils;

import java.util.Map;

public class AJAXResult<T> {
	
	private int code;
	private String msg;
	private T content;
	private Map<String, Object> ext;
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
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public Map<String, Object> getExt() {
		return ext;
	}
	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
	
	public static<T> AJAXResult<T> success(String msg,T content, Map<String, Object> ext){
		AJAXResult<T> result = new AJAXResult<>();
		result.setCode(1);
		result.setMsg(msg);
		result.setContent(content);
		result.setExt(ext);
		return result ;
	}
	public static<T> AJAXResult<T> fail(String msg,T content, Map<String, Object> ext){
		AJAXResult<T> result = new AJAXResult<>();
		result.setCode(0);
		result.setMsg(msg);
		result.setContent(content);
		result.setExt(ext);
		return result ;
	}
	@Override
	public String toString() {
		return "AJAXResult [code=" + code + ", msg=" + msg + ", content=" + content + ", ext=" + ext + "]";
	}
	
	
	
}
