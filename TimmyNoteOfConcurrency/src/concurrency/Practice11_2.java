package concurrency;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Practice11_2 {
    public static void main(String[] args) {
	System.out.println("It will not fail");

	final ItWillFail itWillFail = new ItWillFail();
	ExecutorService executorService =
		Executors.newCachedThreadPool(new ThreadFactory() {

		    @Override
		    public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			    @Override
			    public void uncaughtException(Thread t, Throwable e) {
				System.out.println(itWillFail.getField());
			    }
			});
			return thread;
		    }
		});

	executorService.execute(new Runnable() {

	    @Override
	    public void run() {
		while (true) {
		    if (itWillFail.add() % 3 != 0)
			throw new RuntimeException();
		}
	    }
	});

	executorService.execute(new Runnable() {

	    @Override
	    public void run() {
		while (true) {
		    if (itWillFail.minus() % 3 != 0)
			throw new RuntimeException();
		}
	    }
	});

	executorService.shutdown();
    }

    private static class ItWillFail {
	private int field = 3;

	public synchronized int add() {
	    field++;
	    field++;
	    field++;
	    return field;
	}

	public synchronized int minus() {
	    field--;
	    field--;
	    field--;
	    return field;
	}

	public synchronized int getField() {
	    return field;
	}
    }
}
