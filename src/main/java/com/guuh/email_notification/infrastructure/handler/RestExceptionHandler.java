package com.guuh.email_notification.infrastructure.handler;

import com.guuh.email_notification.infrastructure.exceptions.EmailSendException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailSendException.class)
    private ResponseEntity<RestErrorMessage> illegalDateHandler(EmailSendException e){
        RestErrorMessage threatResponse = new RestErrorMessage
                (HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
