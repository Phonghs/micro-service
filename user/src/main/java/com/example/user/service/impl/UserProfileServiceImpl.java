package com.example.user.service.impl;

import com.example.common.proxy.common.enumtype.Provider;
import com.example.common.proxy.common.enumtype.RoleEnum;
import com.example.common.proxy.entity.MailMessage;
import com.example.common.proxy.exception.BadRequestException;
import com.example.common.proxy.exception.NotFoundException;
import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.user.entity.Role;
import com.example.user.entity.User;
import com.example.user.entity.UserRole;
import com.example.user.repository.RolesRepository;
import com.example.user.repository.UserRepository;
import com.example.user.repository.UserRoleRepository;
import com.example.user.service.EmailService;
import com.example.user.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final RolesRepository rolesRepository;

	private final UserRoleRepository userRoleRepository;

	private final EmailService emailService;

	public UserProfileServiceImpl(UserRepository userRepository,
								  ModelMapper modelMapper,
								  PasswordEncoder passwordEncoder,
								  RolesRepository rolesRepository, UserRoleRepository userRoleRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.rolesRepository = rolesRepository;
		this.userRoleRepository = userRoleRepository;
		this.emailService = emailService;
	}

	@Override
	public UserProfileResponse findByUsername(String username) {
		User user = userRepository.findFirstByUsername(username).orElse(null);
		if(Objects.isNull(user)) return null;
		UserProfileResponse userProfileResponse = modelMapper.map(user,UserProfileResponse.class);
		List<Role> roles = rolesRepository.findByUser(username);
		userProfileResponse.setRole(roles.isEmpty() ? null :
				roles.stream().map(Role::getName).collect(Collectors.toList()));
		return userProfileResponse;
	}

	@Override
	@Transactional
	public UserProfileResponse register(UserRegistrationRequest request) {
		if(userRepository.existsByUsername(request.getUsername())){
			throw new BadRequestException("user already exits");
		}
		byte[] array = new byte[10]; // length is bounded by 10
		new Random().nextBytes(array);
		String pass = Objects.isNull(request.getPassword()) ? new String(array, StandardCharsets.UTF_8) : request.getPassword();
		UserProfileResponse u = modelMapper.map(userRepository.save(com.example.user.entity.User.builder()
				.username(request.getUsername())
				.name(request.getFullName())
				.email(request.getEmail())
				.provider(Provider.LOCAL)
				.password(passwordEncoder.encode(pass))
				.build()),UserProfileResponse.class);
		userRoleRepository.save(UserRole.builder()
				.userId(u.getId())
				.roleId(RoleEnum.USER.getId())
				.build());
		u.setRole(Collections.singletonList(RoleEnum.USER.getName()));
		String body = buildBodyNotifyCreateAccount(u.getName(),u.getUsername(),pass);
		try {
			emailService.send(MailMessage.builder()
					.isHtml(true)
					.to(u.getEmail())
					.text(body)
					.subject("Thông báo đăng ký tài khoản thành công")
					.personal("ALISOON SYSTEM")
					.build());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return u;
	}

	@Override
	public UserProfileResponse changePassword(String username, String oldPassword,String newPassword) {
		User user = userRepository.findFirstByUsername(username).orElseThrow(
				() -> new NotFoundException("Can't find any user by username :"+username)
		);
		if(passwordEncoder.matches(oldPassword,user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			UserProfileResponse response = modelMapper.map(user,UserProfileResponse.class);
			response.setPassword(newPassword);
			return response;
		}
		throw new BadRequestException("old password is wrong");
	}

	@Override
	public List<UserProfileResponse> getListStudent() {
		return userRepository.findByRole(RoleEnum.USER.getName()).stream()
				.filter(Objects::nonNull).map(e -> modelMapper.map(e,UserProfileResponse.class)).collect(Collectors.toList());
	}

	private static String buildBodyNotifyCreateAccount(String name,String username,String password){
		StringBuilder builder = new StringBuilder();

		builder.append("<p>Kính gửi,</p>");
		builder.append("<p>Alison xác nhận đăng ký thành công với tài khoản của quý ông/bà "+name+"</p>");
		builder.append("<p>với thông tin đăng nhập như sau: </p>  ");
		builder.append("<p>Tên đăng nhập : <b>").append(username).append("</b></p>");
		builder.append("<p>Mật khẩu : <b>").append(password).append("</b></p>");
		return builder.toString();
	}
}
