package com.samu.todoapi.service;

import com.samu.todoapi.dto.TaskCreateDTO;
import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.dto.TaskUpdateDTO;
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
import java.util.Optional;

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

    public TaskCreateDTO create(TaskCreateDTO taskDTO) {
        User loggedUser = userService.getLoggedUser();
        return create(taskDTO, loggedUser.getId());
    }

    public TaskCreateDTO create(TaskCreateDTO taskDTO, Long userId) {
        User owner = userRepository.getReferenceById(userId);
        Task transientTask = taskMapper.toEntity(taskDTO);
        transientTask.setOwner(owner);

        Task newTask = taskRepository.save(transientTask);
        return taskMapper.toCreateDTO(newTask);
    }

    @Transactional
    public TaskUpdateDTO update(Long id, TaskUpdateDTO taskDTO) {
        User loggedUser = userService.getLoggedUser();
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));

        if (!task.getOwner().equals(loggedUser) && (loggedUser.getAuthority() != Authority.ADMIN)) {
            throw new ForbiddenException();
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        return taskDTO;
    }

    public TaskDetailsDTO findById(Long id) {
        User loggedUser = userService.getLoggedUser();
        if (loggedUser.getAuthority() == Authority.ADMIN) {
            return taskRepository.findByIdAsDTO(id)
                    .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));
        }
        return taskRepository.findByIdIfOwnerAsDTO(id, loggedUser.getId())
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));
    }

    public List<TaskDetailsDTO> findAll(Optional<Long> userId) {
        User loggedUser = userService.getLoggedUser();
        Long id = loggedUser.getId();

        if (userId.isPresent()) {
            if (loggedUser.getAuthority() == Authority.ADMIN) {
                id = userId.get();
            } else {
                throw new ForbiddenException();
            }
        }

        return  taskRepository.findAllByUserIdAsDTO(id);
    }
}
