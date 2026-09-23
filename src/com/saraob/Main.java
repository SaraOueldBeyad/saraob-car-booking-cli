package com.saraob;

import com.saraob.user.User;
import com.saraob.user.UserDao;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        UserDao user = new UserDao();
        System.out.println(user.getUserById(UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11")));
    }
}
