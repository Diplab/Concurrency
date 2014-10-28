package concurrency;

//: concurrency/OrnamentalGarden.java
import static util.Print.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count2 {
    private int count = 0;
    private Random rand = new Random(47);

    // Remove the synchronized keyword to see counting fail:
    public synchronized int increment() {
	int temp = count;
	if (rand.nextBoolean()) // Yield half the time
	    Thread.yield();
	return (count = ++temp);
    }

    public synchronized int value() {
	return count;
    }
}

class Entrance2 implements Runnable {
    private static Count count = new Count();
    private static List<Entrance2> entrances = new ArrayList<Entrance2>();
    private int number = 0;
    // Doesn't need synchronization to read:
    private final int id;

    public static int getTotalCount() {
	return count.value();
    }

    public static int sumEntrances() {
	int sum = 0;
	for (Entrance2 entrance : entrances)
	    sum += entrance.getValue();
	return sum;
    }

    public Entrance2(int id) {
	this.id = id;
	// Keep this task in a list. Also prevents
	// garbage collection of dead tasks:
	entrances.add(this);
    }

    public synchronized int getValue() {
	return number;
    }

    public void run() {
	while (true) {
	    synchronized (this) {
		++number;
	    }
	    print(this + " Total: " + count.increment());
	    try {
		TimeUnit.MILLISECONDS.sleep(100);
	    } catch (InterruptedException e) {
		print("sleep interrupted");
		return;
	    } finally {
		print("Stopping " + this);
	    }
	}
    }

    public String toString() {
	return "Entrance " + id + ": " + getValue();
    }
}

public class Practice19 {
    public static void main(String[] args) throws Exception {
	ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < 5; i++)
	    exec.execute(new Entrance2(i));
	// Run for a while, then stop and collect the data:
	TimeUnit.SECONDS.sleep(3);
	exec.shutdownNow();
	if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
	    print("Some tasks were not terminated!");
	    exec.shutdownNow();
	}
	print("Total: " + Entrance2.getTotalCount());
	print("Sum of Entrances: " + Entrance2.sumEntrances());
    }
}