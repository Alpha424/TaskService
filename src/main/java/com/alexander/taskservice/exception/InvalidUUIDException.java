package com.alexander.taskservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс-исключение, представляющий ошибку, возникающую при передаче неверного идентификатора задачи в конечную точку веб-сервиса GET /task/{id}
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUUIDException extends RuntimeException {
    public InvalidUUIDException(String description) {
        super(description);
    }
}
