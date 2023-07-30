package com.example.common.proxy.user.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class UserProfileResponse {

	private Long id ;
	
	private String username ;

	private String name;

	private String password;
	private String email;

	private String avatar;

	private String provider;

	private List<String> role;

}
