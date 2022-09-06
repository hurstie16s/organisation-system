package com.hurst;

import com.hurst.account.PasswordSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class testing {

    private static Logger logger = LogManager.getLogger(testing.class);

    public static void main(String[] args){
        String originalPassword = "Pa22w0rd1!";
        String securePassword = PasswordSecurity.securePassword(originalPassword);
        System.out.println(securePassword);
    }

}
