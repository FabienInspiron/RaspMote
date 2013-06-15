package metier;

import ws.IRaspberryPi;


public class TimerThread implements Runnable {
	
	int numOutlet;
	
	/**
	 * Time in secondes
	 */
	int timer;
	
	float timeFinish;
	
	/**
	 * 0 -> switch_on
	 * 1 -> switch_off
	 */
	int mode;
	
	IRaspberryPi rasp;
	
	public TimerThread(int numOutlet, IRaspberryPi rasp, int timer, int mode) {
		this.numOutlet = numOutlet;
		this.rasp= rasp;
		this.timer = timer;
		this.mode = mode;
		this.timeFinish = System.currentTimeMillis() + timer*1000;
	}
	
	public void run() {
		System.out.println("Attente");
		
		int time =timer*1000;
		
		/**
		 * wait
		 */
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Switch on or switch off in fonction of the mode
		 */
		switch (mode) {
			case 0:
					rasp.switch_on(numOutlet);
				break;
			case 1:
					rasp.switch_off(numOutlet);
				break;
			default:
				break;
		}
	}
	
	public float getTime(){
		return timeFinish;
	}
}
