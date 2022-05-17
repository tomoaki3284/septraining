package antra.week1.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Day6 {
	
	public static void main(String[] args) {
	
	}
}

class FutureTest {
	
	public static void main(String[] args) throws Exception {
		ExecutorService tp = Executors.newFixedThreadPool(2);
		Future<Integer> ft = tp.submit(() -> {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			
			}
			return 1;
		});
		
		System.out.println("main is blocking...");
		// main thread is blocked until future is get completed
		final int res = ft.get();
		System.out.println(res);
		
		Future<Integer> ft2 = tp.submit(() -> {
			return 2*res;
		});
		System.out.println("other logic");
	}
}

/**
 * Completable Future vs Future
 * Completable Future:
 *  - fully async
 *  - chain operation
 *  -
 *
 * Future:
 *  -
 */
class CompletableTest {
	
	public static void main(String[] args) {
//		CompletableFuture<Integer> cf = CompletableFuture
//			.supplyAsync(() -> {
//				try {
//					Thread.sleep(3000);
//				} catch (Exception e) {
//
//				}
//				return 1;
//			});
//
//		System.out.println("In main");
//
//		CompletableFuture<Integer> cf2 = cf.thenApply(x -> {
//			return 2*x;
//		});
//
//		System.out.println("In main2");
//
//		// blocking method
//		System.out.println(cf2.join());
//		System.out.println("ahhh");
		
		// ------------------------------------------------------------------------
		
		List<CompletableFuture> list = new ArrayList<>();
		// adding request to the list
		for (int i=0; i<5; i++) {
			final int x = i;
			list.add(CompletableFuture.supplyAsync(() -> {
				// ...send request
				try {
					Thread.sleep(x*1000);
				} catch (Exception e) {
				
				}
				System.out.println("step1");
				return "I am the res of " + x + "th request";
			}));
		}
		
		// use all of to combine the res
		CompletableFuture f = CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
		f.thenAccept(Void -> {
			for (CompletableFuture fu : list) {
				System.out.println("step2");
				System.out.println(fu.join());
			}
		})
		.join();
	}
}
