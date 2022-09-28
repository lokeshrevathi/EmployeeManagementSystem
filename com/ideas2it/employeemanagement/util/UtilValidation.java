package com.ideas2it.employeemanagement.util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilValidation {
    public static String regexName = "^[a-zA-Z]{2,15}([ ]?[a-zA-Z]{0,15}){0,}$";
    public static String regexPhoneNo = "^[6-9]{1}[0-9]{9}$";
    public static String regexMailId = "^[a-zA-Z]{1}[0-9a-zA-Z]{0,15}?[.\\-_]"
                                      .concat("?[a-zA-Z0-9]{1,20}")
                                      .concat("[@][a-z]{1,20}[a-z0-9]")
                                      .concat("{0,10}[.][a-z]{2,3}[.]?[a-z]{1,3}$");

    public static boolean isValid(String regexPattern, String fieldValue) {
        return Pattern.matches(regexPattern, fieldValue);
    }
}