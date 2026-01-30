package com.samu.todoapi.repository;

import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, ListCrudRepository<User, Long> {
    @Query("SELECT new com.samu.todoapi.dto.UserDetailsDTO(u.id, u.name, u.email, u.role) FROM User u")
    List<UserDetailsDTO> findAllUsersAsDTO();

    @Query("SELECT new com.samu.todoapi.dto.UserDetailsDTO(u.id, u.name, u.email, u.role) FROM User u WHERE u.id = :id")
    Optional<UserDetailsDTO> findByIdAsDTO(Long id);
}
