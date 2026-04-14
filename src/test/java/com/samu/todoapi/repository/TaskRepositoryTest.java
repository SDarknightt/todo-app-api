package com.samu.todoapi.repository;

import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.entity.Authority;
import com.samu.todoapi.entity.Status;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest // Adiciona @Transactional e @Rollback em cada teste e configura por default H2
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Impede a substituição do banco PostgreSQL pelo H2
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Container
    private static final PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:16")
            .withDatabaseName("todo_app_test")
            .withUsername("postgres")
            .withPassword("1234");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    private User createUser(String name, String email) {
        return userRepository.save(User.builder()
            .name(name)
            .email(email)
            .password("1234")
            .authority(Authority.USER)
            .build());
    }

    @Test
    void shouldReturnTaskDTOWhenTaskExists() {
        User user = createUser("User", "user@email.com");

        Task newTask = Task.builder()
                .title("Task Test")
                .description("Task test description")
                .status(Status.DONE)
                .owner(user)
                .build();

        Task savedTask = taskRepository.save(newTask);

        TaskDetailsDTO taskDTO = taskRepository.findByIdAsDTO(savedTask.getId())
                .orElseThrow();

        assertEquals(savedTask.getId(), taskDTO.getId());
        assertEquals("Task Test", taskDTO.getTitle());
        assertEquals("Task test description", taskDTO.getDescription());
        assertEquals(Status.DONE, taskDTO.getStatus());
        assertEquals(user.getId(), taskDTO.getOwnerId());
    }

    @Test
    void shouldReturnTaskDTOWithItsOwner() {
        User user = createUser("User", "user@email.com");

        Task newTask = Task.builder()
                .title("Task Test")
                .description("Task test description")
                .status(Status.DONE)
                .owner(user)
                .build();

        Task savedTask = taskRepository.save(newTask);

        Task task = taskRepository.findByIdWithOwner(savedTask.getId())
                .orElseThrow();

        assertEquals(savedTask.getId(), task.getId());
        assertEquals(user.getId(), task.getOwner().getId());
    }

    @Test
    void shouldNotReturnTaskDTOIfUserIsNotOwner() {
        User user1 = createUser("User", "user@email.com");
        User user2 = createUser("User 2", "user2@email.com");

        Task savedTask = taskRepository.save(Task.builder()
            .title("Task Test")
            .status(Status.DONE)
            .owner(user1)
            .build());

        Optional<TaskDetailsDTO> task = taskRepository.findByIdIfOwnerAsDTO(savedTask.getId(), user2.getId());

        assertTrue(task.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenTaskNotExists() {
        Optional<TaskDetailsDTO> taskDTO = taskRepository.findByIdAsDTO(UUID.randomUUID());
        assertTrue(taskDTO.isEmpty());
    }

    @Test
    void shouldReturnAllTasksFromUser() {
        User user = createUser("User", "user@email.com");

        taskRepository.save(Task.builder()
                .title("Task 1")
                .status(Status.DONE)
                .owner(user)
                .build());

        taskRepository.save(Task.builder()
                .title("Task 2")
                .status(Status.DONE)
                .owner(user)
                .build());

        List<TaskDetailsDTO> tasks = taskRepository.findAllByUserIdAsDTO(user.getId());

        assertEquals(2, tasks.size());
    }
}
