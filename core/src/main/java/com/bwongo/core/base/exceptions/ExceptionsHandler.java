package com.bwongo.core.base.exceptions;

import com.bwongo.commons.exceptions.*;
import com.bwongo.commons.exceptions.model.ExceptionPayLoad;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:36 PM
 **/
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException, HttpServletRequest request){

        var httpStatus = HttpStatus.BAD_REQUEST;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                badRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request){

        var httpStatus = HttpStatus.NOT_FOUND;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                resourceNotFoundException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {InsufficientAuthenticationException.class})
    public ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException insufficientAuthenticationException, HttpServletRequest request){

        var httpStatus = HttpStatus.FORBIDDEN;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                insufficientAuthenticationException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest request){

        var httpStatus = HttpStatus.UNAUTHORIZED;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                badCredentialsException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {DefaultException.class})
    public ResponseEntity<Object> handleDefaultException(DefaultException defaultException, HttpServletRequest request){

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                defaultException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest webRequest){
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var exceptionPayLoad = new ExceptionPayLoad(
                webRequest.getContextPath(),
                exception.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request){

        var httpStatus = HttpStatus.valueOf(403);

        var exceptionPayLoad = new ExceptionPayLoad(
                request.getRequestURI(),
                accessDeniedException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class, org.springframework.security.authentication.BadCredentialsException.class, MalformedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<Object> handleSecurityException(Exception ex, HttpServletRequest request){

        ResponseEntity<Object> response = null;
        HttpStatus httpStatus = null;

        if(ex instanceof org.springframework.security.authentication.BadCredentialsException){
            httpStatus = HttpStatus.valueOf(401);
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "Authentication Failure",
                    httpStatus,
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, httpStatus);
        }

        if(ex instanceof org.springframework.security.access.AccessDeniedException){
            httpStatus = HttpStatus.valueOf(403);
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "Not Authorized!",
                    httpStatus,
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, httpStatus);
        }

        if(ex instanceof MalformedJwtException){
            httpStatus = HttpStatus.valueOf(403);
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "JWT Signature not valid!",
                    HttpStatus.valueOf(403),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, httpStatus);
        }

        if(ex instanceof ExpiredJwtException){
            httpStatus = HttpStatus.valueOf(403);
            var exceptionPayLoad = new ExceptionPayLoad(
                    request.getRequestURI(),
                    "JWT already expired!",
                    httpStatus,
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            response = new ResponseEntity<>(exceptionPayLoad, httpStatus);
        }

        return response;
    }
}
