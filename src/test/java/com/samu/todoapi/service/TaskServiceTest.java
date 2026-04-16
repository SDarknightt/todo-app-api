package com.samu.todoapi.service;

import com.samu.todoapi.dto.TaskCreateRequestDTO;
import com.samu.todoapi.dto.TaskCreateResponseDTO;
import com.samu.todoapi.entity.Authority;
import com.samu.todoapi.entity.Status;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.mapper.TaskMapper;
import com.samu.todoapi.repository.TaskRepository;
import com.samu.todoapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Spy // Objeto é gerenciado pelo Mockito, vira mock mas com implementação real e podendo ser alterado
    private final TaskMapper taskMapper = new TaskMapper();

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskForUserAndReturnIt() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("User")
                .email("user@email.com")
                .authority(Authority.USER)
                .password("1234")
                .build();

        TaskCreateRequestDTO taskDTO = TaskCreateRequestDTO.builder()
                .title("Task Test")
                .status(Status.DONE)
                .build();

        when(userRepository.getReferenceById(user.getId()))
                .thenReturn(user);

        // thenAnswer captura o elemento durante a execução do método e permite manipular antes de retornar
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            var task = invocation.getArgument(0, Task.class);
            task.setId(UUID.randomUUID());
            return task;
        });

        TaskCreateResponseDTO taskCreatedDTO = taskService.createForUser(taskDTO, user.getId());
        assertNotNull(taskCreatedDTO.getId());
        assertEquals(taskDTO.getTitle(), taskCreatedDTO.getTitle());
    }

}
