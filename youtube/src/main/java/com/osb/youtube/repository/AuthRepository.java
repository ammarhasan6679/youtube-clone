package com.osb.youtube.repository;
import com.osb.youtube.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {
    Optional<Auth> findByUsername(String username);

    boolean existsByUsername(String username);
}
