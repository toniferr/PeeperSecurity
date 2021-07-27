package toni.ferreiro.brewery.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toni.ferreiro.brewery.domain.security.Authority;
import toni.ferreiro.brewery.domain.security.User;
import toni.ferreiro.brewery.repositories.security.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Getting User info via JPA");

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("User name: " + username + " not found");
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                user.getAccountNonLocked(), convertToSpringAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0){
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}