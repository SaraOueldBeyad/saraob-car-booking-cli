package com.saraob.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO {

    private final String filePath;

    public UserFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public User getUserById(UUID id) {
        if (id != null) {
            User[] users = getAllUser();
            for (User user : users) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User[] getAllUser() {
        User[] users = null;
        File file = new File(filePath);
        if (file.exists() || file.length() == 0) {
            return new User[0];
        }

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (User[]) ois.readObject();
            ois.close();
            fis.close();
            return users;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
