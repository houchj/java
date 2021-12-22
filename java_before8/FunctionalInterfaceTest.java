import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Any interface with a SAM(Single Abstract Method) is a functional interface,
 * Note that Java 8's default methods are not abstract and do not count;
 * a functional interface may still have multiple default methods.
 */
public class FunctionalInterfaceTest {

    /**
     * The most simple and general case of a lambda is a functional interface with a method
     * that receives one value and returns another.
     * This function of a single argument is represented by the Function interface,
     * which is parameterized by the types of its argument and a return value:
     * <p>
     * public interface Function<T, R> { … }
     */
    public static void test_Functions() {
        Map<String, Integer> nameMap = new HashMap<>();
        //nameMap.put("John", 12);
        Integer value = nameMap.computeIfAbsent("John", s -> s.length());

        /**
         * This allows us to cast an instance method length reference to a Function interface:
         */
        Integer value2 = nameMap.computeIfAbsent("Alex2", String::length);
        System.out.println(value);
        System.out.println(value2);

        /**
         * The Function interface also has a default compose method that allows us
         *  to combine several functions into one and execute them sequentially:
         */
        Function<Integer, String> intToString = Object::toString;
        Function<String, String> quote = s -> "'" + s + "'";
        Function<Integer, String> quoteIntToString = quote.compose(intToString);
        System.out.println("'5'".equals(quoteIntToString.apply(5)));
    }

    /**
     * Since a primitive type can’t be a generic type argument, there are versions of the Function interface for the most used primitive types double, int, long, and their combinations in argument and return types:
     * <p>
     * IntFunction, LongFunction, DoubleFunction: arguments are of specified type, return type is parameterized
     * ToIntFunction, ToLongFunction, ToDoubleFunction: return type is of specified type, arguments are parameterized
     * DoubleToIntFunction, DoubleToLongFunction, IntToDoubleFunction, IntToLongFunction, LongToIntFunction, LongToDoubleFunction: having both argument and return type defined as primitive types, as specified by their names
     */
    public static byte[] transformArray(short[] array, ShortToByteFunction function) {
        byte[] transformedArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            transformedArray[i] = function.applyAsByte(array[i]);
        }
        return transformedArray;
    }
    public static void test_PrimitiveFunctions() {
        short[] array = {(short) 1, (short) 2, (short) 3};
        byte[] transformedArray = transformArray(array, s -> (byte) (s * 2));
        System.out.println(transformedArray[0]);
    }

    /**
     * o define lambdas with two arguments, we have to use additional interfaces that contain “Bi” keyword in their
     *  names: BiFunction, ToDoubleBiFunction, ToIntBiFunction, and ToLongBiFunction.
     */
    public static void test_TwoArityFunction() {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((name, oldValue) -> {
            if ("Freddy".equals(name)) {
                return oldValue + 15000;
            } else return oldValue;
        });
        System.out.println(salaries.get("Freddy"));
    }

    /**
     * The Supplier functional interface is yet another Function specialization that does not take any arguments.
     * We typically use it for lazy generation of values.
     */
    public static double squareLazy(Supplier<Double> lazyValue) {
        return Math.pow(lazyValue.get(), 2);
    }
    public static void test_Suppliers() {
        Supplier<Double> lazyValue = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 9d;
        };
        Double valueSquared = squareLazy(lazyValue);
        System.out.println(valueSquared);

        int[] fibs = {0, 1};
        Stream<Integer> fibonacci = Stream.generate(() -> {
            int result = fibs[1];
            int fib3 = fibs[0] + fibs[1];
            fibs[0] = fibs[1];
            fibs[1] = fib3;
            return result;
        });
        fibonacci.limit(10).forEach(System.out::println);
    }

    /**
     * As opposed to the Supplier, the Consumer accepts a generified argument and returns nothing.
     * It is a function that is representing side effects.
     */
    public static void test_Consumers() {
        Map<String, Integer> ages = new HashMap<>();
        ages.put("John", 25);
        ages.put("Freddy", 24);
        ages.put("Samuel", 30);

        ages.forEach((name, age) ->
                System.out.println(name + " is " + age + " years old"));
    }

    /**
     * In mathematical logic, a predicate is a function
     *  that receives a value and returns a boolean value.
     */
    public static void test_Predicates() {
        List<String> names = Arrays.asList("Angela", "Aaron", "Bob", "Claire", "David");
        List<String> nameWithA = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(nameWithA);
    }

    /**
     * Operator interfaces are special cases of a function
     * that receive and return the same value type.
     */
    public static void test_Operators() {
        List<String> names = Arrays.asList("bob", "josh", "megan");
        names.replaceAll(name -> name.toUpperCase());
        // or
        names.replaceAll(String::toUpperCase);

        System.out.println(names);

        /**
         * One of the most interesting
         *  use cases of a BinaryOperator is a reduction operation.
         */
        List<Integer> values = Arrays.asList(3, 5, 8, 9, 12);
        int sum = values.stream()
                .reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(sum);
    }

    public static void main(String[] args) {
        test_Functions();

        test_PrimitiveFunctions();

        test_TwoArityFunction();

        test_Suppliers();

        test_Consumers();

        test_Predicates();

        test_Operators();
    }
}

/**
 * As an example, there is no out-of-the-box functional interface for a function that takes a short and returns a byte, but nothing stops us from writing our own:
 */
@FunctionalInterface
interface ShortToByteFunction {
    byte applyAsByte(short s);
}
