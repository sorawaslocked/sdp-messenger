package com.sdp.sdpmessenger.repositories;

import com.sdp.sdpmessenger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByPhone(String phone);
}
