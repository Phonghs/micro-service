package com.example.user.entity;

import com.example.common.proxy.common.enumtype.Provider;
import com.example.common.proxy.entity.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id ;

	@Column(nullable = false,name = "username")
	private String username ;

	private String name;

	@JsonIgnore
	private String password;
	private String email;

	private String avatar;

	private Provider provider;

}
