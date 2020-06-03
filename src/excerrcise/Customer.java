package exercise;

import java.util.Date;

class Customer implements Runnable
{
    String name;
    Date inTime;
    WaitingRoom waitingRoom;
 
    public Customer(WaitingRoom waitingRoom)
    {
        this.waitingRoom = waitingRoom;
    }
 
    public String getName() {
        return name;
    }
 
    public Date getInTime() {
        return inTime;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
 
    public void run()
    {
        goForHairCut();
    }
    private synchronized void goForHairCut()
    {
        waitingRoom.addCustomerToWaiting(this);
    }
}
