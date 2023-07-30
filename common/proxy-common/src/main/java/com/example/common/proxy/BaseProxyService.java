package com.example.common.proxy;

import com.example.common.proxy.common.enumtype.LookupStrategyEnum;
import com.example.common.proxy.service.EurekaDiscoveryClientService;
import com.example.common.proxy.user.proxy.UserServiceProxy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.URI;


@Service
@Getter
@Primary
@ConditionalOnMissingBean(value = BaseProxyService.class)
public class BaseProxyService {


	@Autowired
	private UserServiceProxy userServiceProxy;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Value("${internal.apigate.services.user.name}")
	private String userServiceName;

	public URI buildUserServiceUri() {
		return EurekaDiscoveryClientService.getUriOfEurekaService(discoveryClient,
				userServiceName, LookupStrategyEnum.RANDOM);
	}

}
