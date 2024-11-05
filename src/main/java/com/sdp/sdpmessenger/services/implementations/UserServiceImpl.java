package com.sdp.sdpmessenger.services.implementations;

import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.repositories.UserRepository;
import com.sdp.sdpmessenger.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User create(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        if (userRepo.existsById(user.getId())) {
            return userRepo.save(user);
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);

            return true;
        }

        return false;
    }
}
