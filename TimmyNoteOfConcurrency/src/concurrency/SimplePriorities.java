package concurrency;

/**
 * It doesn't work.
 * 
 * Thread Priorities: The thread priorities available to Java threads on a native
 * threaded JVM should be treated as hints to the scheduler, especially if the
 * threads are compute-bound. The number of processors available to a process is
 * dynamic and unpredictable. Therefore, an attempt to use priorities to
 * schedule execution on any multi-tasked, multiprocessor system is not likely
 * to succeed.
 * 
 * @author timmy00274672
 * 
 */
public class SimplePriorities extends Thread {
    private int countDown = 5;
    private volatile double d = 0;

    public SimplePriorities(int priority) {
	setPriority(priority);
	start();
    }

    public String toString() {
	return super.toString() + ": " + countDown;
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
	new SimplePriorities(Thread.MAX_PRIORITY);
	for (int i = 0; i < 5; i++)
	    new SimplePriorities(Thread.MIN_PRIORITY);
    }
}