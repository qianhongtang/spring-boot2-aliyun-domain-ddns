package com.tangqh.aliyun.domain.config.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.tangqh.aliyun.domain.config.service.UpdateDomainService;
import com.tangqh.aliyun.domain.util.IpUtil;

@Service
public class UpdateDomainServiceImpl implements UpdateDomainService {

	@Value("${aliyun.update.domains}")
	private String domains;

	@Autowired
	private IAcsClient iAcsClient;

	@Override
	public String updateDomain(List<String> newDomainList) {
		Map<String, List<String>> domainMap = getDomainMap(mergreList(newDomainList));
		String ip = IpUtil.getIp();
		for (String domainName : domainMap.keySet()) {
			List<String> domainList = domainMap.get(domainName);
			try {
				this.updateDomain(domainName, domainList, ip);
			} catch (ServerException e) {
				e.printStackTrace();
				return "ServerException：" + e.getMessage();
			} catch (ClientException e) {
				e.printStackTrace();
				return "ClientException：" + e.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
				return "Exception：" + e.getMessage();
			}
		}
		return "ok";
	}

	private List<String> mergreList(List<String> newDomainList) {
		if (StringUtils.isEmpty(domains) && CollectionUtils.isEmpty(newDomainList)) {
			return Collections.emptyList();
		}
		List<String> domainList = Arrays.asList(domains.split(","));
		if (CollectionUtils.isEmpty(newDomainList)) {
			return domainList;
		}
		newDomainList.forEach(d -> {
			if (!domainList.contains(d)) {
				domainList.add(d);
			}
		});
		return domainList;
	}

	private Map<String, List<String>> getDomainMap(List<String> domains) {
		Map<String, List<String>> domainMap = new HashMap<>();
		if (CollectionUtils.isEmpty(domains)) {
			return domainMap;
		}
		for (String domain : domains) {
			if (StringUtils.isEmpty(domains)) {
				continue;
			}
			String rr = domain.substring(0, domain.indexOf("."));
			if (StringUtils.isEmpty(rr)) {
				continue;
			}
			String domainName = domain.substring(domain.indexOf(".") + 1);
			if (StringUtils.isEmpty(rr)) {
				continue;
			}
			if (!domainMap.containsKey(domainName)) {
				domainMap.put(domainName, new ArrayList<>());
			}
			rr = rr.trim();
			domainName.trim();
			domainMap.get(domainName).add(rr);
		}
		return domainMap;
	}

	private void updateDomain(String domainName, List<String> rrList, String ip)
			throws ServerException, ClientException, Exception {
		DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
		request.setDomainName(domainName);
		DescribeDomainRecordsResponse response = iAcsClient.getAcsResponse(request);
		List<DescribeDomainRecordsResponse.Record> list = response.getDomainRecords();
		for (DescribeDomainRecordsResponse.Record record : list) {
			for (String rr : rrList) {
				if (record.getRR() != null && record.getRR().equals(rr) && !record.getValue().equals(ip)) {
					this.updateOne(record, ip);
				}
			}
		}
	}

	private void updateOne(DescribeDomainRecordsResponse.Record record, String ip) throws Exception {
		UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
		request.setRecordId(record.getRecordId());
		request.setRR(record.getRR());
		request.setType(record.getType());
		request.setValue(ip);
		request.setTTL(record.getTTL());
		request.setPriority(record.getPriority());
		request.setLine(record.getLine());

		UpdateDomainRecordResponse response = iAcsClient.getAcsResponse(request);
		System.out.println(response.getRequestId());
	}
}
