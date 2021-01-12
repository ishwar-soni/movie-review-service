package com.inos.mrs.daos;

import com.inos.mrs.entities.User;
import com.inos.mrs.exceptions.UserNotFoundException;
import com.inos.mrs.utils.IdCounter;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> users;

    public UserDao() {
        this.users = new ArrayList<>();
    }

    public User create(User user) {
        user.setId(IdCounter.getId());
        users.add(user);
        return user;
    }

    public User findByName(String name) throws UserNotFoundException {
        return users
                .stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with username: [" + name + "] not found."));
    }

    public User update(User updatedUser) throws UserNotFoundException {
        User result = users
                .stream()
                .filter(user -> user.getId() == updatedUser.getId())
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with userid: [" + updatedUser.getId() + "] not found."));
        result.setName(updatedUser.getName());
        result.setRole(updatedUser.getRole());
        return updatedUser;
    }
}
