package com.samu.todoapi.service;

import com.samu.todoapi.dto.*;
import com.samu.todoapi.entity.Authority;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.exception.ForbiddenException;
import com.samu.todoapi.exception.NotFoundException;
import com.samu.todoapi.mapper.TaskMapper;
import com.samu.todoapi.repository.TaskRepository;
import com.samu.todoapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       TaskMapper taskMapper,
                       UserService userService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public TaskCreateResponseDTO create(TaskCreateRequestDTO taskDTO) {
        User loggedUser = userService.getLoggedUser();
        return createForUser(taskDTO, loggedUser.getId());
    }

    @Transactional
    public TaskCreateResponseDTO createForUser(TaskCreateRequestDTO taskDTO, UUID userId) {
        User owner = userRepository.getReferenceById(userId);
        Task transientTask = taskMapper.toEntity(taskDTO);
        transientTask.setOwner(owner);

        Task newTask = taskRepository.save(transientTask);
        return taskMapper.toCreateResponseDTO(newTask);
    }

    @Transactional
    public TaskUpdateResponseDTO update(UUID id, TaskUpdateRequestDTO taskDTO) {
        User loggedUser = userService.getLoggedUser();
        Task task = taskRepository.findByIdWithOwner(id)
                        .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));

        if (!task.getOwner().getEmail().equals(loggedUser.getEmail())) {
            throw new ForbiddenException();
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        return taskMapper.toUpdateResponseDTO(task);
    }

    @Transactional
    public TaskUpdateResponseDTO updateForUser(UUID id, TaskUpdateRequestDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        return taskMapper.toUpdateResponseDTO(task);
    }

    public TaskDetailsDTO findById(UUID id) {
        User loggedUser = userService.getLoggedUser();
        if (loggedUser.getAuthority() == Authority.ADMIN) {
            return taskRepository.findByIdAsDTO(id)
                    .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));
        }
        return taskRepository.findByIdIfOwnerAsDTO(id, loggedUser.getId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));
    }

    public List<TaskDetailsDTO> findAll() {
        User loggedUser = userService.getLoggedUser();
        return  taskRepository.findAllByUserIdAsDTO(loggedUser.getId());
    }

    public List<TaskDetailsDTO> findAllByUserId(UUID id) {
        return  taskRepository.findAllByUserIdAsDTO(id);
    }
}
