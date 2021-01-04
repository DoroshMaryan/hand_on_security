package com.epam.dorosh.services.impl;

import com.epam.dorosh.entities.Otp;
import com.epam.dorosh.entities.User;
import com.epam.dorosh.repositories.OtpRepository;
import com.epam.dorosh.repositories.UserRepository;
import com.epam.dorosh.services.UserService;
import com.epam.dorosh.utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void auth(final User user) {
        final Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());

        if (optionalUser.isPresent()) {
            final User u = optionalUser.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOpt(u);
            } else {
                throw new BadCredentialsException("Bad credentials!");
            }
        } else {
            throw new BadCredentialsException("Bad credentials!");
        }

    }

    private void renewOpt(final User user) {
        Assert.notNull(user, "The user must not be null");

        final String code = GenerateCodeUtil.generateCode();
        final Optional<Otp> optionalOtp = otpRepository.findOtpByUsername(user.getUsername());

        Otp otp;
        if (optionalOtp.isPresent()) {
            otp = optionalOtp.get();
        } else {
            otp = new Otp();
            otp.setUsername(user.getUsername());
        }

        otp.setCode(code);
        otpRepository.save(otp);
    }

    @Override
    public boolean check(final Otp otpToValidate) {
        final Optional<Otp> optionalOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        boolean result = false;
        if (optionalOtp.isPresent()) {
            final Otp otp = optionalOtp.get();
            if (otp.getCode().equals(otpToValidate.getCode())) {
                result = true;
            }
        }

        return result;
    }
}
