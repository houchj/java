import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public static void test_StreamsMatch() {

    }

    public static void main(String[] args) {

        test_StreamOf();

        test_ListStream();

        test_StreamGenerate();

        test_CollectStreamToList();

        test_CollectStreamToArray();

        test_StreamOperations();
    }


}
