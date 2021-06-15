package org.efire.net.security;

import org.efire.net.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var byUserOptional = userRepository.findByEmail(email);
        if (byUserOptional.isPresent()) {
            var user = byUserOptional.get();
            return new User(user.getEmail(), user.getPassword(), user.getRoles());
        }
        throw new UsernameNotFoundException("User not found for email "+email);
    }
}
