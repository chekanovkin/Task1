import entities.Department;
import utils.FileUtil;
import utils.VariantUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Department> departments = FileUtil.readFromFile(args[0]);

        if (departments != null) {
            FileUtil.writeToFile(args[1], VariantUtil.checkVariants(departments));
        }
    }
}
