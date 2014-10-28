package concurrency;

//: concurrency/Interrupting2.java
//Interrupting a task blocked with a ReentrantLock.
import static util.Print.print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Blocked2 implements Runnable {
    BlockedMutex blocked = new BlockedMutex();

    public void run() {
	print("Waiting for f() in BlockedMutex");
	blocked.f();
	print("Broken out of blocked call");
    }
}

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
	// Acquire it right away, to demonstrate interruption
	// of a task blocked on a ReentrantLock:
	lock.lock();
    }

    public void f() {
	try {
	    // This will never be available to a second task
	    lock.lockInterruptibly(); // Special call
	    print("lock acquired in f()");
	} catch (InterruptedException e) {
	    print("Interrupted from lock acquisition in f()");
	}
    }
}

public class Interrupting2 {
    public static void main(String[] args) throws Exception {
	ExecutorService service = Executors.newCachedThreadPool();
	Future<?> submit = service.submit(new Blocked2());
	service.shutdown();
	TimeUnit.SECONDS.sleep(1);
	System.out.println("Issuing t.interrupt()");
	submit.cancel(true);
    }
} 