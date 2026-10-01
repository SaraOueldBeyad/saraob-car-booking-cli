package com.saraob.user;

import java.util.UUID;

public class UserDao {

    private static final User[] users;

    static {
        users = new User[]{
                new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
                new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"),
                new User(UUID.fromString("3f7f0c5d-83b2-4d1a-a8b2-2c2d4f9e1a11"), "Sara"),
                new User(UUID.fromString("6e2c9d41-5a7b-4f64-9f32-1c6d7a8b9e22"), "Alex"),
                new User(UUID.fromString("91b4a8e2-2d61-4a3f-b6c1-8d5e7f2c3a44"), "Maria")
        };
    }

    public User getUserById(UUID id) {
        if (!id.equals(null)) {
            for (int i=0; i< users.length; i++){
                if (users[i].getId().equals(id)){
                    return users[i];
                }
            }
        }
        return null;
    }

    public User[] getAllUser() {
        return users;
    }

}
