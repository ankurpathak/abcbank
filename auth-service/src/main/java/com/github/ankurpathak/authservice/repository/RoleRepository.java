package com.github.ankurpathak.authservice.repository;

import com.github.ankurpathak.authservice.entity.Role;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

@Observed
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
