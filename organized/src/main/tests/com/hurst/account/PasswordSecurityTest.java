package com.hurst.account;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordSecurityTest {

    private String securedPassword;
    private final String originalPassword = "Pa22w0rd1!";



    @Before
    public void setUp() {
        securedPassword = PasswordSecurity.securePassword(originalPassword);
    }

    @Test
    public void validatePasswordTestToPass() {
        assertTrue(PasswordSecurity.validatePassword(originalPassword, securedPassword));
    }

    @Test
    public void validatePasswordToFail() {
        assertFalse(PasswordSecurity.validatePassword(originalPassword.toLowerCase(), securedPassword));
    }

    @Test
    public void checkPasswordRequirementsTestToPass() {
        assertTrue(PasswordSecurity.checkPasswordRequirements(originalPassword, originalPassword));
    }
}