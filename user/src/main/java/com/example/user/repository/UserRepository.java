package com.example.user.repository;

import com.example.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findFirstByUsername(String username);

	boolean existsByUsername(String username);

	@Query(nativeQuery = true,
	value = "select u.* " +
			"from user u " +
			"join user_role ur on u.id = ur.user_id " +
			"join roles r on ur.role_id = r.id " +
			"where r.name = :role " +
			"group by u.id")
	List<User> findByRole(String role);
}
