import encoder.SuperEncoder;
import encoder.SuperEncoderImplements;
import exception.CircularReference;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IOException, InstantiationException, NoSuchFieldException, CircularReference, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        SuperEncoder se = new SuperEncoderImplements();
        Student student = new Student(1, "Vasay", new BigDecimal(new Random().nextInt(10000)));

        long[] number = {8, 9, 8, 7, 3, 5, 5, 0, 5, 6, 0};
        Set<Student> set = new HashSet<>();
        set.add(student);

        SuperStudent superStudent = new SuperStudent(number, set);

        byte[] studentSerialize = se.serialize(student);
        byte[] superStudentSerialize = se.serialize(superStudent);

        Object studentDeserialize = (Student) se.deserialize(studentSerialize);
        Object superStudentDeserialize = ((SuperStudent) se.deserialize(superStudentSerialize));

        System.out.println(((Student) studentDeserialize).getId() + " " +  ((Student) studentDeserialize).getName() + " " + ((Student) studentDeserialize).getMoney());
        System.out.println(
                Arrays.toString(((SuperStudent) superStudentDeserialize).getPhoneNumber()) + " " +
                "" +  ((SuperStudent) superStudentDeserialize).getStudents());
    }
}
