package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice17 {

    public static void main(String[] args) throws InterruptedException {
	ExecutorService service = Executors.newCachedThreadPool();
	for (int i = 0; i < 5; i++) {
	    service.execute(new Sensor(i));
	}

	TimeUnit.SECONDS.sleep(1);
	Sensor.cancel();
	service.shutdown();
	if (!service.awaitTermination(200, TimeUnit.MILLISECONDS)) {
	    System.out.println("ShutdownNow");
	    service.shutdownNow();
	}
	System.out.format("Now total sum = %f", Sensor.getTotal());
    }

}

class Sensor implements Runnable {

    /**
     * If this is cancel, all sensor test should stop
     */
    private static volatile boolean cancel = false;

    private static List<Sensor> sensors = new ArrayList<>();

    /**
     * It makes all sensor test stop
     */
    static synchronized void cancel() {
	cancel = true;
    }

    static double getTotal() {
	double sum = 0;
	for (Sensor sensor : sensors) {
	    sum += sensor.getSum();
	}
	return sum;
    }

    static synchronized boolean isCancel() {
	return cancel;
    }

    final private int id;

    private double sum = 0;

    public Sensor(int id) {
	this.id = id;
	sensors.add(this);
    }

    private synchronized double getSum() {
	return sum;
    }

    @Override
    /**
     * Every 0.5 second scan one time
     */
    public void run() {
	Random random = new Random();
	while (!Sensor.isCancel()) {
	    double tempSum = setAndGetSum(random.nextDouble() * 1000);
	    System.out.format("Sensor[%d] sum = %f\n", id, tempSum);
	    try {
		TimeUnit.MILLISECONDS.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	System.out.format("Sensor[%d] shutdown with sum = %f\n", id, getSum());
    }

    synchronized double setAndGetSum(double sum) {
	this.sum += sum;
	return sum;
    }

}