package com.opeyemi.superduperdrive.services;

import com.opeyemi.superduperdrive.mapper.UsersMapper;
import com.opeyemi.superduperdrive.model.Users;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UsersMapper usersMapper;
    private final HashService hashService;


    public UserService(UsersMapper usersMapper, HashService hashService) {
        this.usersMapper = usersMapper;
        this.hashService = hashService;
    }

    public boolean isUserAvailable(String username){
        return usersMapper.getUser(username) != null;
    }

    public int createUser(Users users){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = hashService.getHashedValue(users.getPassword(), encodedSalt);
       return this.usersMapper.insert(new Users(null, users.getUsername(), encodedSalt, hashedPassword, users.getFirstName(), users.getLastName()));

    }
}
