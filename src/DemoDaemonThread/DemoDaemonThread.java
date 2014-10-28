package DemoDaemonThread;

public class DemoDaemonThread {

    public static void main(String[] args) {

    	System.out.println("Main Start.") ;
        Thread thread = new Thread(
        // 這是匿名類別的寫法
            new Runnable() {
                public void run() { 
                    while(true) { 
                        System.out.print("T"); 
                    } 
                }        
            }); 
        
        // 設定為Daemon執行緒
        thread.setDaemon(true); 
        thread.start(); 
        System.out.println("Main End.") ;
    }

}
