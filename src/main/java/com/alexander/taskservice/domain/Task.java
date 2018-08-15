package com.alexander.taskservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс, описывающий задачу
 */
@Entity
public class Task {

    /**
     * Уникальный идентификатор задачи
     */
    @Id
    private String uuid = UUID.randomUUID().toString();

    /**
     * Текущий статус задачи
     */
    private Status status = Status.CREATED;

    /**
     * Время последнего изменения задачи
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private Timestamp lastUpdateTime;

    /**
     * Метод для обновления временной метки последнего изменения задачи. Срабатывает перед записью или обновлением соответствующей задачи в БД
     */
    @PrePersist
    @PreUpdate
    private void refreshTime() {
        this.lastUpdateTime = Timestamp.from(Instant.now());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(uuid, task.uuid) &&
                status == task.status &&
                Objects.equals(lastUpdateTime, task.lastUpdateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, status, lastUpdateTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", status=" + status +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }


    /**
     * Класс-перечисление, содержащий статусы задач
     */
    public enum Status {
        CREATED, // Задача создана (такой статус присваивается задаче после её создания)
        RUNNING, // Задача запущена
        FINISHED // Задача выполнена
    }
}
