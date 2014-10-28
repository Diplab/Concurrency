package DemoThreadGroup;

import java.io.*;

public class DemoThreadGroup {

    public static void main(String[] args) {
        ThreadGroup threadGroup1 = 
        // �o�O�ΦW���O�g�k
            new ThreadGroup("group1") {
                // �~��ThreadGroup�í��s�w�q�H�U��k
                // �b�����������Xunchecked exception
                // �|���榹��k
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(t.getName() + ": " 
                             + e.getMessage());
                }
            };

        // �o�O�ΦW���O�g�k
        Thread thread1 = 
            // �o�Ӱ�����OthreadGroup1���@��
            new Thread(threadGroup1,
              new Runnable() {
                public void run() {
                    // ��Xunchecked�ҥ~
                    throw new RuntimeException("���ըҥ~");
                }
            }); 

        thread1.start();
    }

}
