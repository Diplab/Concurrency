package concurrency;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice28 {

    final BlockingQueue<Integer> blockingQueue;

    public Practice28() {
	super();
	this.blockingQueue = new ArrayBlockingQueue<>(4);
    }

    public Practice28(BlockingQueue<Integer> blockingQueue) {
	super();
	this.blockingQueue = blockingQueue;
    }

    class Producer implements Runnable {
	String name;

	public Producer(String name) {
	    this.name = name;
	}

	@Override
	public void run() {
	    while (!Thread.interrupted()) {
		try {
		    TimeUnit.MILLISECONDS.sleep(500);
		    blockingQueue.put(new Random().nextInt());
		    System.out.format("put[%d]\n", blockingQueue.size());
		} catch (InterruptedException e) {
		    System.out
			    .format("Consumer[%s] catch InterruptedException when put\n",
				    name);
		}
	    }
	    System.out.format("Consumer[%s] is interrupted and leave", name);
	}
    }

    class Consumer implements Runnable {
	String name;

	public Consumer(String name) {
	    this.name = name;
	}

	@Override
	public void run() {
	    while (!Thread.interrupted()) {
		try {
		    TimeUnit.MILLISECONDS.sleep(1250);
		    System.out.format("take %d[%d]\n", blockingQueue.take(),
			    blockingQueue.size());
		} catch (InterruptedException e) {
		    System.out
			    .format("Consumer[%s] catch InterruptedException when put\n",
				    name);
		}
	    }
	    System.out.format("Consumer[%s] is interrupted and leave", name);
	}
    }

    public static void main(String[] args) throws InterruptedException {
	Practice28 practice28 = new Practice28();
	ExecutorService service = Executors.newCachedThreadPool();
	service.execute(practice28.new Producer("1"));
	service.execute(practice28.new Producer("2"));
	service.execute(practice28.new Consumer("1"));
	// service.execute(practice28.new Consumer("2"));

	TimeUnit.SECONDS.sleep(20);
	service.shutdownNow();
    }
}
