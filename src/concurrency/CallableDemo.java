package concurrency;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
	this.id = id;
    }

    @Override
    public String call() {
	return "result of TaskWithResult " + id;
    }
}

public class CallableDemo {
    public static void main(String[] args) {
	ExecutorService exec = Executors.newCachedThreadPool();
	ArrayList<Future<String>> results = new ArrayList<Future<String>>();
	for (int i = 0; i < 10; i++)
	    /*
	     * <T> Future<T> submit(Callable<T> task) Submits a value-returning
	     * task for execution and returns a Future representing the pending
	     * results of the task. The Future's get method will return the
	     * task's result upon successful completion. If you would like to
	     * immediately block waiting for a task, you can use constructions
	     * of the form result = exec.submit(aCallable).get();
	     * 
	     * Note: The Executors class includes a set of methods that can
	     * convert some other common closure-like objects, for example,
	     * PrivilegedAction to Callable form so they can be submitted.
	     */
	    results.add(exec.submit(new TaskWithResult(i)));
	for (Future<String> fs : results)
	    try {
		// get() blocks until completion:
		System.out.println(fs.get());
	    } catch (InterruptedException e) {
		System.out.println(e);
		return;
	    } catch (ExecutionException e) {
		System.out.println(e);
	    } finally {
		exec.shutdown();
	    }
    }
}