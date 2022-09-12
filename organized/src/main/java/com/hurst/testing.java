package com.hurst;

import com.hurst.account.PasswordSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        LocalDate currentDate = LocalDate.of(2022, 9, 11);
        LocalTime currentTime = LocalTime.of(16, 56);

        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);
        System.out.println(currentDateTime);
        System.out.println(currentDateTime.toString().substring(0, 10));
        System.out.println(currentDateTime.toString().substring(11));

        int a = 50;
        int b = 3;

        int c = a / b;

        int d = a - (c*b);

        System.out.println(c);
        System.out.println(d);

        LocalDateTime.of(2022, 9, 12, 0, 50);
    }

}
