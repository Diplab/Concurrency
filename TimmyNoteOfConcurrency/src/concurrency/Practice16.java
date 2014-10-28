package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Practice16 {

    private static class B {
	Lock lock = new ReentrantLock();

	void f() {
	    lock.lock();
	    try {
		for (int i = 0; i < 5; i++) {
		    System.out.print("B.f() ");
		    Thread.yield();
		}
		System.out.println();

	    } finally {
		lock.unlock();
	    }
	}

	void g() {
	    lock.lock();
	    try {
		for (int i = 0; i < 5; i++) {
		    System.out.print("B.g() ");
		    Thread.yield();
		}
		System.out.println();

	    } finally {
		lock.unlock();
	    }
	}

	void h() {
	    lock.lock();
	    try {
		for (int i = 0; i < 5; i++) {
		    System.out.print("B.h() ");
		    Thread.yield();
		}
		System.out.println();

	    } finally {
		lock.unlock();
	    }
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
