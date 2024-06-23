package com.example.AppointmentApp.Entiry;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long otpId;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    private String otp;
    private LocalDateTime experationDate;

    public OTP(LocalDateTime experationDate, long otpId, User user, String otp) {
        this.experationDate = experationDate;
        this.otpId = otpId;
        this.user = user;
        this.otp = otp;
    }

    public OTP() {
    }

    public long getOtpId() {
        return otpId;
    }

    public void setOtpId(long otpId) {
        this.otpId = otpId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExperationDate() {
        return experationDate;
    }

    public void setExperationDate(LocalDateTime experationDate) {
        this.experationDate = experationDate;
    }
}
