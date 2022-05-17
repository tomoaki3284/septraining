package antra.homeworks;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SongCacheImplTest {
	@Test
	public void cacheIsWorking() {
		SongCache cache = new SongCacheImpl();
		cache.recordSongPlays("ID-1", 3);
		cache.recordSongPlays("ID-1", 1);
		cache.recordSongPlays("ID-2", 2);
		cache.recordSongPlays("ID-3", 5);
		assertThat(cache.getPlaysForSong("ID-1"), is(4));
		assertThat(cache.getPlaysForSong("ID-9"), is(-1));
		
		List<String> tmp = cache.getTopNSongsPlayed(2);
//		assertThat(cache.getTopNSongsPlayed(2), contains("ID-3", "ID-1"));
		assert cache.getTopNSongsPlayed(2).containsAll(List.of("ID-3", "ID-1"));
//		assertThat(cache.getTopNSongsPlayed(0), is());
		assert cache.getTopNSongsPlayed(0).isEmpty();

	}
	
}