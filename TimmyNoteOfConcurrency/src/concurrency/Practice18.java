package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Practice18 {

    private static class A {
	void f() {
	    System.out.format("Thread:%s in A.f()\n", Thread.currentThread());
	    try {
		TimeUnit.SECONDS.sleep(100);
	    } catch (InterruptedException e) {
		System.out.format(
			"Thread:%s in A.f() get InterruptException\n",
			Thread.currentThread());
	    } finally {
		System.out.format("Thread:%s in A.f() finally block\n",
			Thread.currentThread());
	    }
	}
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
	ExecutorService service = Executors.newCachedThreadPool();
	Future<?> future = service.submit(new Runnable() {

	    @Override
	    public void run() {
		A a = new A();
		a.f();
	    }
	});
	service.shutdown();
	TimeUnit.SECONDS.sleep(2);
	boolean cancel = future.cancel(true);
	System.out.format("future.cancel(ture) get %b\n", cancel);
	service.awaitTermination(1, TimeUnit.SECONDS);
	System.out.println("Main to exit");
    }

}
