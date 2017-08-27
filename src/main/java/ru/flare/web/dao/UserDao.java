package ru.flare.web.dao;

import ru.flare.web.dto.User;

import java.util.*;

public class UserDao implements Dao<User> {

    Map<String, User> usersTable = new HashMap<>();

    @Override
    public User getById(String id) {
        return usersTable.get(id);
    }

    @Override
    public void save(User... users) {
        Arrays.stream(users).forEach(user ->
                usersTable.put(user.getId(), user)
        );
    }
}
