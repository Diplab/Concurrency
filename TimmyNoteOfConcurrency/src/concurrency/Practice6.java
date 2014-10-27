package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice6 {
    public static void main(String[] args) {
	ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	for (int i = 0; i < 50; i++) {
	    newCachedThreadPool.execute(new MyRunnableForPractice6((long) (Math.random()*10)));
	}
	newCachedThreadPool.shutdown();
    }
}

class MyRunnableForPractice6 implements Runnable {
    static int count = 0;
    int id = count++;
    long sleepTime;

    public MyRunnableForPractice6(long sleepTime) {
	super();
	this.sleepTime = sleepTime;
	System.out.format("#%d constructor\n", id);
    }

    @Override
    public void run() {
	try {
	    TimeUnit.SECONDS.sleep(sleepTime);
	    System.out.format("#%d sleep %d seconds\n", id, sleepTime);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}