package com.example.user.repository;

import com.example.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
	Optional<Role> findFirstByName(@NotNull String name);

	@Query(nativeQuery = true,
	value = "select r.* from user u  " +
			"join user_role ur on ur.user_id = u.id " +
			"join roles r on r.id = ur.role_id " +
			"where u.username = :username ")
	List<Role> findByUser(String username);
}
