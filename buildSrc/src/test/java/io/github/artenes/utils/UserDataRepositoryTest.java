package io.github.artenes.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataRepositoryTest {

    private UserDataRepository userDataRepository;

    @Before
    public void setUp() throws SQLException {
        userDataRepository = new UserDataRepository("temp.db");
    }

    @Test
    public void insertAll_insertAllUsers() throws SQLException {
        List<User> users = asUsers(
                "UserA", "emaila@email.com",
                "UserB", "emailb@email.com",
                "UserC", "emailc@email.com"
        );

        boolean result = userDataRepository.insertAll(users);
        int amount = userDataRepository.getCount();

        assertTrue(result);
        assertEquals(users.size(), amount);
    }

    @After
    public void tearDown() {
        if (!new File("temp.db").delete()) {
            fail("Failed to delete temp.db");
        }
    }

    private List<User> asUsers(String... data) {
        List<User> users = new ArrayList<>();
        for (int index = 0; index < data.length; index += 2) {
            String name = data[index];
            String email = data[index + 1];
            users.add(new User(name, email));
        }
        return users;
    }

}