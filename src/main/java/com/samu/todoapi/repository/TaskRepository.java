package com.samu.todoapi.repository;

import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT new com.samu.todoapi.dto.TaskDetailsDTO(t.id, t.title, t.description, t.status, t.owner.id) FROM Task t WHERE t.id = :id AND t.owner.id = :userId")
    Optional<TaskDetailsDTO> findByIdIfOwnerAsDTO(UUID id, UUID userId);

    @Query("SELECT new com.samu.todoapi.dto.TaskDetailsDTO(t.id, t.title, t.description, t.status, t.owner.id) FROM Task t WHERE t.id = :id")
    Optional<TaskDetailsDTO> findByIdAsDTO(UUID id);

    @Query("SELECT new com.samu.todoapi.dto.TaskDetailsDTO(t.id, t.title, t.description, t.status, t.owner.id) FROM Task t WHERE t.owner.id = :id")
    List<TaskDetailsDTO> findAllByUserIdAsDTO(UUID id);

    @Query("SELECT t FROM Task t JOIN FETCH t.owner WHERE t.id = :id")
    Optional<Task> findByIdWithOwner(UUID id);
}
