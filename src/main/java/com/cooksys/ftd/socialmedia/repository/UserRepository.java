package com.cooksys.ftd.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.ftd.socialmedia.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}