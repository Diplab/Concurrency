package DemoThreadJoin;

public class DemoThreadJoin {

    public static void main(String[] args) {
        System.out.println("Thread A ����");

        Thread threadB = new Thread(new Runnable() { 
            public void run() { 
                try { 
                    System.out.println("Thread B �}�l.."); 
                    for(int i = 0; i < 5; i++) { 
                        Thread.sleep(1000); 
                        System.out.println("Thread B ����.."); 
                    }
                    System.out.println("Thread B �Y�N����.."); 
                } 
                catch(InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        });

        threadB.start();

        try {
            // Thread B �[�J Thread A
            threadB.join();
        } 
        catch(InterruptedException e) { 
            e.printStackTrace(); 
        } 

        System.out.println("Thread A ����");
    }

}
