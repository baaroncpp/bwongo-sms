package com.bwongo.commons.text;


import com.bwongo.commons.exceptions.BadRequestException;

import java.util.regex.Pattern;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 */
public class StringRegExUtil {

    public static void stringOfStandardPassword(String value, String message){
        if(!standardPassword(value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyNumbers(String value, String message){
        if(!onlyNumbersAndChars(value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyNumbersAndChars(String value, String message){
        if(!Pattern.matches("^[a-zA-Z0-9]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyNumbersAndCharsAndOneSpace(String value, String message){
        if(!Pattern.matches("^[a-zA-Z0-9 ]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyCharsNoneCaseSensitive(String value, String message){
        if(!Pattern.matches("^[a-zA-Z]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyCharsNoneCaseSensitiveAndOneSpace(String value, String message){
        if(!Pattern.matches("^[a-zA-Z ]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyLowerCase(String value, String message){
        if(!Pattern.matches("^[a-z]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfOnlyUpperCase(String value, String message){
        if(!Pattern.matches("^[A-Z]+$", value)){
            throw new BadRequestException(message);
        }
    }

    public static void stringOfInternationalPhoneNumber(String value, String message, Object ... params){
        if(!Pattern.matches("^\\+?[1-9][0-9]{7,14}$", value)){
            throw new BadRequestException(message, params);
        }
    }

    public static void stringOfEmail(String value, String message, Object ... params){
        if(!validEmail(value)){
            throw new BadRequestException(message, params);
        }
    }

    public static boolean validEmail(String value){
        return Pattern.matches("^(.+)@(.+)$", value);
    }

    public static boolean standardPassword(String value){
        return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", value);
    }

    public static boolean onlyNumbersAndChars(String value){
        return Pattern.matches("^[a-zA-Z0-9]+$", value);
    }
}
