package antra.week1.day1;

import java.util.HashMap;

public class Day2 {
	
	public static void main(String[] args) {
		new HashMap<>();
	}
}

/**
 * Finalize
 */

class TestFinalize extends Object {
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
