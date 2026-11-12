package com.saraob.user;

public class UserService {

    UserDaoArrayDataAccessService userDaoArrayDataAccessService = new UserDaoArrayDataAccessService();

    public User[] getAllUsers() {
        return userDaoArrayDataAccessService.getAllUser();
    }

}
