package antra.week1.day1;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day5 {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		threadPool.submit(() -> {
			System.out.println("I am runnable");
		});
		
		Callable c = new Callable() {
			@Override
			public Object call() throws Exception {
				return "I am callable";
			}
		};
		Future<String> future = threadPool.submit(c);
		System.out.println(future.get());
	}
}
/**
 * Thread pool
 *  - ThreadPoolExecutor
 *  	- SingleThreadPool
 *  	- CacheThreadPool
 *  	- FixedThreadPool
 *  - ForkJoinPool
 *  	- ForkJoinPool
 *  - ScheduleThreadPoolExecutor
 *  	- ScheduleThreadPool
 *
 *  Runnable vs Callable (Quite common interview question)
 *  Runnable:
 *  return type void
 *  cannot throw checked exception
 *
 *  Callable:
 *  return type Object
 *
 *  Interface: we use them to wrap our thread implementation, they provide us the method to work with the thread pool
 *  Executor(execute(runnable)), ExecutorService(submit(runnable, callable)), ScheduleExecutorService(schedule)
 *
 *  ThreadPool Implementations:
 *  	Executors (provide multiple threadPool implementations)
 *  	ThreadPoolExecutor()
 *  	ScheduleThreadPoolExecutor
 *  	ForkJoinPool
 */

/**
 * Java 8 new features:
 * 		- Functional Interface
 * 		- Lambda Expression
 * 		- Stream API:
 * 			- no for loop
 * 			- chain operation
 * 		- Optional<>
 */

/**
 * Functional Interface:
 * 		Comparator, etc
 *
 * 	only contains one abstract method
 */
class TestStream {
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4);
		List<Integer> res = list
			.stream()
			.map(e -> e*2)
			.filter(integer -> integer > 5)
			.collect(Collectors.toList());
		
		System.out.println(res);
	}
}
