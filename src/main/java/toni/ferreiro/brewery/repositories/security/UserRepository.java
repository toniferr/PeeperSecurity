package toni.ferreiro.brewery.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import toni.ferreiro.brewery.domain.security.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}