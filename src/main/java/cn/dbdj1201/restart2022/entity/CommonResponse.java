/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package cn.dbdj1201.restart2022.entity;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.apache.hc.core5.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class CommonResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public CommonResponse() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static CommonResponse error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static CommonResponse error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static CommonResponse error(int code, String msg) {
		CommonResponse r = new CommonResponse();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static CommonResponse ok(String msg) {
		CommonResponse r = new CommonResponse();
		r.put("msg", msg);
		return r;
	}
	
	public static CommonResponse ok(Map<String, Object> map) {
		CommonResponse r = new CommonResponse();
		r.putAll(map);
		return r;
	}
	
	public static CommonResponse ok() {
		return new CommonResponse();
	}

	public CommonResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode() {

		return (Integer) this.get("code");
	}

	public CommonResponse setData(Object data) {
		put("data", data);
		return this;
	}

	public <T> T getData(TypeReference<T> tTypeReference) {
		return this.getData("data", tTypeReference);
	}

	public <T> T getData(String key, TypeReference<T> tTypeReference) {
		Object data = this.get(key);
		String toJSONString = JSON.toJSONString(data);
		return JSON.parseObject(toJSONString, tTypeReference);
	}
}
