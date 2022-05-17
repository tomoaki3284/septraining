package antra.week1.day1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Day4 {
	
}

class TestAtomic {
	static AtomicInteger ai = new AtomicInteger();
	static {
		ai.set(0);
	}
	
	static void add() {
		for (int i=0; i<10000; i++) {
			ai.incrementAndGet();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			add();
		});
		
		Thread t2 = new Thread(() -> {
			add();
		});
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println(ai.get());
	}
	
	/**
	 * Compare and Set explanation:
	 *
	 * weakCompareAndSetInt(o, offset, v, v + delta) // atomic instruction
	 *
	 *
	 * optimistic lock (version)
	 * when we read more than write
	 *
	 * disadvantage:
	 * if too many write, waste CPU usage
	 */
	
	/**
	 * synchronized
	 * 	disadvantages:
	 * 		No fair lock (no queue, no order, when notified. No FIFO)
	 * 		No try lock
	 * 		Slow performance (only one thread each)
	 * 		One waiting list (consumer and producer in the same list, we cannot notify specific one like only consumer)
	 *			- This can be solved by ReentrantLock (listNode: t1 <- t2, and the notify specific one)
	 *
	 *	ReentrantLock(boolean fair)
	 *		fair lock()
	 *		 - This can be specify in constructor, default would be non fair
	 *		tryLock()
	 *		 - reentrantLock.tryLock() will return boolean, weather it acquired lock successfully. So it can be checked in if statement if you want
	 *		t1 <- t2
	 *
	 */
}

class MyBlockingQueue {
	private Queue<Integer> q;
	private int capacity = 10;
	// make fair lock
	private ReentrantLock lock = new ReentrantLock(true);
	// separate producer and consumer condition, for notifying specific one later
	private Condition producerCondition = lock.newCondition();
	private Condition consumerCondition = lock.newCondition();
	
	public MyBlockingQueue() {
		q = new LinkedList<>();
	}
	
	public void add(int e) {
		// acquire the lock, if acquired, move to next instruction. if can't keep tyring to get lock
		lock.lock();
		
		try {
			while (q.size() == capacity) {
				// since it is producing it, use producerCondition
				producerCondition.await();
			}
			System.out.println("Starting adding");
			q.add(e);
			// now there is something to consume (producer added), so notify to consumer
			consumerCondition.signalAll();
		} catch (Exception ex) {
		
		} finally {
			// make sure other threads can acquire the lock afterwards
			lock.unlock();
		}
	}
	
	public int poll() {
		lock.lock();
		int res = -1;
		
		try {
			while (q.isEmpty()) {
				consumerCondition.await();
			}
			System.out.println("Start polling");
			res = q.poll();
			
			producerCondition.signalAll();
		} catch (Exception ex) {
		
		} finally {
			lock.unlock();
		}
		
		return res;
	}
}

/**
 * DeadLock
 *
 */
