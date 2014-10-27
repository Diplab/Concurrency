package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Practice9 extends Thread {
    private int countDown = 2;
    private volatile double d = 0;

    public String toString() {
	return super.toString() + ": " + countDown + ": "
		+ Thread.currentThread().getPriority();
    }

    public void run() {
	while (true) {
	    for (int i = 1; i < 100000; i++)
		d = d + (Math.PI + Math.E) / (double) i;
	    System.out.println(this);
	    if (--countDown == 0)
		return;
	}
    }

    public static void main(String[] args) {
	MyThreadFactory threadFactory = new MyThreadFactory();
	ExecutorService newCachedThreadPool =
		Executors.newCachedThreadPool(threadFactory);
	for (int i = 0; i < 5; i++) {
	    newCachedThreadPool.execute(new Practice9());
	}
	newCachedThreadPool.execute(new Practice9());

	newCachedThreadPool.shutdown();
    }
}

class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
	Thread thread = new Thread(r);
	thread.setPriority(10);
	return thread;
    }

}