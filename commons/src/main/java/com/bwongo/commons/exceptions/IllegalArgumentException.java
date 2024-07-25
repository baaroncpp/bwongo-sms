package com.bwongo.commons.exceptions;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message, Object... messageConstants) {
        super(String.format(message, messageConstants));
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
