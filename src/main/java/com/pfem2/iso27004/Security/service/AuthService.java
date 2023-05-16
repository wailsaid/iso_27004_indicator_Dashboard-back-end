package com.pfem2.iso27004.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.User;
import com.pfem2.iso27004.Repository.UserRepository;
import com.pfem2.iso27004.Security.Entity.AuthRequest;
import com.pfem2.iso27004.Security.Entity.AuthResponse;
import com.pfem2.iso27004.Service.UserService;

@Service
public class AuthService {
        private final UserService userService;
        // private final TokenRepository tokenRepository;
        // private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        public AuthService(UserService userservice, PasswordEncoder passwordEncoder, JwtService jwtService,
                        AuthenticationManager authenticationManager) {
                this.userService = userservice;
                // this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
                this.authenticationManager = authenticationManager;
        }

        public AuthResponse authenticate(AuthRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                User user = userService.getByUsername(request.getUsername()).orElseThrow();

                String jwtToken = jwtService.generateToken(user);
                // var refreshToken = jwtService.generateRefreshToken(user);
                // revokeAllUserTokens(user);
                // saveUserToken(user, jwtToken);
                return AuthResponse.builder().token(jwtToken)
                                .user(user)
                                .build();
                // return jwtToken;
                // .refreshToken(refreshToken)
                // .build();
        }
}
