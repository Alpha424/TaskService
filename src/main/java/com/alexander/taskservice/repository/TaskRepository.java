package com.alexander.taskservice.repository;

import com.alexander.taskservice.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс для взаимодействия с хранилищем объектов типа Task
 */

public interface TaskRepository extends JpaRepository<Task, String> {
}
