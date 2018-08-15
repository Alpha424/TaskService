package com.alexander.taskservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Вспомогательный класс для валидации идентификатора UUID
 */

@Component
public class UUIDValidator {

    /**
     * Метод, проверяющий валидность строкового представления UUID, переданного в качестве параметра
     * @param uuid Строковое представление UUID
     * @return Возвращает true, если идентификатор - валидный, иначе - false
     */
    public boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
