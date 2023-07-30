package com.example.common.proxy.service;

import com.example.common.proxy.common.enumtype.LookupStrategyEnum;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EurekaDiscoveryClientService {
	public static ServiceInstance lookupEurekaService(DiscoveryClient client, String serviceName,
													  LookupStrategyEnum strategy) {
		List<ServiceInstance> listService = client.getInstances(serviceName);
		if (!CollectionUtils.isEmpty(listService)) {
			if (strategy == LookupStrategyEnum.FIRST || listService.size() == 1) {
				Iterator<ServiceInstance> iter = listService.iterator();
				return iter.next();
			} else if (strategy == LookupStrategyEnum.RANDOM) {
				Random rand = new Random();
				return listService.stream().skip(rand.nextInt(listService.size() - 1)).findFirst().get();
			}
		}
		return null;
	}

	public static URI getUriOfEurekaService(DiscoveryClient client, String serviceName, LookupStrategyEnum strategy) {
		ServiceInstance instance = lookupEurekaService(client, serviceName, strategy);
		if (instance != null) {
			return instance.getUri();
		}
		return null;
	}
}
