package com.hurst;

import com.hurst.account.PasswordSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Testing.
 */
public class testing {

    private static Logger logger = LogManager.getLogger(testing.class);

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){
        String originalPassword = "Pa22w0rd1!";
        String securePassword = PasswordSecurity.securePassword(originalPassword);
        System.out.println(securePassword);
    }

}
