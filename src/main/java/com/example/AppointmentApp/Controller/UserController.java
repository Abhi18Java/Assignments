package com.example.AppointmentApp.Controller;


import com.example.AppointmentApp.Entiry.User;
import com.example.AppointmentApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User adduser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam String email){
        String response = userService.forgetPassword(email);
        if (response.equals("Email not found")) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam String otp){
        boolean verified = userService.verifyOtp(otp);
            if(verified){
                return ResponseEntity.ok().body("OTP verified");
            }
            return ResponseEntity.badRequest().body("Invalid OTP");

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> newPassword(@RequestParam String otp, String newPassword, String confirmPassword){
                String response = userService.resetPassword(otp,newPassword,confirmPassword);
                    {
                    if(response.equals("Password Reset Successfully")){
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.status(400).body(response);
                }
    }



}
