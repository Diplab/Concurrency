package concurrency;

//: concurrency/EvenChecker.java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
    // Default value for count:
    public static void test(IntGenerator gp) {
	test(gp, 10);
    }
    // Test any type of IntGenerator:
    public static void test(IntGenerator gp, int count) {
	System.out.println("Press Control-C to exit");
	ExecutorService exec = Executors.newCachedThreadPool();
	for (int i = 0; i < count; i++)
	    exec.execute(new EvenChecker(gp, i));
	exec.shutdown();
    }

    private IntGenerator generator;

    public EvenChecker(IntGenerator g, int ident) {
	generator = g;
    }

    public void run() {
	while (!generator.isCanceled()) {
	    int val = generator.next();
	    if (val % 2 != 0) {
		System.out.println(val + " not even!");
		generator.cancel(); // Cancels all EvenCheckers
	    }
	}
    }
} // /:~
