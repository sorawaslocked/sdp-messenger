package com.sdp.sdpmessenger.services.interfaces;

import com.sdp.sdpmessenger.models.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> getAll();
    User getById(int id);

    User create(User user);
    User update(User user);
    boolean deleteById(int id);
}
