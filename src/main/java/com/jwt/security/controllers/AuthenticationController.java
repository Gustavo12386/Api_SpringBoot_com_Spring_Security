package com.jwt.security.controllers;

import com.jwt.security.domain.user.AuthenticationDTO;
import com.jwt.security.domain.user.LoginResponseDTO;
import com.jwt.security.domain.user.User;

import com.jwt.security.infra.TokenService;
import com.jwt.security.repositories.UserRepository;
import com.jwt.security.domain.user.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository repository;
	private final TokenService tokenService;	
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager,
	UserRepository repository, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.tokenService = tokenService;
	}	
	   
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
	    try {
	        var authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password())
	        );

	        String token = tokenService.generateToken((User) authentication.getPrincipal());
	        return ResponseEntity.ok(new LoginResponseDTO(token));
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body(new LoginResponseDTO("Credenciais inválidas"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new LoginResponseDTO("Erro interno ao processar a solicitação"));
	    }
	}


	

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
	    if (this.repository.findByLogin(data.login()) != null) {
	        return ResponseEntity.badRequest().body(null); // Retorna 400 Bad Request
	    }

	    try {
	        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
	        User newUser = new User(data.login(), encryptedPassword, data.role());
	        this.repository.save(newUser);
	        return ResponseEntity.ok().build(); // Retorna 200 OK
	    } catch (Exception e) {
	        // Log se ocorrer algum erro ao salvar o usuário
	        System.out.println("Erro ao registrar usuário: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
	    }
	}
}

