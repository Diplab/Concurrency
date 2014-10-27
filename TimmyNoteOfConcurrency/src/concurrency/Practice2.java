package concurrency;

import util.Fibonacci;

public class Practice2 {
    static class MyRunnable implements Runnable {
	final int num;
	static int count = 0;
	int id = count++;

	public MyRunnable(int num) {
	    super();
	    System.out.format("#(%d): Constructor ", id);
	    this.num = num;
	}

	@Override
	public void run() {
	    Fibonacci gen = new Fibonacci();
	    for (int i = 0; i < num; i++) {
		System.out.format("#%d:%d ", id, gen.next());
	    }
	}
    }

    public static void main(String[] args) {
	for (int i = 0; i < 5; i++) {
	    new Thread(new MyRunnable(5 + i)).start();
	}
    }

}
