package antra.homeworks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Create an implementation for the SongCache interface shown below, being mindful of concurrency.
 * The solution can assume that JDK 11 will be used to build.
 * ____________________________________________________________________________________________
 *
 * We are looking for both correctness and good coding style. Please make sure that your code is
 * well-designed and architected, in addition to being algorithmically efficient.
 *
 * Include a simple document explaining your solution.
 *
 * See this unit test for guidance.
 */
public class SongCacheImpl implements SongCache {
	
	// need to be mindful about the concurrency, therefore using ConcurrentHashMap as a cache here
	private ConcurrentHashMap<String,Integer> cache;
	
	public SongCacheImpl() {
		cache = new ConcurrentHashMap<>();
	}
	
	@Override
	public void recordSongPlays(String songId, int numPlays) {
		// null songId is illegal argument, so throw an Exception
		if (songId == null) {
			throw new IllegalArgumentException("arg0: songID cannot be null");
		}
		// otherwise, simply put/update it into the cache
		cache.put(songId, cache.getOrDefault(songId, 0) + numPlays);
	}
	
	@Override
	public int getPlaysForSong(String songId) {
		Integer res = cache.get(songId);
		// if songId cannot find any result (null), return -1, otherwise return the result
		return res == null ? -1 : res;
	}
	
	@Override
	public List<String> getTopNSongsPlayed(int n) {
		// negative value n doesn't make any sense. Therefore, it will throw an Exception
		if (n < 0) {
			throw new IllegalArgumentException("arg0: n cannot be smaller than 0");
		}
		// if n is 0, then we don't need to compute anything, so return empty list
		if (n == 0) {
			return new ArrayList<>();
		}
		
		/*
		Step by Step:
			1. Create a max heap, so that the ordering would be smaller to larger play count
			2. Insert all the key that is stored in cache
				3. remove smallest played count song if the max heap exceeded n, since we only need top n
			4. Collect it to the list
			5. Reverse the order in the list to make it descending
			6. Return it
		 */
		
		// maybe no need for blocking queue, because this is local variable...
		PriorityBlockingQueue<String> maxHeap = new PriorityBlockingQueue<>(10, (s1,s2) -> {
			Integer numPlays1 = cache.get(s1);
			Integer numPlays2 = cache.get(s2);
			
			return numPlays1 - numPlays2;
		});
		
		for (String key : cache.keySet()) {
			maxHeap.offer(key);
			if (maxHeap.size() > n) {
				maxHeap.poll();
			}
		}
		
		List<String> res = new ArrayList<>(maxHeap);
		Collections.reverse(res);
		
		return res;
	}
}
