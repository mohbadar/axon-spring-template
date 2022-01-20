package com.unite.axon_spring.iam.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserService extends UserDetailsService {
	public CustomUser loadUserByUsername(String username);
	public CustomUser loadUserByUsername(String username, String currentEnv, String currentLang);

	public String getCurrentEnv();
}
