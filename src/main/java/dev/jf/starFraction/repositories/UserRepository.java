package dev.jf.starFraction.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import dev.jf.starFraction.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    UserDetails findByEmail(String email);
    Optional<User> findUserByEmail(String email);

}
