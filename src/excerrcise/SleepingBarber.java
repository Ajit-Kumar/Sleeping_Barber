package exercise;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SleepingBarber {

	public static void main(String a[]) {
		int barberCount = 3;
		int waitingRoomCapacity = 5;
		int customerCount = 30;
		if (a.length > 0) {
			if (a.length >0 && Integer.parseInt(a[0]) > 0)
				barberCount = Integer.parseInt(a[0]);
			if (a.length >1 && Integer.parseInt(a[1]) > 0)
				waitingRoomCapacity = Integer.parseInt(a[1]);
			if (a.length >2 && Integer.parseInt(a[2]) > 0)
				customerCount = Integer.parseInt(a[2]);

		}
		System.out.println("Barber Count : " + barberCount);
		System.out.println("Waiting Room Capacity : " + waitingRoomCapacity);
		System.out.println("Customer Count : " + customerCount);
		System.out.println("********************************************************");

		WaitingRoom waitingRoom = new WaitingRoom(waitingRoomCapacity);

		ArrayList<Barber> barber = new ArrayList<Barber>();

		for (int i = 1; i <= barberCount; i++) {
			barber.add(new Barber(waitingRoom, i));
		}

		CustomerGenerator customerGenerator = new CustomerGenerator(waitingRoom, customerCount);

		ArrayList<Thread> barberThread = new ArrayList<Thread>();
		for (int i = 0; i < barberCount; i++) {
			barberThread.add(new Thread(barber.get(i)));
			barberThread.get(i).start();
		}

		Thread customGeneratorThread = new Thread(customerGenerator);
		customGeneratorThread.start();

		/** Timeout for complete application **/

		boolean stop = false;
		while (!stop) {
			try {
				TimeUnit.SECONDS.sleep((long) (5));
			} catch (InterruptedException iex) {
				iex.printStackTrace();
			}
			synchronized (waitingRoom.customerList) {
				
			if (waitingRoom.customerList.size() == 0) {
				for (int i = 0; i < barberCount; i++) {
					Barber temp = barber.get(i);
					stop = temp.isSleeping();
					if (!stop)
						break;
				}
			}
			}
		}
		System.out.println("Stopping Barber Threads");
		for (int i = 0; i < barberCount; i++) {
			Barber temp = barber.get(i);
			temp.stopRunning();
		}	
		System.out.println("**** Sleeping Barber Problem Complete ******");
		System.exit(0);
	}
}
