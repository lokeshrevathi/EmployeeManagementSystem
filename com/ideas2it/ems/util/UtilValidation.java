package com.ideas2it.ems.util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilValidation {
    public static String regexName = "^[a-zA-Z]{2,15}[ ]?[a-zA-Z]{0,15}$";
    public static String regexDob = "^[0-3]{1}[0-9]{1}[-]{1}[0-1]{1}"
                            .concat("[0-9]{1}[-]{1}[0-9]{1}")
                            .concat("[0-9]{1}[0-9]{1}[0-9]{1}$");
    public static String regexRole = "^[a-zA-Z]{2,15}[ ]?[a-zA-Z]{0,15}$";
    public static String regexPhoneNo = "^[6-9]{1}[0-9]{9}$";
    public static String regexMailId = "^[a-zA-Z]{1}[0-9a-zA-Z]{0,15}?[.\\-_]"
                               .concat("?[a-zA-Z0-9]{1,20}")
                               .concat("[@][a-z]{1,20}[a-z0-9]")
                               .concat("{0,10}[.][a-z]{2,3}[.]?[a-z]{1,2}$");

   public static boolean isValid(String regexPattern, String fieldValue) {
       return Pattern.matches(regexPattern, fieldValue);
   }

    public static boolean checkDob(String dob) {
        boolean check;
        String regexDob = "^[0-3]{1}[0-9]{1}[-]{1}[0-1]{1}"
                             .concat("[0-9]{1}[-]{1}[0-9]{1}")
                             .concat("[0-9]{1}[0-9]{1}[0-9]{1}$");
        check = Pattern.matches(regexDob, dob);
        return validateDate(check, dob);
    }

    public static boolean validateDate(boolean isDate, String dob) {

        if (isDate) {
            int month = (dob.charAt(3) - 48)*10 + (dob.charAt(4) - 48);
            int day = (dob.charAt(0) - 48)*10 + (dob.charAt(1) - 48);
            int year = (dob.charAt(6) - 48)*10 + (dob.charAt(7) - 48)*10
                        + (dob.charAt(8) - 48)*10 +(dob.charAt(9) - 48);
            if (month == 2 && (!(year % 4 == 0 || (year % 400 == 0
                && year % 100 != 0)) && day > 28)) { 
                isDate = false;
            } else if ((month == 4 || month == 6
                       || month == 9 || month == 11)
                       && (day > 30)) {
                isDate = false;
            } else if (month == 0 || day == 0 || year == 0) {
                isDate = false;
            } else {
                isDate = true;
            }
        } else {
            isDate = false;
        }
        return isDate;
    }

    // public static boolean isValidExperience(float experience) {
        
}