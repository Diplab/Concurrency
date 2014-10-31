package concurrency.practice33;

//: concurrency/GreenhouseScheduler.java
//Rewriting innerclasses/GreenhouseController.java
//to use a ScheduledThreadPoolExecutor.
//{Args: 5000}
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static util.Print.print;
import static util.Print.printnb;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import concurrency.practice33.DelayedTask.RegularTest;

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    protected int delta;
    protected long trigger;

    // protected static List<DelayedTask> sequence = new
    // ArrayList<DelayedTask>();

    public DelayedTask(int delayInMilliseconds) {
	delta = delayInMilliseconds;
	trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
	// sequence.add(this);
    }

    public long getDelay(TimeUnit unit) {
	return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    public int compareTo(Delayed arg) {
	DelayedTask that = (DelayedTask) arg;
	if (trigger < that.trigger)
	    return -1;
	if (trigger > that.trigger)
	    return 1;
	return 0;
    }

    @Override
    public void run() {
	printnb(this + " ");
    }

    @Override
    public String toString() {
	return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
	return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
	private ExecutorService exec;

	public EndSentinel(int delay, ExecutorService e) {
	    super(delay);
	    exec = e;
	}

	@Override
	final public void run() {
	    // for (DelayedTask pt : sequence) {
	    // printnb(pt.summary() + " ");
	    // }
	    // print();
	    print(this + " Calling shutdownNow()");
	    exec.shutdownNow();
	}
    }

    public static abstract class RegularTest extends DelayedTask {

	private int periodInMilliseconds;
	private DelayQueue<DelayedTask> queue;

	public RegularTest(int delayInMilliseconds, int periodInMilliseconds,DelayQueue<DelayedTask> queue) {
	    super(delayInMilliseconds);
	    this.periodInMilliseconds = periodInMilliseconds;
	    this.queue = queue;
	}

	@Override
	public void run() {
	    if (!Thread.interrupted()) {
		doLoop();
		delta = periodInMilliseconds;
		trigger =
			System.nanoTime()
				+ NANOSECONDS.convert(delta, MILLISECONDS);
		queue.put(this);
	    }
	}

	protected abstract void doLoop();

    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
	this.q = q;
    }

    public void run() {
	try {
	    while (!Thread.interrupted())
		q.take().run(); // Run task with the current thread
	} catch (InterruptedException e) {
	    // Acceptable way to exit
	}
	print("Finished DelayedTaskConsumer");
    }
}

public class GreenHouse {
    private String thermostat = "Day";
    private ExecutorService exec = Executors.newCachedThreadPool();

    public synchronized String getThermostat() {
	return thermostat;
    }

    public synchronized void setThermostat(String value) {
	thermostat = value;
    }

    class Bell extends RegularTest {
	public Bell(int delayInMilliseconds, int periodInMilliseconds, DelayQueue<DelayedTask> queue) {
	    super(delayInMilliseconds, periodInMilliseconds,queue);
	}

	@Override
	protected void doLoop() {
	    System.out.println("Bing!");
	}
    }

    public static void main(String[] args) {
	GreenHouse gHouse = new GreenHouse();
	DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
	queue.add(gHouse.new Bell(0, 1000, queue));
	queue.add(new DelayedTask.EndSentinel(5000, gHouse.exec));
	gHouse.exec.execute(new DelayedTaskConsumer(queue));
    }
}