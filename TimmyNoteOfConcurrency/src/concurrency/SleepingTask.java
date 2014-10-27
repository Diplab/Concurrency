package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask implements Runnable {
    protected int countDown = 10; // Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public SleepingTask() {
    }

    public SleepingTask(int countDown) {
	this.countDown = countDown;
    }

    public String status() {
	return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!")
		+ "), ";
    }

    public void run() {
	try {
	    while (countDown-- > 0) {
		System.out.print(status());
		// Old-style:
		// Thread.sleep(100);
		// Java SE5/6-style:
		TimeUnit.MILLISECONDS.sleep(100);
	    }
	} catch (InterruptedException e) {
	    System.err.println("Interrupted");
	}
    }

    public static void main(String[] args) {
	ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < 5; i++)
	    exec.execute(new SleepingTask());
	exec.shutdown();
    }
}