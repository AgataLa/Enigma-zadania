package com.enigma.teamtaskmanager.domain;

import java.util.Arrays;

public enum TaskStatus {
    TO_DO,
    IN_PROGRESS,
    TO_TEST,
    IN_TESTS,
    COMPLETED;

    public static boolean isValidStatus(final String status) {
        return Arrays.stream(TaskStatus.values()).anyMatch(taskStatus -> taskStatus.name().equals(status));
    }
}
