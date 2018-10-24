package com.tangqh.aliyun.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Configuration
public class ConfigAliyun {

	@Value("${aliyun.regionId}")
	String regionId;

	@Value("${aliyun.accessKeyId}")
	String accessKeyId;

	@Value("${aliyun.accessKeySecret}")
	String accessKeySecret;

	@Bean("iAcsClient")
	public DefaultAcsClient iAcsClient() {
//        String regionId = "cn-hangzhou"; //必填固定值，必须为“cn-hanghou”
//        String accessKeyId = ""; // your accessKey
//        String accessKeySecret = "";// your accessSecret
		IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		// 若报Can not find endpoint to access异常，请添加以下此行代码
		// DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
		return new DefaultAcsClient(profile);
	}
}
