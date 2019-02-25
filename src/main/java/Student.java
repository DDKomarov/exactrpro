import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Student implements Serializable {
    private int id;
    private String name;
    private BigDecimal money;

}
