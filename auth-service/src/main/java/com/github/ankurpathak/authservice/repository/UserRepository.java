package com.github.ankurpathak.authservice.repository;

import com.github.ankurpathak.authservice.entity.User;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

@Observed
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
