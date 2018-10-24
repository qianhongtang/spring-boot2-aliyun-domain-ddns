package com.tangqh.aliyun.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tangqh.aliyun.domain.config.service.UpdateDomainService;

@RestController
public class AliyunController {

	@Autowired
	private UpdateDomainService updateDomainService;

	@RequestMapping("/update-domain/{domains}")
	public String describeDomainRecords(@PathVariable List<String> domains) {
		return updateDomainService.updateDomain(domains);
	}

}
