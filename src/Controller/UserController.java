package Controller;

import dao.UserDao;
import Model.UserData;
import java.util.List;

public class UserController {
    private final UserDao userDao = new UserDao();

    public List<UserData> getUsersByStatus(String status) {
        return userDao.getUsersByStatus(status);
    }

    public boolean banUser(int userId) {
        return userDao.updateUserStatus(userId, "banned");
    }

    public boolean unbanUser(int userId) {
        return userDao.updateUserStatus(userId, "active");
    }

}
