package com.bwongo.commons.exceptions;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
public class DefaultException  extends RuntimeException{
    public DefaultException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
    }

    public DefaultException(String message) {
        super(message);
    }
}

