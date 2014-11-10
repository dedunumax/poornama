package com.poornama.data.dao;

import com.poornama.api.objects.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

/**
 * Created by dedunu on 10/22/14.
 */
public class UserDAOTest {

    private UserDAO userDAO = new UserDAO();

    @Ignore
    @Test
    public void createUser() {
        User user1 = new User();
        Random random = new Random();
        int value = random.nextInt(10000);
        user1.setDisplayName("Temp" + value);
        user1.setUserName("Temp" + value);
        user1.setPassword("Temp" + value);
        userDAO.create(user1);
        Assert.assertTrue(true);
    }

    @Test
    public void getUserByName() {
        User user = userDAO.getByUserName("dedunumax");
        Assert.assertTrue(user.getDisplayName().equals("Dedunu Dhananjaya"));
    }

    @Test(expected = NullPointerException.class)
    public void getUserByNameNotExisting() {
        User user = userDAO.getByUserName("notdedunumax");
        String displayName = user.getDisplayName();
    }

    @Ignore
    @Test
    public void deleteUser() {
        User user1 = new User();
        Random random = new Random();
        int value = random.nextInt(10000);
        user1.setDisplayName("Temp" + value);
        user1.setUserName("Temp" + value);
        user1.setPassword("Temp" + value);
        userDAO.create(user1);
        User user = userDAO.getByUserName("Temp" + value);
        userDAO.delete(user);
        Assert.assertTrue(true);
    }
}