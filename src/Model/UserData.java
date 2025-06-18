package model;

import java.sql.Timestamp;

public class UserData {
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String confirmPassword;
    private String status;
    private String imagePath; //changed from byte[] to String
    private Timestamp registrationDate;

    public UserData(String username, String email, String password, String confirmPassword, String phone,
            String imagePath, String status, Timestamp registrationDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = "";
        this.phone = phone;
        this.imagePath = "default.png";
        this.status = (status != null) ? status : "active";
        this.registrationDate = (registrationDate != null) ? registrationDate
                : new Timestamp(System.currentTimeMillis());
    }

    public UserData() {
    }

    public UserData(String userName2, String email2, String password2, String contact) {
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
