package com.niemczuk.taskmanagerbackend.repository;

import com.niemczuk.taskmanagerbackend.model.Task;
import com.niemczuk.taskmanagerbackend.model.dto.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(value = "SELECT new com.niemczuk.taskmanagerbackend.model.dto.TaskDto(" +
            "t.taskId, " +
            "t.name, " +
            "t.description, " +
            "t.status, " +
            "t.creationDate" +
            ") FROM Task t WHERE t.owner.username = ?1")
    List<TaskDto> findAllByOwner_Username(String username);

    @Query(value = "SELECT new com.niemczuk.taskmanagerbackend.model.dto.TaskDto(" +
            "t.taskId, " +
            "t.name, " +
            "t.description, " +
            "t.status, " +
            "t.creationDate" +
            ") FROM Task t WHERE t.taskId = ?1 AND t.owner.username = ?2")
    Optional<TaskDto> findByTaskIdAndOwner_Username(UUID id, String username);
}
