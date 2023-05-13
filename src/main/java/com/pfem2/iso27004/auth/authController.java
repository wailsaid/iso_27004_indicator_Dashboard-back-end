package com.pfem2.iso27004.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfem2.iso27004.Entity.AuthenticationRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class authController {
    private final AuthService authService;

    @Autowired
    public authController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public String testdStri(@RequestBody AuthenticationRequest req) {
        return authService.authenticate(req);
    }
}
