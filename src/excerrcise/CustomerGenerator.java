package exercise;

import java.util.Date;
import java.util.concurrent.TimeUnit;


class CustomerGenerator implements Runnable
{
    WaitingRoom waitingRoom;
	private volatile boolean running = true;
	private volatile int custCount = 0;
	int customerCount;


	public CustomerGenerator(WaitingRoom waitingRoom, int customerCount)
    {
        this.waitingRoom = waitingRoom;
        this.customerCount = customerCount;
    }
 
    public void run()
    {
    	int i=1;
        while(i!=customerCount+1)
        {
            Customer customer = new Customer(waitingRoom); 
            customer.setInTime(new Date());
            Thread customerThread = new Thread(customer);
            customerThread.setName(String.valueOf(i));
            customer.setName("Customer "+customerThread.getName());
            customerThread.start();
 
            try
            {
                TimeUnit.SECONDS.sleep((long)(Math.random()*3));
            }
            catch(InterruptedException iex)
            {
                iex.printStackTrace();
            }
            custCount = i;
            i++;
        }
    }

	public int getCustCount() {
		return custCount;
	}

	public void setCustCount(int custCount) {
		this.custCount = custCount;
	}
 
}
