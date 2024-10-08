package com.bwongo.commons.utils;

import com.bwongo.commons.exceptions.*;
import com.bwongo.commons.exceptions.model.ExceptionType;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static com.bwongo.commons.utils.ConstantMessages.IS_INVALID_FIELD;


/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
public class Validate {
    private Validate() {}

    public static void isTrue(boolean value, ExceptionType exceptionType, String message, Object ... params){
        if (!value && exceptionType.equals(ExceptionType.BAD_REQUEST)) {
            throw new BadRequestException(message, params);
        }

        if (!value && exceptionType.equals(ExceptionType.RESOURCE_NOT_FOUND)) {
            throw new ResourceNotFoundException(message, params);
        }

        if (!value && exceptionType.equals(ExceptionType.BAD_CREDENTIALS)) {
            throw new BadCredentialsException(message, params);
        }

        if (!value && exceptionType.equals(ExceptionType.INSUFFICIENT_AUTH)) {
            throw new InsufficientAuthenticationException(message, params);
        }

        if (!value && exceptionType.equals(ExceptionType.ACCESS_DENIED)) {
            throw new AccessDeniedException(message, params);
        }

    }

    public static void notNull(Object value, ExceptionType exceptionType, String message, Object ... params){

        if (value == null && exceptionType.equals(ExceptionType.BAD_REQUEST)) {
            throw new BadRequestException(message, params);
        }

        if (value == null && exceptionType.equals(ExceptionType.RESOURCE_NOT_FOUND)) {
            throw new ResourceNotFoundException(message, params);
        }

        if (value == null && exceptionType.equals(ExceptionType.BAD_CREDENTIALS)) {
            throw new BadCredentialsException(message, params);
        }

        if (value == null && exceptionType.equals(ExceptionType.INSUFFICIENT_AUTH)) {
            throw new InsufficientAuthenticationException(message, params);
        }

        if (value == null && exceptionType.equals(ExceptionType.ACCESS_DENIED)) {
            throw new AccessDeniedException(message, params);
        }

    }

    public static void notEmpty(String value, String message, Object ... params){
        if (!StringUtils.hasLength(value)) {
            throw new BadRequestException(message, params);
        }
    }

    public static void isPresent(Optional<?> value, String message, Object ... params){
        if(value.isEmpty()){
            throw new ResourceNotFoundException(String.format(message,params));
        }
    }

    /*private static void checkForbiddenWord(String word){
        boolean anyMatch = FORBIDDEN_WORDS.stream()
                .anyMatch(fw -> fw.contains(word));

        if(anyMatch){
            throw new BadRequestException(String.format(IS_FORBIDDEN_WORD, word));
        }
    }*/

    public static void doesObjectContainFields(Object object, List<String> fields){
        for(String value : fields){
            Validate.isTrue(doesObjectContainField(object, value), ExceptionType.BAD_REQUEST , String.format(IS_INVALID_FIELD, value));
        }
    }

    public static boolean doesObjectContainField(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public static void isAcceptableDateFormat(String stringDate){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        try {
            sdf.parse(stringDate);
        } catch (ParseException e) {
            throw new BadRequestException("invalid date format, use: yyyy-MM-dd HH:mm:ss");
        }
    }
}
