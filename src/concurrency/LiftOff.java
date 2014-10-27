package concurrency;

//: concurrency/LiftOff.java
//Demonstration of the Runnable interface.

public class LiftOff implements Runnable {
    protected int countDown = 10; // Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
	this.countDown = countDown;
    }

    public String status() {
	return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!")
		+ "), ";
    }

    public void run() {
	while (countDown-- > 0) {
	    System.out.print(status());
	    Thread.yield();
	}
    }

    public static void main(String[] args) {
	LiftOff launch = new LiftOff();
	System.out.println("Run in Main Thread: ");
	launch.run();
	System.out.println();

	// The most basic use of the Thread class.
	Thread thread = new Thread(new LiftOff());
	thread.start();
	System.out.println("Waiting for LiftOff");

	// Adding more threads.
	for (int i = 0; i < 5; i++) {
	    new Thread(new LiftOff()).start();
	}
    }
} // /:~
