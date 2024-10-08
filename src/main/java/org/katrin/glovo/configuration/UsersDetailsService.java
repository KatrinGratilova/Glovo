package org.katrin.glovo.configuration;

import lombok.Data;
import org.katrin.glovo.entity.UserEntity;
import org.katrin.glovo.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity employee = userRepository.findByEmail(username);

        Set<GrantedAuthority> authorities = employee.getRoles().stream()
                .map((roles) -> new SimpleGrantedAuthority(roles.toString()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                username,
                employee.getPassword(),
                authorities
        );
    }
}
