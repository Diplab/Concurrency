package DemoExecutor ;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
 public class ThreadPoolDemo {
 
     public static void main(String[] args) {
         
         // 建立 2 個 thread 的 thread pool
         Executor executor = Executors.newFixedThreadPool(2);  
         
         // 執行實作了 Runnable 介面的內部類別 Work
         executor.execute(new Work(1));  
         executor.execute(new Work(2));  
         executor.execute(new Work(3));  
 
         // 直接在 function 中宣告匿名內部類別
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