package concurrency;

import static util.Print.print;

import java.util.concurrent.TimeUnit;

class ADaemon implements Runnable {

    @Override
    public void run() {
	try {
	    print("Starting ADaemon");
	    TimeUnit.SECONDS.sleep(1);
	} catch (InterruptedException e) {
	    print("Exiting via InterruptedException");
	} finally {
	    print("This should always run?");
	}
    }
}

public class DaemonsDontRunFinally {
    public static void main(String[] args) throws Exception {
	print("Main");
	Thread t = new Thread(new ADaemon());
	t.setDaemon(true);
	t.start();
    }
} /*
   * Output: Starting ADaemon
   */// :~
