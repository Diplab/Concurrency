package concurrency;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

public class Practice4 {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCachedThreadPool() {
	System.out.println("in testCachedThreadPool");
	ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	for (int i = 0; i < 5; i++) {
	    newCachedThreadPool.execute(new Practice2.MyRunnable(5));
	}
	newCachedThreadPool.shutdown();
    }

    @Test
    public void testFixedThreadPool() {
	System.out.println("in testFixedThreadPool");
	ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(5);
	for (int i = 0; i < 5; i++) {
	    newCachedThreadPool.execute(new Practice2.MyRunnable(5));
	}
	newCachedThreadPool.shutdown();
    }

    @Test
    public void testSingleThreadExecutor() {
	System.out.println("in testSingleThreadExecutor");
	ExecutorService newCachedThreadPool =
		Executors.newSingleThreadExecutor();
	for (int i = 0; i < 5; i++) {
	    newCachedThreadPool.execute(new Practice2.MyRunnable(5));
	}
	newCachedThreadPool.shutdown();
    }
}
