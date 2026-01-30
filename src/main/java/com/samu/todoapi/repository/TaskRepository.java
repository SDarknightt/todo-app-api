package com.samu.todoapi.repository;

import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT new com.samu.todoapi.dto.TaskDetailsDTO(t.id, t.title, t.description, t.status, t.owner.id) FROM Task t WHERE t.id = :id")
    Optional<TaskDetailsDTO> findByIdAsDTO(Long id);

    @Query("SELECT new com.samu.todoapi.dto.TaskDetailsDTO(t.id, t.title, t.description, t.status, t.owner.id) FROM Task t WHERE t.owner.id = :id")
    List<TaskDetailsDTO> findAllByUserIdAsDTO(Long id);
}
