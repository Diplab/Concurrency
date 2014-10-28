package DemoThread;


class Person extends Thread{
	public Person(String name) { // name會成為新thread的名稱
		super(name) ; 
	}
	
	public void run() {
		String name = Thread.currentThread().getName(); // 取得thread的名稱
		int priority = Thread.currentThread().getPriority() ; // 取得thread的優先序
		Thread.State state = currentThread().getState() ; // 取得thread的狀態
		
		System.out.println(name + "的優先序:" + priority + "; 狀態:" + state ) ;
		
		for( int i = 1 ; i <= 5 ; i++ ) {
			System.out.println(name + "跑完第" + i + "圈") ;
			if ( name.equals("Ken") && i%3 == 0 ) { // Ken thread每跑三圈休息一秒
				System.out.println(name + "休息1秒") ;
				try{ 
					Thread.sleep(1000); // 暫停目前的thread1000毫秒
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				} // catch
			} // if
		} // for
	} // run()
} // Person

public class DemoSampleThread {

    public static void main(String[] args) {
    	Person allen = new Person("Allen") ;
    	Person ken = new Person("Ken") ;
    	
    	allen.start(); // 啟動thread，並呼叫run()方法
    	// allen.start() ; // 已經啟動不能再呼叫start()方法
    	// ken.run() ; // 直接呼叫run()而略過start()不會開啟新的thread，而是由主執行緒去執行run()內容
    	
    	ken.start();
    	System.out.println("執行緒個數:" + Thread.activeCount()) ;
    	// 要注意的是，每次的執行結果不一定相同
    }

}
