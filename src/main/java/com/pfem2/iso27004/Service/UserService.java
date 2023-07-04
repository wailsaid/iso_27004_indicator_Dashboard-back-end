package com.pfem2.iso27004.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Entity.Collector;
import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.User;
import com.pfem2.iso27004.Repository.CollectorRepository;
import com.pfem2.iso27004.Repository.UserRepository;
import com.pfem2.iso27004.Security.Errors.BadrequestException;
import com.pfem2.iso27004.Security.service.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CollectorRepository collectorRepository;
    private final EvaluationService evaluationService;
    private final PasswordEncoder passwordEncoder;
    // private final JwtService jwtService;
    // private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager, CollectorRepository collectorRepository,
            EvaluationService evaluationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.collectorRepository = collectorRepository;
        this.evaluationService = evaluationService;
        // this.jwtService = jwtService;
        // this.authenticationManager = authenticationManager;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            // throw new IllegalStateException("E-mail already exist");
            throw new BadrequestException("E-mail already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exist = userRepository.existsById(userId);
        if (!exist) {

            throw new BadrequestException("User with ID:" + userId + " does not exist");
        }
        List<Evaluation> evaluations = this.evaluationService.getAll();

        for (Evaluation e : evaluations) {
            if ((e.getResp().getId()).equals(userId)) {

                this.evaluationService.deleteEvaluation(e);
            }
        }

        List<Collector> collectors = this.collectorRepository.findAll();
        for (Collector c : collectors) {
            /*
             * System.out.println(c.getCollector().getId());
             *
             *
             * System.out.println((c.getCollector().getId()).equals(userId));
             */

            if ((c.getCollector().getId()).equals(userId)) {
                this.collectorRepository.delete(c);
            }
        }

        userRepository.deleteById(userId);

    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<String> getAdminEmails() {
        return this.userRepository.findAllAdminEmails();
    }

    public Collector setCollector(Collector c) {
        return this.collectorRepository.save(c);
    }

    public List<Collector> getCollectors() {
        return this.collectorRepository.findAll();
    }

    public void saveALLCollectors(List<Collector> l) {
        this.collectorRepository.saveAll(l);
    }

}
