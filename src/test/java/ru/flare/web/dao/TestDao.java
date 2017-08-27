package ru.flare.web.dao;

import ru.flare.web.dto.User;

import java.util.Arrays;
import java.util.List;

public class TestDao implements Dao<User> {


    @Override
    public User getById( String id ) {
        return  new User(id, 1000);
    }

    @Override
    public void save(User... type) {
        //nothing to do in test
    }
}
