package com.ccsu.clothesmanagement.entity;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于向页面传递信息的类
 */
public class Result {
	//状态码100成功， 200失败
	private int code;

	//提示信息
	private String msg;

	//返回给用户的信息
	private Map<String, Object> extend = new HashMap<>();

	public static Result success(){
		Result result = new Result();
		result.setCode(100);
		result.setMsg("处理成功");
		return result;
	}

	public static Result fail(){
		Result result = new Result();
		result.setCode(200);
		result.setMsg("处理失败");
		return result;
	}

	public Result add(String key, Object value){
		this.extend.put(key, value);
		return this;
	}
	public Result remove(String key){
		this.extend.remove(key);
		return this;
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

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
}