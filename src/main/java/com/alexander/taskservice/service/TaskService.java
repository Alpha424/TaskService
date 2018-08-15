package com.alexander.taskservice.service;

import com.alexander.taskservice.domain.Task;

import java.util.Optional;

/**
 * Абстракция, представляющая собой интерфейс для доступа к сервису взаимодействия с хранилищем задач
 */

public interface TaskService {
    /**
     * Метод для сохранения новой записи задачи
     * @param task Задача для сохранения
     * @return Сохранённая задача
     */
    Task saveTask(Task task);

    /**
     * Метод для внесения изменений в задачу
     * @param task Задача с внесёнными изменениями
     * @return Изменённая задача
     */
    Task updateTask(Task task);

    /**
     * Метод для нахождения записи задачи по её идентификатору
     * @param uuid Идентификатор задачи
     * @return Задача
     */
    Optional<Task> findTaskByUUID(String uuid);
}
