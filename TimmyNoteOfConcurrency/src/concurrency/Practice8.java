package concurrency;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

/**
 * It shows that all daemon stop when non-daemon thread is over. And note that
 * if the sleep time is turned off, we can see the race condition on
 * {@link PrintStream}
 * 
 * @author timmy00274672
 * 
 */
public class Practice8 {
    public static void main(String[] args) throws InterruptedException {
	for (int i = 0; i < 5; i++) {
	    Thread thread = new Thread(new LiftOff2());
	    thread.setDaemon(true);
	    thread.start();
	}
	TimeUnit.SECONDS.sleep(1);
	System.out.println("Waiting for LiftOff");
    }
}
