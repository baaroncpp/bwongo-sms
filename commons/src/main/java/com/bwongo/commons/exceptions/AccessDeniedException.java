package com.bwongo.commons.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
@Slf4j
public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message, Object ... messageConstants){
        super(String.format(message, messageConstants));
        log.error(String.format(message, messageConstants));
    }

    public AccessDeniedException(String message) {
        super(message);
        log.error(message);
    }
}
