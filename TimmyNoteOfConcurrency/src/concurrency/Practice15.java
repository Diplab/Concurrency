package concurrency;

public class Practice15 {
    private static class A {
	void f() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("A.f() ");
		Thread.yield();
	    }
	    System.out.println();
	}

	void g() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("A.g() ");
		Thread.yield();
	    }
	    System.out.println();
	}

	void h() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("A.h() ");
		Thread.yield();
	    }
	    System.out.println();
	}
    }

    public static void main(String[] args) throws InterruptedException {
	final A a = new A();
	new Thread() {
	    @Override
	    public void run() {
		a.f();
	    };
	}.start();
	new Thread() {
	    @Override
	    public void run() {
		a.g();
	    };
	}.start();
	new Thread() {
	    @Override
	    public void run() {
		a.h();
	    };
	}.start();
    }

}
