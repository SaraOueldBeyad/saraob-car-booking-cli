package com.saraob.user;

import java.util.UUID;

public interface UserDAO {
    public User getUserById(UUID id);
    public User[] getAllUser();
}
