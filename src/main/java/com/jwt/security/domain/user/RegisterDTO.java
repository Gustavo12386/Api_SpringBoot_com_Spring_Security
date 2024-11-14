package com.jwt.security.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {

}
