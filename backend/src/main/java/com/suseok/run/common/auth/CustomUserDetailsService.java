package com.suseok.run.common.auth;

import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userSeq) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userSeq)).get();
        return new CustomUserDetails(user);
    }
}
