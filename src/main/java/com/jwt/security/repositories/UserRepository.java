package com.jwt.security.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwt.security.domain.user.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	UserDetails findByLogin(String login);
}
