package com.example.backend.service;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.backend.constatns.JWTConstants;
import com.example.backend.model.User;
import com.example.backend.repo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JWTService {

	@Autowired
	UserRepo ur;

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String userName) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecretKey key = Keys.hmacShaKeyFor(JWTConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
		return Jwts.builder().setClaims(claims).setSubject(userName).claim("authorities", populateAuthorities(userName))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	private String populateAuthorities(String email) {

		Optional<User> userInfo = ur.findByEmail(email);

		return userInfo.get().getRole();

	}

}
