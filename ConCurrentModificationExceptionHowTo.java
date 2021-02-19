import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class ConCurrentModificationExceptionHowTo {

	public static void removingDuringIteration() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		for (int v : ints) {
			if (v == 2) {
				int i = ints.indexOf(v);
				ints.remove(i);
				System.out.println("removed one item");
			}
		}
		System.out.println(ints);
	}

	public static void removingDuringIteration_FixByForLoop() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		for (int i = 0; i < ints.size(); i++) {
			int v = ints.get(i);
			if (v == 2) {
				ints.remove(i);
				System.out.println("removed one item");
			}
		}
		System.out.println(ints);
	}

	public static void removingDuringIteration_FixByIterator() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		Iterator<Integer> it = ints.iterator();
		while (it.hasNext()) {
			Integer i = it.next();
			if (i == 2) {
				it.remove();
				System.out.println("removed one item");
			}
		}
		System.out.println(ints);
	}

	public static void removingDuringIteration_FixByRemoveAfter() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		Iterator<Integer> it = ints.iterator();
		List<Integer> toRemove = new ArrayList<Integer>();
		while (it.hasNext()) {
			Integer v = it.next();
			if (v == 2) {
				toRemove.add(v);
			}
		}
		ints.removeAll(toRemove);
		System.out.println(ints);
	}

	public static void removingDuringIteration_FixByRemoveIf() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		ints.removeIf(i -> i == 2);
		System.out.println(ints);
	}

	public static void removingDuringIteration_FixByStream() throws InterruptedException {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		List<String> collected = ints.stream().filter(i -> i != 2).map(Object::toString).collect(toList());
		System.out.println(collected);
	}

	public static void modifyAfterSubList() {
		List<Integer> ints = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
		List<Integer> first2Ints = ints.subList(0, 2);

		first2Ints.add(2);
		System.out.println(ints + " , " + first2Ints); // this line don't

		ints.add(5);
		System.out.println(ints + " , " + first2Ints); // this line throws ConcurrentModificationException

	}

	public static void main(String[] args) throws InterruptedException {
		// removingDuringIteration1();
//		modifyAfterSubList();
		removingDuringIteration_FixByStream();
	}

}
