package DemoDaemonThread;

public class DemoDaemonThread {

    public static void main(String[] args) {

    	System.out.println("Main Start.") ;
        Thread thread = new Thread(
        // �o�O�ΦW���O���g�k
            new Runnable() {
                public void run() { 
                    while(true) { 
                        System.out.print("T"); 
                    } 
                }        
            }); 
        
        // �]�w��Daemon�����
        thread.setDaemon(true); 
        thread.start(); 
        System.out.println("Main End.") ;
    }

}
