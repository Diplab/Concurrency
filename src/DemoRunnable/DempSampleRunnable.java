package DemoRunnable;

class Person implements Runnable {
	public void run() {
		String name = Thread.currentThread().getName() ;
		
		for ( int i = 1 ; i < 5 ; i++ ) {
			System.out.println(name + "�]����" + i + "��") ;
			if ( name.equals("Ken") && i%3 == 0 ) {
				System.out.println(name + "�y���Ȱ�" ) ;
				Thread.currentThread().yield(); // ���Ӱ�����Ȱ��@�U�A������L���������
			} // if
		} // for
	} // run
} // Person

public class DempSampleRunnable {

	public static void main(String[] args) {
		Person allen = new Person() ; // Person���O�갵Runnable�����A�ҥHPerson����N�ORunnable����
		Person ken = new Person() ;
		Thread tAllen = new Thread( allen, "Allen" ) ; // �I�sThread�غc���A�NRunnable����allen�ǤJ�w�إ�Thread����tAllen
		Thread tKen = new Thread( ken, "Ken" ) ;
		tAllen.start(); // �}�Ұ�����ð���run��k
		tKen.start(); 
		
		try { // �D�������������tAllen�BtKen���槹���~�i�H�~��i��
			tAllen.join();
			System.out.println("Join tAllen.") ;
			
			tKen.join();
			System.out.println("Join tKen.") ;
		}
		catch( InterruptedException e ) {
			e.printStackTrace();
		}
		
		System.out.println("�]�B�V�m����") ;

	}

}
