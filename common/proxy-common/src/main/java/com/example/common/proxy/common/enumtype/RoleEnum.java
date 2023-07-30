package com.example.common.proxy.common.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
	USER("user",1L),
	ADMIN("admin",2L),
	AUTHOR("author",3L);
	private final String name;
	private final Long id;
}
