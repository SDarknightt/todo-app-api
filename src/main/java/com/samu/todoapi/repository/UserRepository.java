package com.samu.todoapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.samu.todoapi.dto.UserDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.samu.todoapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, ListCrudRepository<User, UUID> {
    @Query("SELECT new com.samu.todoapi.dto.UserDetailsDTO(u.id, u.name, u.email, u.authority) FROM User u")
    List<UserDetailsDTO> findAllUsersAsDTO();

    @Query("SELECT new com.samu.todoapi.dto.UserDetailsDTO(u.id, u.name, u.email, u.authority) FROM User u WHERE u.id = :id")
    Optional<UserDetailsDTO> findByIdAsDTO(UUID id);

    Optional<User> findByEmailIgnoreCase(String username);
}
