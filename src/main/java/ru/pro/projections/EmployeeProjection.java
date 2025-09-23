package ru.pro.projections;

public interface EmployeeProjection {
    String getFirstName();
    String getLastName();
    String getPosition();
    DepartmentInfo getDepartmentName();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    interface DepartmentInfo {
        String getName();
    }
}
