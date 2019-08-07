package com.atguigu.scw.portal.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.portal.utils.AJAXResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	@org.junit.Test
	public void test() {
		
		try {
			URI uri = new URIBuilder().setScheme("http")
				.setHost("localhost:8081")
				.setPath("/scw-restapi/member/regist")
				.setParameter("loginacct", "kevin00060")
				.setParameter("userpswd", "123456")
				.setParameter("email", "kevin02@scw.com")
				.build();
			// HttpGet get = new HttpGet("http://localhost:8081/scw-restapi/member/regist?"); // 构造请求,使用？拼接请求参数
			HttpGet get = new HttpGet(uri); // 构造请求
			CloseableHttpClient client = HttpClients.createDefault(); // 利用client发送
			CloseableHttpResponse resp = client.execute(get); // 发送请求，得到响应
			HttpEntity entity = resp.getEntity();
			//System.out.println(EntityUtils.toString(entity));
			String s = EntityUtils.toString(entity);
			AJAXResult<TMemeber> result = null;
//			result = JSON.toJavaObject(JSON.parseObject(s), AJAXResult.class);
			 result = new ObjectMapper().readValue(s.getBytes(), new TypeReference<AJAXResult<TMemeber>>() {}); //jackjson
			
			System.out.println(result.getContent());
			client.close();
			resp.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
	}

}
