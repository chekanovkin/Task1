package utils;

import entities.Department;
import entities.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VariantUtil {

    public static BigDecimal getAvgSalary(Set<Employee> employees) {
        BigDecimal averageSalary = getSumSalary(employees);
        try {
            averageSalary = averageSalary.divide(new BigDecimal(employees.size()), 2, RoundingMode.HALF_UP);
        } catch (ArithmeticException ae) {
            return new BigDecimal(0);
        }
        return averageSalary;
    }

    private static BigDecimal getSumSalary(Set<Employee> employees) {
        BigDecimal sumSalary = new BigDecimal(0);
        for (Employee person : employees) {
            sumSalary = sumSalary.add(person.getSalary());
        }
        return sumSalary;
    }

    //Реализация рекурсивного поиска через списки
    /*protected static List<List<Employee>> getVariants(List<Employee> initialList, int counter, List<Employee> list, BigDecimal avgSalary) {
        List<List<Employee>> variants = new ArrayList<>();
        for (int i = counter; i < initialList.size(); i++) {
            List<Employee> variant = new ArrayList<>(list);
            variant.add(initialList.get(i));
            if (getAvgSalary(variant).compareTo(avgSalary) < 0) {
                variants.add(variant);
            }
            variants.addAll(getVariants(initialList, i + 1, variant, avgSalary));
        }
        return variants;
    }*/

    protected static Set<Set<Employee>> powerSet(Set<Employee> initial) {
        Set<Set<Employee>> sets = new HashSet<>();
        if (initial.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<Employee> list = new ArrayList<>(initial);
        Employee head = list.get(0);
        Set<Employee> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<Employee> set : powerSet(rest)) {
            Set<Employee> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    protected static String addVariant(Department from, Department to, Set<Employee> variant) {
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

        BigDecimal sumSalaryFrom = getSumSalary(from.getEmployees()).subtract(getSumSalary(variant));
        BigDecimal sumSalaryTo = getSumSalary(to.getEmployees()).add(getSumSalary(variant));

        output.append(from.getDepartmentName())
                .append(" - ")
                .append(sumSalaryFrom.divide(new BigDecimal(from.getEmployees().size() - variant.size()), 2, RoundingMode.HALF_UP))
                .append("\n")
                .append(to.getDepartmentName())
                .append(" - ")
                .append(sumSalaryTo.divide(new BigDecimal(to.getEmployees().size() + variant.size()), 2, RoundingMode.HALF_UP))
                .append("\n");

        return output.toString();
    }
}
