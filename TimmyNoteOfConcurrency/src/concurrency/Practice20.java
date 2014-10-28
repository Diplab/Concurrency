package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice20 {
    public static void main(String[] args) throws InterruptedException {
	final ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < 3; i++)
	    exec.execute(new LiftOff2());
	exec.shutdown();
	TimeUnit.SECONDS.sleep(3);
	System.out.println("ShutdownNow");
	exec.shutdownNow();
	if (!exec.awaitTermination(1, TimeUnit.SECONDS)) {
	    System.out.format("awaitTermination get %b\n", false);
	}
    }
}

class LiftOff2 implements Runnable {
    protected int countDown = 10; // Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff2() {
    }

    public LiftOff2(int countDown) {
	this.countDown = countDown;
    }

    public String status() {
	return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!")
		+ "), ";
    }

    public void run() {
	while (countDown-- > 0) {
	    try {
		TimeUnit.SECONDS.sleep(1);
	    } catch (InterruptedException e) {
		System.out.format("#%d get InterruptedException\n", id);
		// if not return, that the task will not end forever.
		return;
	    } finally {
		System.out.print(status());
	    }
	}
    }

}