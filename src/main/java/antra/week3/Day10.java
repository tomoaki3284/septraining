package antra.week3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Day10 {
	
	/**
	 * HashMap:
	 *
	 * key should be immutable
	 *
	 */
	public static void main(String[] args) {
	
	}
}
/**
 * fail-fast:
 * ConcurrentModificationException
 * For Example - It is not permissible for a thread to modify a Collection when some other thread is iterating over it.
 */

class FailFastTest {
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		
		// will throw ConcurrentModificationException
		for (int e : list) {
			list.add(2);
		}
	}
}

/**
 * ConcurrentHashMap
 * todo: Look into how ConcurrentHashMap work
 *
 * 	put: synchronized + CAS
 * 	get: volatile
 * 		1. Visibility
 * 		2. re-ordering
 * 		3. happen - before
 */
class ConHashMapTest {
	
	// if no volatile, t1 thread is in infinite loop. visibility problem
	private static volatile boolean flag;
	
	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(() -> {
			while (!flag) {
			
			}
			System.out.println("break while loop");
		});
		
		t1.start();
		Thread.sleep(1000);
		
		Thread t2 = new Thread(() -> {
			flag = true;
			System.out.println("changed flag from F to T");
		});
		t2.start();
		
	}
}

/**
 * Stream API
 *
 * list.parallelStream() provide better performance
 */
class StreamAPITest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(2);
		
		Map<Integer, Long> freq = list.stream()
			.collect(
				HashMap::new, // supplier
				(map, val) -> map.put(val, map.getOrDefault(val, 0l)+1), // accumulator
				(finalMapResult, tmpMapResult) -> {} // combiner
			);
		
		System.out.println(freq);
		
	}
}

/**
 * return 2nd highest freq element
 *
 */
class AlgorithmTest {
	
	public static void main(String[] args) {
	
	}
	
	/**
	 * TDD:
	 * if input is null => exception
	 * if input is empty / input.size() <= 1 => null
	 * if freq is same => return number decedent
	 *
	 * PriorityQueue / 2 variable
	 *
	 * @param list
	 * @return
	 */
	static int secondHighestFreq(List<Integer> list) {
		if (list == null || list.size() == 1) {
			throw new IllegalArgumentException("list is null or size <= 1");
		}
		
		Map<Integer,Integer> freq = list.stream()
			.collect(
				HashMap::new,
				(map, val) -> map.put(val, map.getOrDefault(val, 0)+1),
				null
			);
		
		PriorityQueue<Integer> heap = new PriorityQueue<>((n1,n2) -> {
			Integer v1 = freq.get(n1);
			Integer v2 = freq.get(n2);
			
			if (v1.equals(v2)) {
				return n1 - n2;
			}
			
			return v1 - v2;
		});
		
		freq.keySet()
			.stream()
			.map(key -> {
				heap.add(key);
				if (heap.size() > 2) {
					heap.poll();
				}
				return heap;
			});
		
		return heap.poll();
		
		// since it is 2nd highest, the time complexity is : O(N + Nlog2)
	}
	
	/**
	 * homework:
	 * 		upload to git, send to Renqing Yang before tomorrow 10am CDT
	 */
}
