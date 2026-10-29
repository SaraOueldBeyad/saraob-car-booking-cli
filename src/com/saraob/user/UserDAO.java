package com.saraob.user;

import java.util.UUID;

public interface UserDAO {
    User getUserById(UUID id);
    User[] getAllUser();
}
