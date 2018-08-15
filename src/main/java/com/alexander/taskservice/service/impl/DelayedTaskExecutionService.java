package com.alexander.taskservice.service.impl;

import com.alexander.taskservice.domain.Task;
import com.alexander.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс-сервис, предназначенный для отложенного выполнения задач.
 */

@Service
public class DelayedTaskExecutionService {
    /**
     * Переменная, содержащая текущий временной интервал, в течение которого задача будет висеть в статусе RUNNING
     * Значение переменной задается в файле конфигурации
     */
    @Value("${task.execution.time-ms}") private long taskExecutionTime;
    private TaskService taskService;

    public DelayedTaskExecutionService(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * Метод, предназначенный для запуска отложенного выполнения задачи
     * @param task Задача для выполнения
     */
    public void scheduleTaskExecution(Task task) {
        new Timer(
                true // Чтобы не мешать преждевременному закрытию программы
        )
                .schedule(
                        new TaskExecutionTask(task),
                        taskExecutionTime
                );
    }

    /**
     * Внутренний класс, содержащий логику завершения выполнения задачи по таймеру.
     */
    private class TaskExecutionTask extends TimerTask {
        private Task taskToExecute;

        private TaskExecutionTask(Task task) {
            this.taskToExecute = task;
            runTask(); // Переводим задачу в состояние RUNNING
        }

        private void runTask() {
            this.taskToExecute.setStatus(Task.Status.RUNNING);
            taskService.updateTask(taskToExecute);
        }

        /**
         * Метод выполняется после истечения заданного временного интервала
         * Переводим задачу в состояние FINISHED
         */
        @Override
        public void run() {
            taskToExecute.setStatus(Task.Status.FINISHED);
            taskService.updateTask(taskToExecute);
        }
    }
}
