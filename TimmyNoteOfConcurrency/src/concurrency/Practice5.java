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

public class Practice5 {

    static class MyCallable implements Callable<Integer> {
	final private int num;
	static int count = 0;
	final int id = count++;

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
		System.out.format("#%d:%d ", id, next);
		sum += next;
	    }
	    return sum;
	}

    }

    public static void main(String[] args) {
	ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	List<Future<Integer>> results = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    Future<Integer> submit =
		    newCachedThreadPool.submit(new MyCallable(5 + i));
	    results.add(submit);
	}

	for (Iterator<Future<Integer>> iterator = results.iterator(); iterator
		.hasNext();) {
	    try {
		Future<Integer> future = iterator.next();
		Integer result = future.get(1, TimeUnit.SECONDS);
		System.out.format("\nThe result of %s is %s\n", future, result);
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
