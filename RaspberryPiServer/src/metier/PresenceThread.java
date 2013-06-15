package metier;

import java.util.Random;

import ws.IRaspberryPi;

public class PresenceThread implements Runnable{
	private int idOutlet;
	private IRaspberryPi rasp;
	private int timeMax;
	private boolean continueLoop;
	
	public PresenceThread(int idOutlet, IRaspberryPi rasp, int timeMax) {
		this.idOutlet = idOutlet;
		this.rasp = rasp;
		this.timeMax = timeMax;
		continueLoop = true;
	}

	@Override
	public void run() {
		while(continueLoop){
			rasp.switch_on(idOutlet);
			

			Random r = new Random();
			int time = r.nextInt(timeMax);
			time = time*1000;
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			rasp.switch_off(idOutlet);
			
			time = r.nextInt(timeMax);
			time = time*1000;
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Stop the loop
	 */
	public void stop(){
		continueLoop = false;
	}
	
}
