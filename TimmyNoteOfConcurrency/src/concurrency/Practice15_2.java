package concurrency;

public class Practice15_2 {

    private static class B {
	synchronized void f() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("B.f() ");
		Thread.yield();
	    }
	    System.out.println();
	}

	synchronized void g() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("B.g() ");
		Thread.yield();
	    }
	    System.out.println();
	}

	synchronized void h() {
	    for (int i = 0; i < 5; i++) {
		System.out.print("B.h() ");
		Thread.yield();
	    }
	    System.out.println();
	}
    }

    public static void main(String[] args) throws InterruptedException {
	final B b = new B();
	new Thread() {

	    @Override
	    public void run() {
		b.f();
	    };
	}.start();
	new Thread() {

	    @Override
	    public void run() {
		b.g();
	    };
	}.start();
	new Thread() {

	    @Override
	    public void run() {
		b.h();
	    };
	}.start();

    }

}
