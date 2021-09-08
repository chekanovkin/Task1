import entities.Department;
import utils.FileUtil;

import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String, Department> departments = FileUtil.readFromFile(args[0]);

            if (!departments.isEmpty()) {
                FileUtil.checkVariants(args[1], new ArrayList<>(departments.values()));
            }
        } catch (IndexOutOfBoundsException ex) {
            if (args.length < 2) {
                System.out.println("Не хватает аргументов командной строки (найдено " + args.length + " вместо 2)");
            } else {
                System.out.println("Слишком много аргументов командной строки (найдено " + args.length + " вместо 2)");
            }
        }
    }
}
