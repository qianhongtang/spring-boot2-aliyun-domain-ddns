/**
 * 
 */
package com.tangqh.aliyun.domain.scheduled;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tangqh.aliyun.domain.config.service.UpdateDomainService;

/**
 * @author qianh
 *
 */
@Component
public class ScheduledList {

	@Autowired
	private UpdateDomainService updateDomainService;

	@PostConstruct
	public void post() {
		updateDomain();
	}

	@Scheduled(cron = "0 0/10 * * * ?") // 每隔10分钟执行一次定时任务
	public void updateDomain() {
		System.out.println("开始更新域名-" + new Date());
		try {
			updateDomainService.updateDomain(null);
			System.out.println("结束更新域名[成功]-" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("结束更新域名[失败]-" + new Date());
		}
	}

}
