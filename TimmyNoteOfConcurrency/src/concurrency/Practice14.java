package concurrency;

import java.util.Timer;
import java.util.TimerTask;


public class Practice14 {
    private static class MyTimerTask extends TimerTask{
	private int time;
	public MyTimerTask(int time) {
	    this.time = time;
	}
	@Override
	public void run() {
	    System.out.format("Time : %s second\n", time);
	}
    }
    
    public static void main(String[] args){
	Timer timer = new Timer();
	for (int i = 0; i < 15; i++) {
	    timer.schedule(new MyTimerTask(i), 1000*i);
	}
    }
}
