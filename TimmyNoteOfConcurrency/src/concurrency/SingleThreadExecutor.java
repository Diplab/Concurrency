package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {
	/*
	 * Creates an Executor that uses a single worker thread operating off an
	 * unbounded queue. (Note however that if this single thread terminates
	 * due to a failure during execution prior to shutdown, a new one will
	 * take its place if needed to execute subsequent tasks.) Tasks are
	 * guaranteed to execute sequentially, and no more than one task will be
	 * active at any given time. Unlike the otherwise equivalent
	 * newFixedThreadPool(1) the returned executor is guaranteed not to be
	 * reconfigurable to use additional threads.
	 */
	ExecutorService exec = Executors.newSingleThreadExecutor();
	for (int i = 0; i < 5; i++)
	    exec.execute(new LiftOff2());
	exec.shutdown();
    }
}
