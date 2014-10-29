package concurrency.practice27;

//: concurrency/Restaurant.java
//The producer-consumer approach to task cooperation.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
	this.orderNum = orderNum;
    }

    public String toString() {
	return "Meal " + orderNum;
    }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant r) {
	restaurant = r;
    }

    public void run() {
	System.out.println("WaitPerson run");
	try {
	    while (!Thread.interrupted()) {
		restaurant.lock.lock();
		try {
		    if (restaurant.meal == null)
			restaurant.fullMealCondition.await();
		    System.out.println("Order up");
		    restaurant.meal = null;
		    TimeUnit.SECONDS.sleep(1);
		    restaurant.noMealCondition.signalAll();
		} finally {
		    restaurant.lock.unlock();
		}
	    }
	} catch (InterruptedException e) {
	    System.out.println("WaitPerson catch InterruptedException");
	} finally {
	    System.out.println("WaitPerson over");
	}
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;

    public Chef(Restaurant r) {
	restaurant = r;
    }

    public void run() {
	System.out.println("Chef run");
	try {
	    while (!Thread.interrupted()) {
		restaurant.lock.lock();
		try {
		    if (restaurant.meal != null)
			restaurant.noMealCondition.await();
		    restaurant.meal = new Meal(0);
		    System.out.println("Meal come");
		    restaurant.fullMealCondition.signalAll();
		} finally {
		    restaurant.lock.unlock();
		}
	    }
	} catch (InterruptedException e) {
	    System.out.println("Chef catch InterruptedException");
	} finally {
	    System.out.println("Chef out");
	}
    }
}

public class Restaurant {
    Lock lock = new ReentrantLock();
    Condition noMealCondition = lock.newCondition();
    Condition fullMealCondition = lock.newCondition();

    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    Chef chef = new Chef(this);
    WaitPerson waitPerson = new WaitPerson(this);

    public Restaurant() {
	exec.execute(chef);
	exec.execute(waitPerson);
    }

    public static void main(String[] args) throws InterruptedException {
	Restaurant restaurant = new Restaurant();

	TimeUnit.SECONDS.sleep(5);
	 restaurant.exec.shutdownNow();
    }
}