package entities;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Department {

    private String departmentName;
    private Set<Employee> employees = new HashSet<>();

    public Department(String name) {
        this.departmentName = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
}
