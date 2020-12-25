package com.opeyemi.superduperdrive;

import com.opeyemi.superduperdrive.mapper.UsersMapper;
import com.opeyemi.superduperdrive.model.Users;
import com.opeyemi.superduperdrive.services.HashService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DataTests {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    HashService hashService;

    @Test
    public void testUserInsertFunction() {
        String username = "OpeyemiOluwa2";
        String password = "ayanfe";
        String firstName = "Opeyemi";
        String lastName = "Idris";

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(password, encodedSalt);

        usersMapper.insert(new Users(null, username, encodedSalt, hashedPassword, firstName, lastName));
        Users users = usersMapper.getUser(username);

        assertEquals(hashedPassword, users.getPassword());
        assertEquals(lastName, users.getLastName());
        assertEquals(firstName, users.getFirstName());

    }
}
