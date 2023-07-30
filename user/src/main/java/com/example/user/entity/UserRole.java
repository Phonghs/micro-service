package com.example.user.entity;

import com.example.common.proxy.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRole extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id ;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "role_id")
	private Long roleId;
}
