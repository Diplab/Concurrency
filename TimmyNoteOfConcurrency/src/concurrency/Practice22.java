package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice22 {
    public synchronized boolean isFlag() {
        return flag;
    }

    public synchronized void setFlag(boolean flag) {
        this.flag = flag;
        notifyAll();
    }

    public boolean flag = false;

    class MyBusyRunning implements Runnable {

	@Override
	public void run() {
	    System.out.format("MyBusyRunning start running\n");
	    int counter = 0;
	    while (!isFlag()) {
		counter++;
		// System.out.format("Flag in run = %b", Practice22.flag);
	    }
	    System.out.format("MyBusyRunning busy running %d times\n", counter);
	}
    }

    class MyWaitRunning implements Runnable{
	@Override
	public void run() {
	    Practice22 practice22 = Practice22.this;
	    synchronized (practice22) {
		
	    System.out.format("MyWaitRunning start running\n");
	    int counter = 0;
	    while (!isFlag()) {
		counter++;
		try {
		    practice22.wait();
		} catch (InterruptedException e) {
		    System.out.format("MyWaitRunning interrupt\n");
		    return;
		}
	    }
	    System.out.format("MyWaitRunning busy running %d times\n", counter);
	    }
	}
    }
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
	Practice22 practice22 = new Practice22();
	MyBusyRunning myBusyRunning = practice22.new MyBusyRunning();
	ExecutorService service = Executors.newCachedThreadPool();
	
	service.execute(myBusyRunning);
	TimeUnit.SECONDS.sleep(1);
	practice22.setFlag(true);
	System.out.println("flag = true");
	
	TimeUnit.SECONDS.sleep(2);
	
	practice22.setFlag(false);
	service.execute(practice22.new MyWaitRunning());
	TimeUnit.SECONDS.sleep(1);
	practice22.setFlag(true);
	System.out.println("flag = true");
	
    }

}
