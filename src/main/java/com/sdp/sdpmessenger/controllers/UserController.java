package com.sdp.sdpmessenger.controllers;

import com.sdp.sdpmessenger.models.User;
import com.sdp.sdpmessenger.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserServiceInterface service;

    public UserController(UserServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getById(@PathVariable("user_id") int id){
        User user = service.getById(id);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user){
        User createdUser = service.create(user);
        if(createdUser == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED); //201 Create
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> create(@PathVariable int id, @RequestBody User user){
        // Set ID to ensure we are updating the correct user
        user.setId(id);
        User updatedUser = service.update(user);

        if(updatedUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 User not found

        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED); //201
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.deleteById(id)) { // Assuming deleteById returns boolean for success
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if user not found
        }
        return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
    }

}
