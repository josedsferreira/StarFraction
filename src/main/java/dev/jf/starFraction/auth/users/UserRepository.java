package dev.jf.starFraction.auth.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    UserDetails findByUsername(String username);
    UserDetails findByEmail(String email);

}