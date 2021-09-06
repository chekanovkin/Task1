package utils;

import entities.Department;
import entities.Employee;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    private static Employee createEmployee(String[] data){
        if (NumberUtils.isCreatable(data[data.length-1].trim())) {
            BigDecimal salary = new BigDecimal(data[data.length-1].trim());
            if (salary.compareTo(BigDecimal.ZERO) >= 0 && salary.scale() <= 2) {
                return new Employee(data[0].trim(), salary);
            }
        }
        return null;
    }

    public static List<Department> readFromFile(String file) {
        String[] data;
        Map<String, Department> departmentMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null) {
                data = line.split(";");
                if (data.length == 3) {
                    Employee employee = createEmployee(data);
                    String departmentName = data[1].trim();
                    if (!departmentMap.containsKey(departmentName)) {
                        departmentMap.put(departmentName, new Department(departmentName));
                    }
                    if (employee != null) {
                        departmentMap.get(departmentName).addEmployee(employee);
                    }
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            return null;
        } catch (IOException e){
            System.out.println("Ошибка при чтении данных из файла");
            return null;
        }
        return new ArrayList<>(departmentMap.values());
    }

    public static void writeToFile(String file, List<String> variants) {
        try(FileWriter fw = new FileWriter(file)) {
            int variantNumber = 1;
            for (String variant : variants) {
                fw.write(variantNumber + " Вариант\n" + variant);
                variantNumber++;
            }
            fw.flush();
        } catch (IOException e) {
            System.out.println("Ошибка в имени выходного файла");
        }
    }
}
