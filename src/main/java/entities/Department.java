package entities;

import java.util.*;

public class Department {

    private String departmentName;
    private Set<Employee> employees = new HashSet<>();

    public Department(String name) {
        this.departmentName = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return departmentName.equals(that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName);
    }
}
