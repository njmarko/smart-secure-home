package com.example.demo.auth;

import com.example.demo.service.BlacklistedTokenService;
import com.example.demo.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Filter koji ce presretati SVAKI zahtev klijenta ka serveru 
// (sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web))
// Filter proverava da li JWT token postoji u Authorization header-u u zahtevu koji stize od klijenta
// Ukoliko token postoji, proverava se da li je validan. Ukoliko je sve u redu, postavlja se autentifikacija
// u SecurityContext holder kako bi podaci o korisniku bili dostupni u ostalim delovima aplikacije gde su neophodni
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private final TokenUtils tokenUtils;
	private final UserDetailsService userDetailsService;
	private final BlacklistedTokenService blacklistedTokenService;
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, BlacklistedTokenService blacklistedTokenService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.blacklistedTokenService = blacklistedTokenService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		String username;
		
		// 1. Preuzimanje JWT tokena i cookie iz zahteva
		String authToken = tokenUtils.getToken(request);
		String fingerprint = tokenUtils.getFingerprintFromCookie(request);

		try {
	
			if (authToken != null && blacklistedTokenService.isAllowed(authToken)) {
				
				// 2. Citanje korisnickog imena iz tokena
				username = tokenUtils.getUsernameFromToken(authToken);
				
				if (username != null) {
					
					// 3. Preuzimanje korisnika na osnovu username-a
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					// 4. Provera da li je prosledjeni token validan
					if (tokenUtils.validateToken(authToken, userDetails, fingerprint)) {
						
						// 5. Kreiraj autentifikaciju
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			
		} catch (ExpiredJwtException ex) {
			LOGGER.debug("Token expired!");
		} 
		
		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}

}