//package com.example.backend.config;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.example.backend.model.UserModel;
//import com.example.backend.repo.UserRepo;
//
//
//@Component
//public class UserNamePwdProvider implements AuthenticationProvider  {
//	
//	@Autowired
//	UserRepo ur;
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		
//		String userName = authentication.getName();
//		String pwd = authentication.getCredentials().toString();
//		
//		List<UserModel> userList = ur.findByUsername(userName);
//		UserModel user=null;
//		if(userList.size()>0)
//		{
//			user=ur.findByUsername(userName).get(0);
//		}
//		else {
//			System.out.println("user not found");
//			return null;
//		}
//		
//		if(passwordEncoder.matches(pwd, user.getPassword())) {
////			List<GrantedAuthority> authorities = new ArrayList<>();
////			authorities.add(new SimpleGrantedAuthority(user.getRole()));
////			System.out.println(authorities);
//			
//			return new UsernamePasswordAuthenticationToken(user,pwd,getGrantedAuthorities(user.getRole()));
//		}
//		else {
//			throw new BadCredentialsException("Invalid Password or UserName");
//		}
//		
//		
//	}
//	
//	 private List<GrantedAuthority> getGrantedAuthorities(String role) {
//	        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//	        
//	            System.out.println("Simple Role"+role);
//	            grantedAuthorities.add(new SimpleGrantedAuthority(role));
//	            System.out.println("Grant authorities added....");
//	      
//	        return grantedAuthorities;
//	    }
//
//	
//	
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		
//		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//	}
//}
