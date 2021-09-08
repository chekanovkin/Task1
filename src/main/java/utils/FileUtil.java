package utils;

import entities.Department;
import entities.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static utils.VariantUtil.*;
import static utils.VariantUtil.getAvgSalary;

public class FileUtil {

    private static Employee createEmployee(String[] data) {
        BigDecimal salary = new BigDecimal(data[data.length-1].trim());
        if (salary.compareTo(BigDecimal.ZERO) >= 0) {
            return new Employee(data[0].trim(), salary.setScale(2, RoundingMode.HALF_UP));
        } else {
            throw new NumberFormatException();
        }
    }

    public static Map<String, Department> readFromFile(String file) {
        String[] data;
        Map<String, Department> departmentMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int rowNum = 1;
            while (line != null) {
                try {
                    data = line.split(";");
                    if (data.length != 3) {
                        throw new IndexOutOfBoundsException();
                    }
                    Employee employee = createEmployee(data);
                    String departmentName = data[1].trim();
                    departmentMap.putIfAbsent(departmentName, new Department(departmentName));
                    if (employee.getSalary() != null && !employee.getFullName().isEmpty()) {
                        departmentMap.get(departmentName).addEmployee(employee);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("Количество колонок отлично от 3 в строке №" + rowNum);
                } catch (NumberFormatException e) {
                    System.out.println("Не верный формат зарплаты в строке №" + rowNum);
                }
                line = br.readLine();
                rowNum++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл " + file + " не найден");
            return Collections.emptyMap();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных из файла " + file);
            return Collections.emptyMap();
        }
        printInfo(new ArrayList<>(departmentMap.values()));
        return departmentMap;
    }

    public static void checkVariants(String file, List<Department> departments) {
        for (Department departmentFrom : departments) {
            List<List<Employee>> allVariantsForOne = getVariants(departmentFrom.getEmployees(), 0, new ArrayList<>(), getAvgSalary(departmentFrom.getEmployees()));
            for (Department departmentTo : departments) {
                if (departmentFrom.equals(departmentTo)) {
                    continue;
                }
                for (List<Employee> variant : allVariantsForOne){
                    if (getAvgSalary(variant).compareTo(getAvgSalary(departmentTo.getEmployees())) > 0) {
                        writeToFile(file, addVariant(departmentFrom, departmentTo, variant));
                    }
                }
            }
        }
    }

    public static void writeToFile(String file, String variant) {
        try(FileWriter fw = new FileWriter(file, true)) {
            fw.write("++++++++++++++++++++++++++++++++++++++++++++\n" + variant);
            fw.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка в имени выходного файла");
        }
    }

    private static void printInfo(List<Department> departments) {
        System.out.println("\nИнформация об отделах\n");
        StringBuilder output = new StringBuilder();
        for (Department department : departments) {
            output.append("Отдел ")
                    .append(department.getDepartmentName())
                    .append("\nСредняя зарплата: ")
                    .append(getAvgSalary(department.getEmployees()))
                    .append("\nСписок сотрудников:\n");
            for (Employee employee : department.getEmployees()) {
                output.append(employee.getFullName())
                        .append(" ")
                        .append(employee.getSalary())
                        .append("\n");
            }
            output.append("\n");
        }
        System.out.println(output);
    }
}
