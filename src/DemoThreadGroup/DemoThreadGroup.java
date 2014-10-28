package DemoThreadGroup;

import java.io.*;

public class DemoThreadGroup {

    public static void main(String[] args) {
        ThreadGroup threadGroup1 = 
        // 這是匿名類別寫法
            new ThreadGroup("group1") {
                // 繼承ThreadGroup並重新定義以下方法
                // 在執行緒成員丟出unchecked exception
                // 會執行此方法
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(t.getName() + ": " 
                             + e.getMessage());
                }
            };

        // 這是匿名類別寫法
        Thread thread1 = 
            // 這個執行緒是threadGroup1的一員
            new Thread(threadGroup1,
              new Runnable() {
                public void run() {
                    // 丟出unchecked例外
                    throw new RuntimeException("測試例外");
                }
            }); 

        thread1.start();
    }

}
