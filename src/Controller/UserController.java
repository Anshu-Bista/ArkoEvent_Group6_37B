package controller;

import dao.UserDao;
import model.UserData;
import java.util.List;

public class UserController {
    private final UserDao userDao = new UserDao();

    public List<UserData> getAllUsers() {
        return userDao.getAllUsers();
    }
}
