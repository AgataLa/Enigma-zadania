package com.enigma.teamtaskmanager.repository.specification;

import com.enigma.teamtaskmanager.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> filterByFirstname(final String firstname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
    }

    public static Specification<User> filterByLastname(final String lastname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
    }

    public static Specification<User> filterByEmail(final String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }
}
