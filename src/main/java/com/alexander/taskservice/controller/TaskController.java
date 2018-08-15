package com.alexander.taskservice.controller;

import com.alexander.taskservice.domain.Task;
import com.alexander.taskservice.exception.InvalidUUIDException;
import com.alexander.taskservice.exception.NoTaskFoundException;
import com.alexander.taskservice.service.TaskService;
import com.alexander.taskservice.service.impl.DelayedTaskExecutionService;
import com.alexander.taskservice.util.UUIDValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Класс-контроллер приложения, содержащий конечные точки доступа к REST сервису
 */
@RestController
public class TaskController {
    private TaskService taskService;
    private DelayedTaskExecutionService taskExecutionService;
    private UUIDValidator uuidValidator;

    public TaskController(TaskService taskService, DelayedTaskExecutionService taskExecutionService, UUIDValidator uuidValidator) {
        this.taskService = taskService;
        this.taskExecutionService = taskExecutionService;
        this.uuidValidator = uuidValidator;
    }

    /**
     * Конечная точка веб-сервиса для получения задачи по UUID
     * @param uuid UUID запрашиваемой задачи
     * @return Сущность Task соответствующей задачи
     */
    @GetMapping(value = "/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") String uuid) {
        if(!uuidValidator.isValidUUID(uuid)) {
            throw new InvalidUUIDException("Invalid task UUID");
        }
        Optional<Task> task = taskService.findTaskByUUID(uuid);
        if(!task.isPresent()) {
            throw new NoTaskFoundException("No task with such UUID found");
        }
        return ResponseEntity.ok(task.get());
    }

    /**
     * Конечная точка веб-сервиса для создания новой задачи. Метод создает сущность задачи, запускает её на отложенное выполнение
     * @return UUID созданной задачи
     */
    @PostMapping(value = "/task")
    public ResponseEntity<String> createTask() {
        Task newTask = taskService.saveTask(new Task());
        taskExecutionService.scheduleTaskExecution(newTask);
        return ResponseEntity.accepted().body(newTask.getUuid());
    }
}
