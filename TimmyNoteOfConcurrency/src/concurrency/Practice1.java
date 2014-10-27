package concurrency;

public class Practice1 {
    static class MyRunnable implements Runnable {
	static int count = 0;
	int id = count++;

	public MyRunnable() {
	    System.out.format("#(%d): Constructor\n", id);
	}

	@Override
	public void run() {
	    System.out.format("#(%d): run %d\n", id, 1);
	    Thread.yield();
	    System.out.format("#(%d): run %d\n", id, 2);
	    Thread.yield();
	    System.out.format("#(%d): run %d\n", id, 3);
	    Thread.yield();
	    System.out.format("#(%d): run %s\n", id, "Over");
	}

    }

    public static void main(String[] args) {
	for (int i = 0; i < 3; i++) {
	    new Thread(new MyRunnable()).start();
	}
    }

}
