package toni.ferreiro.brewery.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import toni.ferreiro.brewery.domain.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}