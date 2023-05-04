package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebSecurity
//@EnableSwagger2
public class Backend1Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend1Application.class, args);
	}

	
//	@Bean
//    public WebMvcConfigurer corsConfigurationSource() {
//		return new WebMvcConfigurer() {
//			
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//				.exposedHeaders(HttpHeaders.AUTHORIZATION, "X-XSRF-TOKEN")
//				.allowedOriginPatterns("http://localhost:[*]")
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION")
//				.allowCredentials(true);
//			}
//		
//		};
//		
//       
//    }

}
