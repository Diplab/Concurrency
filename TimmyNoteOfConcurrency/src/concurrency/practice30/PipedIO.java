package concurrency.practice30;

//: concurrency/PipedIO.java
//Using pipes for inter-task I/O
import static util.Print.print;
import static util.Print.printnb;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Sender implements Runnable {
    private Random rand = new Random(47);
    BlockingQueue<Character> blockingQueue;

    public Sender(BlockingQueue<Character> blockingQueue) {
	this.blockingQueue = blockingQueue;
    }

    public void run() {
	try {
	    while (true)
		for (char c = 'A'; c <= 'z'; c++) {
		    blockingQueue.put(c);
		    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
		}
	} catch (InterruptedException e) {
	    print(e + " Sender InterruptedException");
	}
    }
}

class Receiver implements Runnable {
    BlockingQueue<Character> blockingQueue;

    public Receiver(BlockingQueue<Character> blockingQueue) {
	this.blockingQueue = blockingQueue;
    }

    public void run() {
	try {
	    while (true) {
		// Blocks until characters are there:
		printnb("Read: " + (char) blockingQueue.take() + ", ");
	    }
	} catch (InterruptedException e) {
	    print(e + " Receiver InterruptedException");
	}
    }
}

public class PipedIO {
    public static void main(String[] args) throws Exception {
	BlockingQueue<Character> blockingQueue = new ArrayBlockingQueue<>(5);
	Sender sender = new Sender(blockingQueue);
	Receiver receiver = new Receiver(blockingQueue);
	
	ExecutorService exec = Executors.newCachedThreadPool();
	exec.execute(sender);
	exec.execute(receiver);
	TimeUnit.SECONDS.sleep(4);
	exec.shutdownNow();
    }
}