package com.saraob.user;

public class UserService {

    UserDao userDao = new UserDao();

    public User[] getAllUsers() {
        return userDao.getAllUser();
    }

}
