package com.pfem2.iso27004.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
