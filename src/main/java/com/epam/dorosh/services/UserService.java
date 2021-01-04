package com.epam.dorosh.services;

import com.epam.dorosh.entities.Otp;
import com.epam.dorosh.entities.User;

public interface UserService {
    void addUser(User user);

    void auth(User user);

    boolean check(Otp otpToValidate);
}
