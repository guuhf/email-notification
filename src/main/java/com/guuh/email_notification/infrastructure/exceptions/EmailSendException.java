package com.guuh.email_notification.infrastructure.exceptions;

public class EmailSendException extends RuntimeException{
    public EmailSendException(String message){super(message);}
    public EmailSendException(String message, Throwable cause){super(message, cause);}
}
