package com.saraob.user;

import java.util.List;

public class UserService {

    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUser();
    }

}
