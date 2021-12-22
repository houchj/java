import java.util.Optional;

public class OptionalTest{


    public static Optional<String> test_optional() {
        Optional<String> optional = Optional.ofNullable("a");
        return optional;
    }

    public static void main(String[] args) {
        System.out.println(test_optional().isPresent());
        System.out.println(test_optional().isEmpty());
        System.out.println(test_optional().get());

    }
}
