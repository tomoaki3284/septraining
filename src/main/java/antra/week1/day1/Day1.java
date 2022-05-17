package antra.week1.day1;

public class Day1 {
	
	public static void main(String[] args) {
	
	}
}

class Test {
	@Override
	public String toString() {
		String s = "hi";
		return String.format("%s", s);
	}
}

abstract class A {
	void a() {
		System.out.println();
	}
	
	abstract void b();
}

interface interaceA {
	// implicit public abstract
	void a();
	void b();
}

class P extends A implements interaceA {
	
	@Override
	public void a() {
		System.out.println("");
	}
	
	@Override
	public void b() {
		System.out.println("");
	}
	
	// ----- variable reference test -----
	
	static int a;
	static void increment(int a) {
		// this var "a" is local variable, rather than static var
		a++;
	}
	
	public static void main(String[] args) {
		a = 1;
		increment(a);
		System.out.println(a); // output: 1
	}
}

class TestInner {
	
	public static void main(String[] args) {
		TestInner testInner = new TestInner();
		Node node = testInner.new Node();
		
		// if this is static, the you don't have to do above
		StaticNode staticNode = new StaticNode();
	}
	
	class Node {
	
	}
	
	static class StaticNode {
	
	}
}

