package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {
	/*
	 * Creates a thread pool that creates new threads as needed, but will
	 * reuse previously constructed threads when they are available. These
	 * pools will typically improve the performance of programs that execute
	 * many short-lived asynchronous tasks. Calls to execute will reuse
	 * previously constructed threads if available. If no existing thread is
	 * available, a new thread will be created and added to the pool.
	 * Threads that have not been used for sixty seconds are terminated and
	 * removed from the cache. Thus, a pool that remains idle for long
	 * enough will not consume any resources. Note that pools with similar
	 * properties but different details (for example, timeout parameters)
	 * may be created using ThreadPoolExecutor constructors.
	 */
	ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < 5; i++)
	    /*
	     * Executes the given command at some time in the future. The
	     * command may execute in a new thread, in a pooled thread, or in
	     * the calling thread, at the discretion of the Executor
	     * implementation.
	     */
	    exec.execute(new LiftOff2());
	/*
	 * Initiates an orderly shutdown in which previously submitted tasks are
	 * executed, but no new tasks will be accepted. Invocation has no
	 * additional effect if already shut down. This method does not wait for
	 * previously submitted tasks to complete execution. Use
	 * awaitTermination to do that.
	 */
	exec.shutdown();
    }
}