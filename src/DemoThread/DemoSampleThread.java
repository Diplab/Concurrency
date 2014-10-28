package DemoThread;


class Person extends Thread{
	public Person(String name) { // name�|�����sthread���W��
		super(name) ; 
	}
	
	public void run() {
		String name = Thread.currentThread().getName(); // ���othread���W��
		int priority = Thread.currentThread().getPriority() ; // ���othread���u����
		Thread.State state = currentThread().getState() ; // ���othread�����A
		
		System.out.println(name + "���u����:" + priority + "; ���A:" + state ) ;
		
		for( int i = 1 ; i <= 5 ; i++ ) {
			System.out.println(name + "�]����" + i + "��") ;
			if ( name.equals("Ken") && i%3 == 0 ) { // Ken thread�C�]�T��𮧤@��
				System.out.println(name + "��1��") ;
				try{ 
					Thread.sleep(1000); // �Ȱ��ثe��thread1000�@��
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
    	
    	allen.start(); // �Ұ�thread�A�éI�srun()��k
    	// allen.start() ; // �w�g�Ұʤ���A�I�sstart()��k
    	// ken.run() ; // �����I�srun()�Ӳ��Lstart()���|�}�ҷs��thread�A�ӬO�ѥD������h����run()���e
    	
    	ken.start();
    	System.out.println("������Ӽ�:" + Thread.activeCount()) ;
    	// �n�`�N���O�A�C�������浲�G���@�w�ۦP
    }

}
