import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.*;

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
        // create stream of dates
        List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());

        LocalDate max = dates.stream()
                .max(Comparator.comparing(LocalDate::toEpochDay))
                .get();
        LocalDate min = dates.stream()
                .min(Comparator.comparing(LocalDate::toEpochDay))
                .get();

        System.out.println("max date is " + max);
        System.out.println("min date is " + min);

        // Find min max numbers
        Integer maxNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .max(Comparator.comparing(Integer::valueOf))
                .get();
        Integer minNumber = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .min(Comparator.comparing(Integer::valueOf))
                .get();
        System.out.println("max number is " + maxNumber);
        System.out.println("min number is " + minNumber);

        // Find min max object by key
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lokesh", 36));
        employees.add(new Employee(2, "Alex", 46));
        employees.add(new Employee(3, "Brian", 52));

        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        Employee minObj = employees.stream().min(comparator).get();
        Employee maxObj = employees.stream().max(comparator).get();
        System.out.println("min object is " + minObj);
        System.out.println("max object is " + maxObj);
    }

    public static void test_streamOfRandom() {
        Random random = new Random();

        random.ints(5).sorted().forEach(System.out::println);
        random.doubles(5, 0, 0.5)
                .sorted().forEach(System.out::println);
        List<Long> longs = random.longs(5).boxed().collect(Collectors.toList());
        System.out.println(longs);
    }

    public static void test_streamCount() {
        long count = Stream.of("how", "to", "do", "in", "java")
                .count();
        System.out.printf("there are %d words in the stream \n", count);

        count = Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .collect(Collectors.counting());
        System.out.printf("there are %d integers in the stream \n", count);
    }

    public static void test_getLastElement() {
        // by reduce
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream();
        Integer last = stream.reduce((first, second) -> second)
                .orElse(-1);
        System.out.println(last);

        stream = Stream.empty();
        last = stream.reduce((first, second) -> second)
                .orElse(-1);
        System.out.println(last);

        //Streams.findLast() examples
    }

    public static void test_removeDuplicates() {
        List<String> list = Arrays.asList("A", "B", "C", "D", "A", "B", "C");
        List<String> distinctItems = list.stream().distinct()
                .collect(Collectors.toList());
        System.out.println(distinctItems);
        //can also distinct POJOs

        //use Collectors.toSet()
        ArrayList<Integer> numbersList = new ArrayList<>(
                Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));
        Set<Integer> set = numbersList.stream().collect(Collectors.toSet());

        System.out.println(set);

        //use Collectors.toMap()
        Map<Integer, Long> elementCountMap = numbersList.stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
        System.out.println(elementCountMap);
    }

    public static boolean isPrime(int i) {
        IntPredicate isDivisible = index -> i % index == 0;
        return i > 1 && IntStream.range(2, i).noneMatch(isDivisible);
    }

    public static void test_IntPredicate() {
        IntPredicate isOdd = arg -> arg % 2 == 1;
        IntStream stream = IntStream.range(1, 20);

        List<Integer> oddPrimes = stream.filter(isOdd.and(StreamTest::isPrime))
                .boxed()
                .collect(Collectors.toList());
        System.out.println(oddPrimes);
    }

    public static void test_readFileLineByLine() {
        Path filePath = Paths.get("./java_before8", "data.txt");
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test_StreamIfElse() {
        // Apply if-else logic in streams
        ArrayList<Integer> numberList =
                new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Consumer<Integer> action = i -> {
            if (i % 2 == 0) {
                System.out.println("Even number " + i);
            } else {
                System.out.println("Odd Number " + i);
            }
        };
        numberList.stream().forEach(action);

        Predicate<Integer> isEven = i -> i % 2 == 0;
        numberList.stream().filter(isEven).forEach(System.out::println);
    }

    public static void test_reuseStream() {
        // can we reuse stream? NO!!!, just create it multi times
        List<Integer> tokens = Arrays.asList(1, 2, 3, 4, 5);

        //first use
        Optional<Integer> result = tokens.stream().max(Integer::compareTo);
        System.out.println(result.get());

        //second use
        result = tokens.stream().min(Integer::compareTo);
        System.out.println(result.get());

        //third use
        long count = tokens.stream().count();
        System.out.println(count);
    }

    public static void test_iteratorToStream() {
        //java8
        Iterator<String> iterator = Arrays.asList("a", "b", "c").listIterator();
        Spliterator<String> spliterator = Spliterators
                .spliteratorUnknownSize(iterator, Spliterator.ORDERED);

        Stream<String> stream = StreamSupport.stream(spliterator, false);
        stream.forEach(System.out::println);

        //java9
        Stream.generate(() -> null)
                .takeWhile(x -> iterator.hasNext())
                .map(n -> iterator.next())
                .forEach(System.out::println);
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

        test_findMinMax();

        test_streamOfRandom();

        test_streamCount();

        test_getLastElement();

        test_removeDuplicates();

        test_IntPredicate();

        test_readFileLineByLine();

        test_StreamIfElse();

        test_reuseStream();

        test_iteratorToStream();
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