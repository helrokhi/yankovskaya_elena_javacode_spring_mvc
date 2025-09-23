package ru.pro.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EmployeeProjection {
    @JsonIgnore
    String getFirstName();

    @JsonIgnore
    String getLastName();

    String getPosition();

    DepartmentInfo getDepartment();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    interface DepartmentInfo {
        String getName();
    }
}
