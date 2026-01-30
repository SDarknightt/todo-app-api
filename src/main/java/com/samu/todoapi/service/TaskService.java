package com.samu.todoapi.service;

import com.samu.todoapi.dto.TaskCreateDTO;
import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.dto.TaskUpdateDTO;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.mapper.TaskMapper;
import com.samu.todoapi.repository.TaskRepository;
import com.samu.todoapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public TaskCreateDTO create(TaskCreateDTO taskDTO) {
       return create(taskDTO, 1L);
    }

    public TaskCreateDTO create(TaskCreateDTO taskDTO, Long userId) {
        User owner = userRepository.getReferenceById(userId);
        Task transientTask = taskMapper.toEntity(taskDTO);
        transientTask.setOwner(owner);

        Task newTask = taskRepository.save(transientTask);
        return taskMapper.toCreateDTO(newTask);
    }

    @Transactional
    public TaskUpdateDTO update(TaskUpdateDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        return taskDTO;
    }

    public TaskDetailsDTO findById(Long id) {
        return taskRepository.findByIdAsDTO(id)
                                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
    }

    public List<TaskDetailsDTO> findAll() {
        // Depois pegar da sessão do usuário
        return  taskRepository.findAllByUserIdAsDTO(1L);
    }
}
