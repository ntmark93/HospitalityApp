package com.example.stay_mate.service.user;

import com.example.stay_mate.model.user.User;
import com.example.stay_mate.repository.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
    // @PreAuthorize("hasRole('USER')")
    public void saveUser(User user){
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("User not found:" + email)
                        )
                );
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public boolean isEmailAlreadyTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}
