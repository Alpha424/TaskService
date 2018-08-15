package com.alexander.taskservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс-исключение, представляющий ошибку, возникающую при передаче несуществующего идентификатора задачи в конечную точку веб-сервиса GET /task/{id}
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoTaskFoundException extends RuntimeException {
    public NoTaskFoundException(String description) {
        super(description);
    }
}
