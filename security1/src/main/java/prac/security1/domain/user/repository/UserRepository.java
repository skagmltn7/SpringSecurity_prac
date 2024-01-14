package prac.security1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prac.security1.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
