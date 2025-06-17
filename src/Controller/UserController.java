package controller;

import dao.UserDao;
import model.UserData;
import java.util.List;

public class UserController {
    private final UserDao userDao = new UserDao();

    public List<UserData> getUsersByStatus(String status) {
        return userDao.getUsersByStatus(status);
    }

    public boolean banUser(int userId) {
        return UserDao.updateUserStatus(userId, "banned");
    }

    public boolean unbanUser(int userId) {
        return UserDao.updateUserStatus(userId, "active");
    }
    
}
