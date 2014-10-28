package DemoExecutor ;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
 public class ThreadPoolDemo {
 
     public static void main(String[] args) {
         
         // �إ� 2 �� thread �� thread pool
         Executor executor = Executors.newFixedThreadPool(2);  
         
         // �����@�F Runnable �������������O Work
         executor.execute(new Work(1));  
         executor.execute(new Work(2));  
         executor.execute(new Work(3));  
 
         // �����b function ���ŧi�ΦW�������O
         executor.execute(new Runnable() {
             // anonymous inner class            
             @Override
             public void run() {
                 System.out.println(Thread.currentThread().getName() + 
                     " Begins Work in anonymous inner class.");  
             }
         });
     }
     
     public static class Work implements Runnable {  
         private int id;  
       
         public Work (int id) {  
             this.id = id;  
         }  
       
         public void run() {  
             System.out.println(Thread.currentThread().getName() + 
                 " Begins Work " + id);  
             try {  
                 Thread.sleep(1000);  
             }  
             catch (InterruptedException ex) {  
                 ex.printStackTrace();  
             }  
             System.out.println(Thread.currentThread().getName() + 
                 " Ends Work " + id);  
         }  
     }  
 }