package DemoThread;

public class ThreadExample1 extends Thread {
    public void run() { // override Thread's run()
        System.out.println("Here is the starting point of Thread.");
        for (;;) { // infinite loop to print message
            System.out.println("User Created Thread");
        }
    }
    public static void main(String[] argv) {
        Thread t = new ThreadExample1(); // ����Thread����
        t.start(); // �}�l����t.run()
        for (;;) {
            System.out.println("Main Thread");
        }
    }
}
