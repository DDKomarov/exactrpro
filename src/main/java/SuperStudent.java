import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class SuperStudent implements Serializable {
    private long[] phoneNumber;
    private Set<Student> students;
}
