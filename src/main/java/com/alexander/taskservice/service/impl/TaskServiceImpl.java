package com.alexander.taskservice.service.impl;

import com.alexander.taskservice.domain.Task;
import com.alexander.taskservice.repository.TaskRepository;
import com.alexander.taskservice.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Реализация интерфейса TaskService. Данная реализация нацелена на работу с БД
 */

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {
        Task entryToUpdate = taskRepository.getOne(task.getUuid());
        entryToUpdate.setStatus(task.getStatus());
        return taskRepository.save(entryToUpdate);
    }

    @Override
    public Optional<Task> findTaskByUUID(String uuid) {
        return taskRepository.findById(uuid);
    }

}
