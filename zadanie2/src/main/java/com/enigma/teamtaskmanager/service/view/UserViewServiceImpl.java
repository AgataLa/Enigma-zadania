package com.enigma.teamtaskmanager.service.view;

import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.repository.specification.UserSpecification;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserViewServiceImpl implements UserViewService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserInfoDTO getUserById(final Long userId) {
        final User foundUser = findUserById(userId);
        return userMapper.mapToUserInfoDTO(foundUser);
    }

    @Override
    public List<UserInfoDTO> getFilteredUsers(final String firstname, final String lastname, final String email) {
        Specification<User> specification = Specification.where(null);

        if (firstname != null && !firstname.isEmpty()) {
            specification = specification.and(UserSpecification.filterByFirstname(firstname));
        }
        if (lastname != null && !lastname.isEmpty()) {
            specification = specification.and(UserSpecification.filterByLastname(lastname));
        }
        if (email != null && !email.isEmpty()) {
            specification = specification.and(UserSpecification.filterByEmail(email));
        }

        final List<User> filteredUsers = userRepository.findAll(specification);

        return filteredUsers.stream()
                .map(userMapper::mapToUserInfoDTO)
                .collect(Collectors.toList());
    }

    private User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }
}
