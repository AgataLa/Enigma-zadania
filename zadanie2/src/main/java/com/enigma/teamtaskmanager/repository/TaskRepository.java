package com.enigma.teamtaskmanager.repository;

import com.enigma.teamtaskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByAssignedUsersId(Long userId);
}
