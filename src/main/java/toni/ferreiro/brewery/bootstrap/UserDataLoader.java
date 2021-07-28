package toni.ferreiro.brewery.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import toni.ferreiro.brewery.domain.security.Authority;
import toni.ferreiro.brewery.domain.security.User;
import toni.ferreiro.brewery.repositories.security.AuthorityRepository;
import toni.ferreiro.brewery.repositories.security.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        Authority admin = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customer = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());

        userRepository.save(User.builder()
                .username("toni")
                .password(passwordEncoder.encode("fercou"))
                .authority(admin)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(userRole)
                .build());

        userRepository.save(User.builder()
                .username("user2")
                .password(passwordEncoder.encode("pass2"))
                .authority(customer)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}