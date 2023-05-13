package com.pfem2.iso27004.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.User;
import com.pfem2.iso27004.Repository.UserRepository;
import com.pfem2.iso27004.Security.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    /*
     * return List.of(
     * new User(1L,
     * "wail",
     * "waildebbaghi@gmail.com",
     * "afefaef48z4g",
     * "simple"));
     */

    public User addUser(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("E-mail already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exist = userRepository.existsById(userId);
        if (!exist) {
            throw new IllegalStateException("User with ID:" + userId + " does not exist");
        }
        userRepository.deleteById(userId);

    }
}
