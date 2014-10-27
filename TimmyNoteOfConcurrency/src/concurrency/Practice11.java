package concurrency;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Practice11 {
    public static void main(String[] args) {

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
	int field = 3;

	int add() {
	    field++;
	    field++;
	    field++;
	    return field;
	}

	int minus() {
	    field--;
	    field--;
	    field--;
	    return field;
	}

	int getField() {
	    return field;
	}
    }
}
