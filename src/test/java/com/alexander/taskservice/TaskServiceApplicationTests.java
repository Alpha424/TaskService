package com.alexander.taskservice;

import com.alexander.taskservice.domain.Task;
import com.alexander.taskservice.service.TaskService;
import com.alexander.taskservice.util.UUIDValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceApplicationTests {
    @Value("${task.execution.time-ms}") private long taskExecutionTime;
    private TaskService taskService;
    private UUIDValidator uuidValidator;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUuidValidator(UUIDValidator uuidValidator) {
        this.uuidValidator = uuidValidator;
    }

    @Test
    public void testTaskService() {
        Task t1 = taskService.saveTask(new Task());
        assertEquals(Task.Status.CREATED, t1.getStatus());
        t1.setStatus(Task.Status.RUNNING);
        t1 = taskService.updateTask(t1);
        assertEquals(Task.Status.RUNNING, t1.getStatus());
        t1.setStatus(Task.Status.FINISHED);
        t1 = taskService.updateTask(t1);
        assertEquals(Task.Status.FINISHED, t1.getStatus());
    }

    @Test
    public void testEquals() {
        Task t1 = taskService.saveTask(new Task());
        Task t2 = taskService.saveTask(new Task());
        assertNotEquals(t1, t2);
        t2.setUuid(t1.getUuid());
        t2.setStatus(t1.getStatus());
        t2.setLastUpdateTime(t1.getLastUpdateTime());
        assertEquals(t1, t2);
    }

    @Test
    public void testUUIDValidator() {
        Task t1 = taskService.saveTask(new Task());
        assertTrue(uuidValidator.isValidUUID(t1.getUuid()));
        String brokenUUID = "fbf8d75a-e238-40c1-9264-87c4c89b8ee7ddddddddddddddddddddddddddddddddddd";
        assertFalse(uuidValidator.isValidUUID(brokenUUID));
        String emptyString = "";
        assertFalse(uuidValidator.isValidUUID(emptyString));
        String uuidWithoutDashes = "fbf8d75ae23840c1926487c4c89b8ee7";
        assertFalse(uuidValidator.isValidUUID(uuidWithoutDashes));
    }

}
