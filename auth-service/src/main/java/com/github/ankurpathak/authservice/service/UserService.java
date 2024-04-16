package com.github.ankurpathak.authservice.service;

import com.github.ankurpathak.authservice.entity.Role;
import com.github.ankurpathak.authservice.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String roleName);
    User removeRoleToUser(String username, String roleName);
    void deleteUserById(Long id);
    List<User> getAllUsers();
}
