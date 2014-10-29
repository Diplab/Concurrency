package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice21 {

    static class MyRunnable implements Runnable {

	@Override
	synchronized public void run() {
	    try {
		System.out.format("MyRunnable in try block\n");
		wait();
		System.out.format("MyRunnable in try block after wait\n");
	    } catch (InterruptedException e) {
		System.out.format("MyRunnable in catch block\n");
	    }
	    System.out.format("MyRunnable is about to leave.\n");
	}
    }

    static class MyRunnable2 implements Runnable {
	private Object lock;

	public MyRunnable2(Object lock) {
	    this.lock = lock;
	}

	@Override
	public void run() {
	    synchronized (lock) {
		System.out.format("MyRunnable2.\n");
		try {
		    TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		    System.out.format("MyRunnable2 in catch block\n");
		}
		lock.notifyAll();
	    }
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	ExecutorService service = Executors.newCachedThreadPool();
	MyRunnable command = new MyRunnable();
	service.execute(command);
	service.execute(new MyRunnable2(command));
	service.shutdown();
    }

}
