package com.example.AppointmentApp.Service;

import com.example.AppointmentApp.Entiry.OTP;
import com.example.AppointmentApp.Entiry.User;
import com.example.AppointmentApp.Respository.OTPRepository;
import com.example.AppointmentApp.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final int OTP_VALID_DURATION = 10;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String forgetPassword(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (!checkEmail.isPresent()) {
            return "Email not found";
        }

        int otp = otpGenerator();
        sendEmail(email, "Forget Password", otp);
        saveOtp(email, otp);

        return "Email Verified, OTP has been sent to your email";
    }

    @Override
    public void sendEmail(String to, String subject, int otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("This is the OTP for reset password: " + otp);
        javaMailSender.send(message);
    }

    @Override
    public void saveOtp(String email, int otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OTP newOtp = new OTP();
        newOtp.setUser(user);
        newOtp.setOtp(String.valueOf(otp));
        newOtp.setExperationDate(LocalDateTime.now().plusMinutes(OTP_VALID_DURATION));
        otpRepository.save(newOtp);
    }

    public int otpGenerator() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Ensures a 6-digit OTP
    }

    @Override
    public boolean verifyOtp(String otp) {
        Optional<OTP> otpOptional = otpRepository.findByOtp(otp);
        if(otpOptional.isPresent()){
            OTP retrivedOtp = otpOptional.get();
            if(retrivedOtp.getExperationDate().isAfter(LocalDateTime.now())){
                return true;
            }
            return false;
        }
    return false;
    }

    @Override
    public String resetPassword(String otp, String newPassword, String confirmPassword){
        if(!newPassword.equals(confirmPassword)){
            return "Password not matched";
        }
        Optional<OTP> otpOptional = otpRepository.findByOtp(otp);
            if(otpOptional.isPresent()){
                OTP retrivedOtp = otpOptional.get();
                if(retrivedOtp.getExperationDate().isAfter(LocalDateTime.now())){
                    User user = retrivedOtp.getUser();
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    otpRepository.delete(retrivedOtp);
                    return "Password Reset successfully";
                }
                return "OTP has expired";
            }

            return "Invalid OTP";
    }
}
