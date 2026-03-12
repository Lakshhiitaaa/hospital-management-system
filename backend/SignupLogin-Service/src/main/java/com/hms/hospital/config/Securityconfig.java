package com.hms.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hms.hospital.service.imp.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class Securityconfig {

	@Autowired
	CustomUserDetailsService userDetailsService;

	@SuppressWarnings("removal") // Ignores warning for deprecated code
	@Bean // defines which endpoints are secure / open, how sessions are handled, and what
			// filters run.
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions().disable())
				.authorizeHttpRequests(auth -> auth
						// Use AntPathRequestMatcher explicitly to avoid ambiguity
						.requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // Allow H2 console
						.anyRequest().permitAll() // Allow all since gateway handles authorization
				)
				// Make the application stateless
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(withDefaults());

		// Populate SecurityContext from gateway forwarded header (X-User-Id)
		http.addFilterBefore(headerPrincipalFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// encrypt passwords using BCrypt algorithm
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// AuthenticationManager to handle authentication requests
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// filter that checks if a request has an "X-User-Id" header and uses it to
	// authenticate the request
	@Bean
	public OncePerRequestFilter headerPrincipalFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
					throws ServletException, IOException {

				// Read the user ID from custom header
				String userId = request.getHeader("X-User-Id");

				// If userId is present and no one is currently authenticated
				if (userId != null && !userId.isBlank()
						&& SecurityContextHolder.getContext().getAuthentication() == null) {

					// Create an authentication token with the user ID
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
							Collections.emptyList());

					// Add details like remote address, session info, etc.
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// Set the authentication in the security context
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				// Continue with the next filter in the chain
				chain.doFilter(request, response);
			}
		};
	}

}
