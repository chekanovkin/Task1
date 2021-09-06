package utils;

import com.google.common.collect.Sets;
import entities.Department;
import entities.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VariantUtil {

    private static List<String> allVariants = new ArrayList<>();

    private static BigDecimal getAvgSalary(Set<Employee> employeeSet) {
        BigDecimal averageSalary = new BigDecimal(0);
        for (Employee person : employeeSet) {
            averageSalary = averageSalary.add(person.getSalary());
        }
        averageSalary = averageSalary.divide(new BigDecimal(employeeSet.size()), 2, RoundingMode.HALF_UP);
        return averageSalary;
    }

    public static List<String> checkVariants(List<Department> departments){
        for (Department departmentFrom : departments) {
            Set<Set<Employee>> powerSet = Sets.powerSet(departmentFrom.getEmployees()).stream()
                .filter(set -> !set.isEmpty())
                .filter(set -> getAvgSalary(set).compareTo(getAvgSalary(departmentFrom.getEmployees())) < 0)
                .collect(Collectors.toSet());
            for (Department departmentTo : departments) {
                if (departmentFrom.getDepartmentName().equals(departmentTo.getDepartmentName()))
                    continue;
                for (Set<Employee> set : powerSet){
                    if (getAvgSalary(set).compareTo(getAvgSalary(departmentTo.getEmployees())) > 0)
                        addVariant(departmentFrom, departmentTo, set);
                }
            }
        }
        return allVariants;
    }

    private static void addVariant(Department from, Department to, Set<Employee> variant){
        StringBuilder output = new StringBuilder("Список переведенных сотрудников: \n");
        for (Employee employee : variant) {
            output.append(employee.getFullName())
                .append(" с зарплатой ")
                .append(employee.getSalary())
                .append("\n");
        }

        output.append("Из отдела: ")
            .append(from.getDepartmentName())
            .append("\nВ отдел: ")
            .append(to.getDepartmentName())
            .append("\nСредняя зарплата до перевода\n")
            .append(from.getDepartmentName())
            .append(" - ")
            .append(getAvgSalary(from.getEmployees()))
            .append("\n")
            .append(to.getDepartmentName())
            .append(" - ")
            .append(getAvgSalary(to.getEmployees()))
            .append("\nСредняя зарплата после перевода\n");

        Set<Employee> employeesFrom = new HashSet<>(from.getEmployees());
        Set<Employee> employeesTo = new HashSet<>(to.getEmployees());
        employeesTo.addAll(variant);
        employeesFrom.removeAll(variant);

        output.append(from.getDepartmentName())
            .append(" - ")
            .append(getAvgSalary(employeesFrom))
            .append("\n")
            .append(to.getDepartmentName())
            .append(" - ")
            .append(getAvgSalary(employeesTo))
            .append("\n");
        allVariants.add(output.toString());
    }
}
