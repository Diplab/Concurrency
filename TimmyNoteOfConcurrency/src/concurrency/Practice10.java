package concurrency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import util.Fibonacci;

public class Practice10 {
    static class ThreadMethod {
	ExecutorService executorService = Executors.newCachedThreadPool();

	public Future<Integer> runTask(int until) {
	    return executorService.submit(new MyCallable(until));
	}
    }

    static class MyCallable implements Callable<Integer> {
	final private int num;

	public MyCallable(int num) {
	    super();
	    this.num = num;
	}

	@Override
	public Integer call() throws Exception {
	    Fibonacci gen = new Fibonacci();
	    int sum = 0;
	    for (int i = 0; i < num; i++) {
		Integer next = gen.next();
		sum += next;
	    }
	    return sum;
	}

    }

    public static void main(String[] args) {
	List<Future<Integer>> results = new ArrayList<>();
	ThreadMethod threadMethod = new ThreadMethod();
	for (int i = 0; i < 10; i++) {
	    Future<Integer> submit = threadMethod.runTask(5 + i);
	    results.add(submit);
	}

	for (Iterator<Future<Integer>> iterator = results.iterator(); iterator
		.hasNext();) {
	    try {
		Future<Integer> future = iterator.next();
		Integer result = future.get(1, TimeUnit.SECONDS);
		System.out.format("The result of %s is %s\n", future, result);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } catch (ExecutionException e) {
		e.printStackTrace();
	    } catch (TimeoutException e) {
		e.printStackTrace();
	    }

	}
    }

}
