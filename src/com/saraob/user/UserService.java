package com.saraob.user;

public class UserService {

    UserArrayDataAccessService userArrayDataAccessService = new UserArrayDataAccessService();

    public User[] getAllUsers() {
        return userArrayDataAccessService.getAllUser();
    }

}
