package concurrency;

//: concurrency/Restaurant.java
//The producer-consumer approach to task cooperation.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static util.Print.*;

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
	try {
	    while (!Thread.interrupted()) {
		synchronized (this) {
		    while (restaurant.meal == null)
			wait(); // ... for the chef to produce a meal
		}
		print("Waitperson got " + restaurant.meal);
		synchronized (restaurant.chef) {
		    restaurant.meal = null;
		    restaurant.chef.notifyAll(); // Ready for another
		    synchronized (restaurant.busBoy) {
			restaurant.busBoy.notifyAll();
		    }
		}
	    }
	} catch (InterruptedException e) {
	    print("WaitPerson interrupted");
	}
    }
}

class BusBoy implements Runnable {

    public BusBoy(Restaurant restaurant) {
	super();
    }

    @Override
    public void run() {
	try {
	    synchronized (this) {
		wait();
		while (true) {
		    System.out.println("Clean");
		    wait();
		}
	    }
	} catch (InterruptedException e) {
	    System.out.println("InterruptedException in BusBoy run");
	}
    }

}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant r) {
	restaurant = r;
    }

    public void run() {
	try {
	    while (!Thread.interrupted()) {
		synchronized (this) {
		    while (restaurant.meal != null)
			wait(); // ... for the meal to be taken
		}
		if (++count == 10) {
		    print("Out of food, closing");
		    restaurant.exec.shutdownNow();
		}
		printnb("Order up! ");
		synchronized (restaurant.waitPerson) {
		    restaurant.meal = new Meal(count);
		    restaurant.waitPerson.notifyAll();
		}
		TimeUnit.MILLISECONDS.sleep(100);
	    }
	} catch (InterruptedException e) {
	    print("Chef interrupted");
	}
    }
}

class Trash {

}

public class Restaurant {
    Meal meal;

    ExecutorService exec = Executors.newCachedThreadPool();

    WaitPerson waitPerson = new WaitPerson(this);
    BusBoy busBoy = new BusBoy(this);
    Chef chef = new Chef(this);

    public Restaurant() {
	exec.execute(chef);
	exec.execute(waitPerson);
	exec.execute(busBoy);
    }

    public static void main(String[] args) {
	new Restaurant();
    }
}