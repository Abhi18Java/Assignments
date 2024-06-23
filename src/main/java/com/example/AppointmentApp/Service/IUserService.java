package com.example.AppointmentApp.Service;

import com.example.AppointmentApp.Entiry.User;

public interface IUserService {

    public User addUser(User user);
    public String forgetPassword(String email);
    public void sendEmail(String to, String subject, int otp);
    public void saveOtp(String email, int otp);
    public boolean verifyOtp(String otp);
    public String resetPassword(String otp, String newPassword, String confirmPassword);

}
