package concurrency.practice29;

//: concurrency/ToastOMatic.java
//A toaster that uses queues.
import static util.Print.print;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
    public enum Status {
	DRY, BUTTERED, JAMMED
    }

    private Status status = Status.DRY;
    private final int id;

    public Toast(int idn) {
	id = idn;
    }

    public void butter() {
	status = Status.BUTTERED;
    }

    public void jam() {
	status = Status.JAMMED;
    }

    public Status getStatus() {
	return status;
    }

    public int getId() {
	return id;
    }

    public String toString() {
	return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
}

class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);

    public Toaster(ToastQueue tq) {
	toastQueue = tq;
    }

    public void run() {
	try {
	    while (!Thread.interrupted()) {
		TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
		// Make toast
		Toast t = new Toast(count++);
		print(t);
		// Insert into queue
		toastQueue.put(t);
	    }
	} catch (InterruptedException e) {
	    print("Toaster interrupted");
	}
	print("Toaster off");
    }
}

// Apply butter to toast:
class Butterer implements Runnable {
    private ToastQueue dryQueue, butteredQueue;

    public Butterer(ToastQueue dry, ToastQueue buttered) {
	dryQueue = dry;
	butteredQueue = buttered;
    }

    public void run() {
	try {
	    while (!Thread.interrupted()) {
		// Blocks until next piece of toast is available:
		Toast t = dryQueue.take();
		t.butter();
		print(t);
		butteredQueue.put(t);
	    }
	} catch (InterruptedException e) {
	    print("Butterer interrupted");
	}
	print("Butterer off");
    }
}

// Apply jam to buttered toast:
class Jammer implements Runnable {
    private ToastQueue dry, finishedQueue;

    public Jammer(ToastQueue dry, ToastQueue finished) {
	this.dry = dry;
	finishedQueue = finished;
    }

    public void run() {
	try {
	    while (!Thread.interrupted()) {
		// Blocks until next piece of toast is available:
		Toast t = dry.take();
		t.jam();
		print(t);
		finishedQueue.put(t);
	    }
	} catch (InterruptedException e) {
	    print("Jammer interrupted");
	}
	print("Jammer off");
    }
}

// Consume the toast:
class Eater implements Runnable {
    private ToastQueue finishedQueue;

    public Eater(ToastQueue finished) {
	finishedQueue = finished;
    }

    public void run() {
	try {
	    while (!Thread.interrupted()) {
		// Blocks until next piece of toast is available:
		Toast t = finishedQueue.take();
		print("Chomp! " + t);
	    }
	} catch (InterruptedException e) {
	    print("Eater interrupted");
	}
	print("Eater off");
    }
}

public class ToastOMatic {
    public static void main(String[] args) throws Exception {
	ToastQueue dryQueue = new ToastQueue(), butteredQueue =
		new ToastQueue(), finishedQueue = new ToastQueue();
	ExecutorService exec = Executors.newCachedThreadPool();
	exec.execute(new Toaster(dryQueue));
	exec.execute(new Butterer(dryQueue, butteredQueue));
	exec.execute(new Jammer(dryQueue, finishedQueue));
	exec.execute(new Eater(finishedQueue));
	TimeUnit.SECONDS.sleep(5);
	exec.shutdownNow();
    }
} /* (Execute to see output) */// :~
