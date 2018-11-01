package com.cooksys.ftd.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.ftd.socialmedia.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User getUserByUsernameAndDeletedFalse(String username);

	User getUserByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.deleted = false ORDER BY u.username")
	List<User> findAll();
}