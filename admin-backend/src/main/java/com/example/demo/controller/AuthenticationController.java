package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.demo.dto.JwtAuthenticationRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserTokenState;
import com.example.demo.events.InvalidLoginAttempt;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.logging.LogService;
import com.example.demo.model.RealEstate;
import com.example.demo.model.User;
import com.example.demo.service.BlacklistedTokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BlacklistedTokenService blacklistedTokenService;
    private final LogService logService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
            // kontekst
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Kreiraj token za tog korisnika
            User user = (User) authentication.getPrincipal();
            String fingerprint = tokenUtils.generateFingerprint();
            String jwt = tokenUtils.generateToken(user.getUsername(), fingerprint);
            int expiresIn = tokenUtils.getExpiredIn();

            // Kreiraj cookie
//         String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";  // kasnije mozete probati da postavite i ostale atribute, ali tek nakon sto podesite https
//        String cookie = "Fingerprint" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";  // kasnije mozete probati da postavite i ostale atribute, ali tek nakon sto podesite https
            String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/; Secure; SameSite=Strict;";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", cookie);

            // authorities
            List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            UserTokenState userToken = new UserTokenState(jwt, expiresIn);

            List<RealEstate> realEstates = userService.getMyRealEstates(user.getUsername());

            userToken.setAuthorities(authorities);
            userToken.setRealEstates(realEstates.stream().map(RealEstate::getId).collect(Collectors.toList()));
            userToken.setEmail(user.getEmail());
            userToken.setName(user.getFirstName());
            userToken.setSurname(user.getLastName());

            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok().headers(headers).body(userToken);
        } catch (BadCredentialsException exception) {
            userService.tryLockAccount(authenticationRequest.getUsername());
            logService.save(new InvalidLoginAttempt(authenticationRequest.getUsername()));
            throw exception;
        }
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

        User existUser = this.userService.findByUsername(userRequest.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        String bearerToken = authorizationHeader.replace("Bearer ", "");
        blacklistedTokenService.blacklist(bearerToken);
    }

    @PreAuthorize("hasAuthority('BLACKLIST_JWT')")
    @PutMapping("/blacklist/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blacklist(@PathVariable("token") String token) {
        blacklistedTokenService.blacklist(token);
    }
}
