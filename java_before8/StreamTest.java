import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {


    public static void test_StreamOf() {
        Stream<Integer> stream = Stream.of(1,2,3,4,5,6,7,8,9);
        stream.forEach(p -> System.out.println(p));

        stream = Stream.of( new Integer[]{1,2,3,4,5,6,7,8,9} );
        stream.forEach(p -> System.out.println(p));
    }

    public static void test_ListStream() {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i< 10; i++){
            list.add(i);
        }

        Stream<Integer> stream = list.stream();
        stream.forEach(p -> System.out.println(p));
    }

    public static void test_StreamGenerate() {
        IntStream stream = "123456_abcdefg".chars();
        stream.forEach(p -> System.out.println(p));

        Stream<String> stream2 = Stream.of("A#B#C".split("\\#"));
        stream2.forEach(p-> System.out.println(p));
    }

    public static void test_CollectStreamToList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Stream<Integer> stream = list.stream();
        List evenNumberList = stream.filter(i -> i%2 == 0)
                .collect(Collectors.toList());
        System.out.println(evenNumberList);
    }

    public static void test_CollectStreamToArray() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Stream<Integer> stream = list.stream();
        Integer[] evenNumberArray = stream.filter(i -> i%2 == 0).toArray(Integer[]::new);
        System.out.println(evenNumberArray);
    }

    public static void test_StreamOperations() {
        List<String> memberNames = new ArrayList<>();
        memberNames.add("Amitabh");
        memberNames.add("Shekhar");
        memberNames.add("Aman");
        memberNames.add("Rahul");
        memberNames.add("Shahrukh");
        memberNames.add("Salman");
        memberNames.add("Yana");
        memberNames.add("Lokesh");

        memberNames.stream().filter(s -> s.startsWith("A"))
                .forEach(System.out::println);

        memberNames.stream().filter(s -> s.startsWith("A"))
                .map(String::toUpperCase)
                .forEach(System.out::println);

        memberNames.stream().sorted()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // terminal operations below
        List<String> namesInUppercase = memberNames.stream().sorted().map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(namesInUppercase);

        // test stream match
        boolean matchResult = memberNames.stream().anyMatch(s -> s.startsWith("A"));
        System.out.println(matchResult);

        matchResult = memberNames.stream().allMatch(s -> s.startsWith("A"));
        System.out.println(matchResult);

        matchResult = memberNames.stream().noneMatch(s -> s.startsWith("A"));
        System.out.println(matchResult);

        // test stream count
        long count = memberNames.stream().filter(s -> s.startsWith("A")).count();
        System.out.println(count);

        // test stream reduce
        Optional<String> reduced = memberNames.stream()
                .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
    }

    public static void test_mapAndFlatmap() {
        // map
        List<String> list11 = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> list22 = list11.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        System.out.println(list22);

        //flatMap
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        List<Integer> list3 = Arrays.asList(7, 8, 9);
        List<List<Integer>> listOfLists = Arrays.asList(list1, list2, list3);
        List<Integer> listOfAllIntegers = listOfLists.stream()
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        System.out.println(listOfAllIntegers);
    }

    /**
     * IntStream.iterate() generate an infinite stream
     * Stream.generate() also generates an infinite stream
     */
    public static void test_infiniteStreams() {
        List<Integer> ints = IntStream.iterate(0, i -> i + 2)
                .mapToObj(Integer::valueOf)
                .limit(100)
                .collect(Collectors.toList());
        System.out.println(ints);

        List<Integer> randomNumbers =
                Stream.generate(() -> (new Random()).nextInt(100))
                .limit(100)
                .collect(Collectors.toList());
        System.out.println(randomNumbers);

        List<Employee> employees = Stream.generate(Employee::create)
                .limit(10)
                .collect(Collectors.toList());
        System.out.println(employees);
    }

    public static void test_findMinMax() {
        // Find max min date from list of dates
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1)
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    public static void main(String[] args) {

        test_StreamOf();

        test_ListStream();

        test_StreamGenerate();

        test_CollectStreamToList();

        test_CollectStreamToArray();

        test_StreamOperations();

        test_mapAndFlatmap();

        test_infiniteStreams();
    }


}

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Random r = new Random(Integer.MAX_VALUE);

    private long id;
    private String name;
    private double salary;

    public static Employee create() {
        Employee emp = new Employee(r.nextInt(), "", 0.0);
        return emp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee(long id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=]" + id + ", name=" + name + ", salary=" + salary + "]";
    }
}