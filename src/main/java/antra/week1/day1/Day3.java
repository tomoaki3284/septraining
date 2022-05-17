package antra.week1.day1;

import java.util.LinkedList;
import java.util.Queue;

public class Day3 {
	
}

class TestThread extends Thread {
	
	@Override
	public void run()  {
		try {
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Thread.currentThread().getName());
		TestThread testThread = new TestThread();
		testThread.start();
		testThread.join();
		
		TestThread testThread1 = new TestThread();
		testThread1.start();
		testThread1.join();
	}
}

class ThreadTest2 {
	
	/**
	 * Visibility test by defining the variable as volatile
	 */
	static volatile boolean flag = true;
	
	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch(Exception e) {
				System.out.println(e);
			}
			flag = false;
			System.out.println("changed flag");
		});
		
		Thread t2 = new Thread(() -> {
			while (flag);
			System.out.println("out of while loop");
		});
		
		t.start();
		t2.start();
	}
}

/**
 * Synchronize test
 */
class BlockQueue {
	int capacity = 10;
	Queue<Integer> q = new LinkedList<>();
	
	synchronized void add(int e) throws InterruptedException {
		// while full, wait
		while (q.size() == capacity) {
			this.wait();
		}
		
		q.add(e);
		// notify the waiting thread who wants to poll
		this.notifyAll();
	}
	
	synchronized int poll() throws InterruptedException {
		while (q.isEmpty()) {
			this.wait();
		}
		int res = q.poll();
		// notify the waiting thread who wants to add
		this.notifyAll();
		return res;
	}
	
	public static void main(String[] args) {
		BlockQueue q = new BlockQueue();
		Thread producer = new Thread(() -> {
			try {
				q.add(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		Thread consumer = new Thread(() -> {
			try {
				q.poll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}

class TestRace {
	// volatile cannot solve the race condition, only visibility
	// because referencing to the most updated variable (non-cache val) isn't a problem here
	static int i = 0;
	
	// to solve race condition, needs to be synchronize
	static void increment() {
		for (int j=0; j<10000; j++) {
			i++;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(() -> {
			increment();
		});
		Thread t2 = new Thread(() -> {
			increment();
		});
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println(i);
	}
}
