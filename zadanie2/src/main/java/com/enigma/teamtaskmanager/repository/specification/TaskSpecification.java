package com.enigma.teamtaskmanager.repository.specification;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.TaskStatus;
import com.enigma.teamtaskmanager.domain.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> filterByTitle(final String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Task> filterByStatus(final TaskStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> filterByRealizationDate(final LocalDateTime dateFrom, final LocalDateTime dateTo) {
        return (root, query, criteriaBuilder) -> {
            if (dateFrom != null && dateTo != null) {
                return criteriaBuilder.between(root.get("realizationDate"), dateFrom, dateTo);
            } else if (dateFrom != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("realizationDate"), dateFrom);
            } else if (dateTo != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("realizationDate"), dateTo);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Task> filterByAssignedUser(final Long assignedUserId) {
        return (root, query, criteriaBuilder) -> {
            final Join<Task, User> join = root.join("assignedUsers");
            return criteriaBuilder.equal(join.get("id"), assignedUserId);
        };
    }
}
