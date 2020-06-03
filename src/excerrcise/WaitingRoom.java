package exercise;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


public class WaitingRoom {
	int waitingChairs;
	LinkedList<Customer> customerList;
 
    public  WaitingRoom(int waitingRoomCapacity) {
	
        waitingChairs = waitingRoomCapacity;
        customerList = new LinkedList<Customer>();
    }
 
    public void cutHair(Barber barber)
    {
        Customer customer;
        System.out.println("Barber "+barber.id+" checking for customers at waiting room.");
        synchronized (customerList)
        {

            while(customerList.size()==0)
            {
                System.out.println("Barber "+barber.id+" is waiting for customer. Going to sleep");
                System.out.println();
                barber.setSleeping(true);
                try
                {
                    customerList.wait();

                }
                catch(InterruptedException iex)
                {
                    iex.printStackTrace();
                }
            }
            customer = (Customer)(customerList).poll();
            barber.setSleeping(false);
            System.out.println("Barber "+barber.id+" found "+ customer.getName()+" in the queue.");

        }
        long duration=0;
        try
        {    
            System.out.println("Barber "+barber.id+" Cuting hair of "+customer.getName());
            duration = (long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(duration);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println();
        System.out.println("Barber "+barber.id+" Completed Cuting hair of "+customer.getName() + " in "+duration+ " seconds.");
        System.out.println();
    }
 
    public void addCustomerToWaiting(Customer customer)
    {
    	System.out.println();
        System.out.println(customer.getName()+ " entering the shop");

        synchronized (customerList)
        {
            if(customerList.size() == waitingChairs)
            {
                System.out.println("No chair available for customer "+customer.getName());
                System.out.println(customer.getName()+" Exits...");
                System.out.println();

                return ;
            }
 
            customerList.offer(customer);
            System.out.println(customer.getName()+ " got the chair.");
            System.out.println();

             
            if(customerList.size()>=1)
                customerList.notify();
        }
    }
}
