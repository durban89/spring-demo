package com.gowhich.springdemo.user;

public final class User {

    private final String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}