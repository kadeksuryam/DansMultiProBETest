package com.surya.dansbetest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String hashPassword(String password)  {
        return passwordEncoder.encode(password);
    }

    public boolean isPasswordSame(String givenPassword, String storedPassword) {
        return passwordEncoder.matches(givenPassword, storedPassword);
    }
}
