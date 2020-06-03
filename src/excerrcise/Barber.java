package exercise;

class Barber implements Runnable {
	WaitingRoom waitingRoom;
	int id;
	private volatile boolean running = true;
	private volatile boolean sleeping = false;


	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public Barber(WaitingRoom shop, int id) {
		this.waitingRoom = shop;
		this.id = id;
	}
	
	public void stopRunning(){
		running = false;
    }

	public void run() {
		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException iex) {
		 * iex.printStackTrace(); }
		 */
		try {
		System.out.println("Barber " + id + " started..");
		while (running) {
				waitingRoom.cutHair(this);
				if (Thread.interrupted()) {
					running = false;
	            }
		}
		}
		catch (Exception e) {
			System.out.println("Barber " + id + " Stopped..");
	
			
		}
		System.out.println("Barber " + id + " Stopped..");
	}
}