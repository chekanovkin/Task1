package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Employee {

    private String fullName;
    private BigDecimal salary;


}
