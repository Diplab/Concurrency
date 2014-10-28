package DemoRunnable;

class Person implements Runnable {
	public void run() {
		String name = Thread.currentThread().getName() ;
		
		for ( int i = 1 ; i < 5 ; i++ ) {
			System.out.println(name + "跑完第" + i + "圈") ;
			if ( name.equals("Ken") && i%3 == 0 ) {
				System.out.println(name + "稍為暫停" ) ;
				Thread.currentThread().yield(); // 讓該執行續暫停一下，先讓其他執行緒執行
			} // if
		} // for
	} // run
} // Person

public class DempSampleRunnable {

	public static void main(String[] args) {
		Person allen = new Person() ; // Person類別實做Runnable介面，所以Person物件就是Runnable物件
		Person ken = new Person() ;
		Thread tAllen = new Thread( allen, "Allen" ) ; // 呼叫Thread建構式，將Runnable物件allen傳入已建立Thread物件tAllen
		Thread tKen = new Thread( ken, "Ken" ) ;
		tAllen.start(); // 開啟執行緒並執行run方法
		tKen.start(); 
		
		try { // 主執行緒必須等到tAllen、tKen執行完畢才可以繼續進行
			tAllen.join();
			System.out.println("Join tAllen.") ;
			
			tKen.join();
			System.out.println("Join tKen.") ;
		}
		catch( InterruptedException e ) {
			e.printStackTrace();
		}
		
		System.out.println("跑步訓練結束") ;

	}

}
