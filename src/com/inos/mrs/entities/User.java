package com.inos.mrs.entities;

import com.inos.mrs.utils.Role;

public class User {
    private Integer id;
    private String name;
    private Role role;

    public User(String name) {
        this.name = name;
        this.role = Role.VIEWER;
    }

    public User (User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
