package com.bwongo.commons.exceptions.model;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
public record ExceptionPayLoad(String uri, String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
}
