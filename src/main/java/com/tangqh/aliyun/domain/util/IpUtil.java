package com.tangqh.aliyun.domain.util;

import org.springframework.web.client.RestTemplate;

/**
 * ip工具类，用于获取系统相关信息 Created by tangqh.
 */
public class IpUtil {

	public static String getIp() {
		RestTemplate rest = new RestTemplate();
		String response = rest.getForObject("http://2018.ip138.com/ic.asp", String.class);
		String ip = response.substring(response.indexOf("[") + 1, response.indexOf("]"));
		return ip;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(IpUtil.getIp());
	}
}