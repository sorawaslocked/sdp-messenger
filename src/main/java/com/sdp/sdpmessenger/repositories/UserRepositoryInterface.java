package com.sdp.sdpmessenger.repositories;

import com.sdp.sdpmessenger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryInterface extends JpaRepository<User, Integer> {
}
